/*
 * Copyright (c) 2021 Alex Laird
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


package services

import com.github.alexdlaird.ngrok.NgrokClient
import com.github.alexdlaird.ngrok.conf.JavaNgrokConfig
import com.github.alexdlaird.ngrok.protocol.{CreateTunnel, Region, Tunnel}
import play.api.inject.ApplicationLifecycle
import play.api.Configuration

import java.util.Objects.nonNull
import javax.inject._

@Singleton
class NgrokApplicationLifecycle @Inject()(config: Configuration, lifecycle: ApplicationLifecycle) {
  private val environment: String = config.getOptional[String]("environment").getOrElse("production")
  private val ngrokEnabled: Boolean = config.getOptional[Boolean]("ngrok.enabled").getOrElse(false)
  private val authToken: String = config.getOptional[String]("ngrok.authToken").orNull
  private val region: String = config.getOptional[String]("ngrok.region").orNull

  // java-ngrok will only be installed, and should only ever be initialized, in a dev environment
  if (environment.equals("dev") && ngrokEnabled) {
    val javaNgrokConfig: JavaNgrokConfig = new JavaNgrokConfig.Builder()
      .withAuthToken(authToken)
      .withRegion(if (nonNull(region)) Region.valueOf(region.toUpperCase) else null)
      .build
    val ngrokClient: NgrokClient = new NgrokClient.Builder()
      .withJavaNgrokConfig(javaNgrokConfig)
      .build

    val port: Int = config.getOptional[Int]("http.port").getOrElse(9000)

    val createTunnel: CreateTunnel = new CreateTunnel.Builder()
      .withAddr(port)
      .build
    val tunnel: Tunnel = ngrokClient.connect(createTunnel)

    println(s" * ngrok tunnel \"${tunnel.getPublicUrl}\" -> \"http://localhost:$port\"")
  }
}
