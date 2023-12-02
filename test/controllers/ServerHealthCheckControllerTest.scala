package controllers

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.http.Status.OK
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.ws.WSClient
import play.api.test.{Helpers, Port}

import scala.concurrent.ExecutionContext.Implicits.global


class ServerHealthCheckControllerTest extends PlaySpec with GuiceOneAppPerSuite {

  implicit def port: Port = Helpers.testServerPort

  override def fakeApplication(): Application = {
    GuiceApplicationBuilder().configure(Map("http.port" -> port)).build()
  }

  "Server HealthCheck" in {
    val wsClient = fakeApplication().injector.instanceOf[WSClient]
    val serverAddress = s"localhost:$port"
    val healthcheckUrl = s"http://$serverAddress/healthcheck"
    val response = wsClient.url(healthcheckUrl).get()
    response.map(response => response.status mustBe OK)
  }
}