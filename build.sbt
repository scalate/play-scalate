import ReleaseTransformations._

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommandAndRemaining("+ publishSigned"),
  releaseStepCommandAndRemaining("sonaRelease"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)

val scala213 = "2.13.18"
val scala3 = "3.3.7"
val commonScalaVersions = Seq(scala213, scala3)

crossScalaVersions := commonScalaVersions
publish / skip := true

lazy val commonSettings = Def.settings(
  scalacOptions ++= Seq("-deprecation"),
  scalacOptions ++= {
    scalaBinaryVersion.value match {
      case "3" =>
        Nil
      case _ =>
        Seq("-Xsource:3-cross")
    }
  },
  scalacOptions ~= (_.distinct),
)

lazy val plugin = Project(
  id = "plugin",
  base = file("plugin")
).settings(
  commonSettings,
  name := "play-scalate",
  organization := "io.github.scalate",
  scalaVersion := scala213,
  crossScalaVersions := commonScalaVersions,
  libraryDependencies ++= Seq(
    "jakarta.inject" % "jakarta.inject-api" % "2.0.1",
    "org.playframework" %% "play" % play.core.PlayVersion.current % "provided",
    "org.scalatra.scalate" %% "scalate-core" % "1.10.1" % "provided"
  ),
  publishingSettings
)

lazy val playapp = Project(
  "playapp",
  file("playapp")
).enablePlugins(PlayScala)
  .settings(
    commonSettings,
    publish / skip := true,
    crossScalaVersions := commonScalaVersions,
    scalaVersion := scala213,
    libraryDependencies ++= Seq(
      guice,
      "org.scalatra.scalate" %% "scalate-core" % "1.10.1",
      "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.2" % Test
    ) ++ {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((3, _)) => Seq("org.scala-lang" %% "scala3-compiler" % scalaVersion.value)
        case _ => Seq("org.scala-lang" % "scala-compiler" % scalaVersion.value)
      }
    },
    Compile / unmanagedResourceDirectories += baseDirectory.value / "app" / "views",
    libraryDependencySchemes ++= {
      scalaBinaryVersion.value match {
        case "3" =>
          Nil
        case _ =>
          Seq("org.scala-lang.modules" %% "scala-parser-combinators" % "always")
      }
    }
  )
  .dependsOn(plugin)

val publishingSettings = Seq(
  publishMavenStyle := true,
  publishTo := (if (isSnapshot.value) None else localStaging.value),
  Test / publishArtifact := false,
  pomExtra := _pomExtra
)

val _pomExtra =
  <url>http://github.com/scalate/play-scalate</url>
  <licenses>
    <license>
      <name>Apache License, Version 2.0</name>
      <url>http://github.com/scalate/play-scalate/blob/master/LICENSE.txt</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:scalate/play-scalate.git</url>
    <connection>scm:git:git@github.com:scalate/play-scalate.git</connection>
  </scm>
  <developers>
    <developer>
      <id>tototoshi</id>
      <name>Toshiyuki Takahashi</name>
      <url>http://tototoshi.github.com</url>
    </developer>
  </developers>
