/*
 * Copyright (c) 2021-2024 Alex Laird
 *
 * SPDX-License-Identifier: MIT
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
  private val region: String = config.getOptional[String]("ngrok.region").orNull

  // java-ngrok will only be installed, and should only ever be initialized, in a dev environment
  if (environment.equals("dev") && ngrokEnabled) {
    val javaNgrokConfig: JavaNgrokConfig = new JavaNgrokConfig.Builder()
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
