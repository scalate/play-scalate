package controllers

import jakarta.inject.{ Inject, Singleton }

import com.github.tototoshi.play2.scalate._
import play.api.mvc._
import scala.concurrent.Future

@Singleton
class MustacheController @Inject() (scalate: Scalate, val controllerComponents: ControllerComponents) extends BaseController {

  def index = Action.async { implicit request =>
    Future.successful(
      Ok(scalate.render("index.mustache", Map("message" -> "hello, mustache")))
    )
  }

}
