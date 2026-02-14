package controllers

import jakarta.inject.{ Inject, Singleton }

import com.github.tototoshi.play2.scalate._
import play.api.mvc._
import scala.concurrent.Future

@Singleton
class JadeController @Inject() (scalate: Scalate, val controllerComponents: ControllerComponents) extends BaseController {

  def index = Action.async { implicit request =>
    Future.successful(
      Ok(scalate.render("index.jade", Map("message" -> "hello, jade")))
    )
  }

}
