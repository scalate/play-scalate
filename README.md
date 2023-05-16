# play-scalate

## Install

```scala
libraryDependencies ++= Seq(
  "org.scalatra.scalate" %% "play-scalate" % "0.5.0",
  "org.scalatra.scalate" %% "scalate-core" % "1.9.1",
  "org.scala-lang" % "scala-compiler" % scalaVersion.value
)

Compile / unmanagedResourceDirectories += baseDirectory.value / "app" / "views"
```

## Usage

```scala
package controllers

import javax.inject.{ Inject, Singleton }

import com.github.tototoshi.play2.scalate._
import play.api.mvc._

@Singleton
class JadeController @Inject() (scalate: Scalate, val controllerComponents: ControllerComponents) extends BaseController {

  def index = Action { implicit request =>
    Ok(scalate.render("index.jade", Map("message" -> "hello, jade")))
  }

}
```
