/*
 * Copyright (c) 2021-2024 Alex Laird
 *
 * SPDX-License-Identifier: MIT
 */

package controllers

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Helpers, Port}


class ServerHealthCheckControllerTest extends PlaySpec with GuiceOneAppPerSuite {

  implicit def port: Port = Helpers.testServerPort

  override def fakeApplication(): Application = {
    GuiceApplicationBuilder().configure(Map("http.port" -> port, "play.filters.hosts.allowed" -> Seq(s"localhost:$port"))).build()
  }

  "Server HealthCheck" in {
    val serverAddress = s"localhost:$port"
    val request = FakeRequest(GET, "/healthcheck").withHeaders(HOST -> serverAddress)
    val response = route(app, request).get
    status(response) mustBe OK
  }
}