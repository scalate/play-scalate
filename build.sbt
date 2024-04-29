lazy val plugin = Project (
  id = "plugin",
  base = file ("plugin")
).settings(
  name := "play-scalate",
  organization := "org.scalatra.scalate",
  version := "0.5.1-SNAPSHOT",
  scalaVersion := "2.13.14",
  crossScalaVersions := Seq("2.13.14"),
  resolvers += Resolver.typesafeRepo("releases"),
  libraryDependencies ++= Seq(
    "org.playframework" %% "play" % play.core.PlayVersion.current % "provided",
    "org.scalatra.scalate" %% "scalate-core" % "1.9.8" % "provided"
  ),
  scalacOptions ++= Seq("-language:_", "-deprecation"),
  publishingSettings
)

val playAppName = "playapp"
val playAppVersion = "1.0-SNAPSHOT"

lazy val playapp = Project(
  playAppName,
  file("playapp")
).enablePlugins(PlayScala)
.settings(
  Test / resourceDirectories += baseDirectory.value / "conf",
  crossScalaVersions := Seq("2.13.14"),
  scalaVersion := "2.13.14",
  version := playAppVersion,
  evictionErrorLevel := Level.Warn,
  libraryDependencies ++= Seq(
    guice,
    "org.scalatra.scalate" %% "scalate-core" % "1.9.8",
    "org.scala-lang" % "scala-compiler" % scalaVersion.value,
    "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
  ),
  Compile / unmanagedResourceDirectories += baseDirectory.value / "app" / "views",
  libraryDependencySchemes += "org.scala-lang.modules" %% "scala-parser-combinators" % "always"
)
.dependsOn(plugin)

val publishingSettings = Seq(
  publishMavenStyle := true,
  publishTo := _publishTo(version.value),
  Test / publishArtifact := false,
  pomExtra := _pomExtra
)

def _publishTo(v: String) = {
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

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
