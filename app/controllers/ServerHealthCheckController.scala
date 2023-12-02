package controllers

import javax.inject._
import play.api.mvc._

class ServerHealthCheckController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def check: Action[AnyContent] = Action { request: Request[AnyContent] =>
    Ok("{\"server\": \"up\"}")
  }

}
