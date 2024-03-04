/*
 * Copyright (c) 2021-2024 Alex Laird
 *
 * SPDX-License-Identifier: MIT
 */

package controllers

import javax.inject._
import play.api.mvc._

class ServerHealthCheckController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def check: Action[AnyContent] = Action { request: Request[AnyContent] =>
    Ok("{\"server\": \"up\"}")
  }

}
