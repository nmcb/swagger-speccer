import bintray.Keys._

lazy val root = project.in(file(".")).settings(
  scalaVersion := "2.10.4",
  sbtPlugin := true,
  organization := "org.zalando",
  version := "0.0.1",
  name := "speccer-plugin",
  description := "Swagger for Play!",
  scalacOptions ++= Seq("-deprecation", "-feature"),
  licenses +=("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
  publishMavenStyle := false,
  repository in bintray := "sbt-plugins",
  bintrayOrganization in bintray := None,
  initialCommands in console := "import org.zalando.speccer._",
  resolvers += Resolver.sonatypeRepo("snapshots"),
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "2.2.4" % "test",
    "io.swagger" % "swagger-parser" % "1.0.8",
    "ch.qos.logback" % "logback-classic" % "1.1.3",
    "junit" % "junit" % "4.12" % "test"
  )
)