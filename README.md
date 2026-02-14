# play-scalate

## Install

```scala
libraryDependencies ++= Seq(
  "io.github.scalate" %% "play-scalate" % "0.6.0",
  "org.scalatra.scalate" %% "scalate-core" % "1.10.1",
  "org.scala-lang" % "scala-compiler" % scalaVersion.value
)

Compile / unmanagedResourceDirectories += baseDirectory.value / "app" / "views"
```

## Usage

```scala
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
```
