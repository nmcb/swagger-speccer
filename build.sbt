import bintray.Keys._

lazy val commonSettings = Seq(
	name         := "speccer-scala",
	organization := "net.zalando",
	version      := "0.0.1-SNAPSHOT",
	scalaVersion := "2.11.2"
)

lazy val root = project.in(file("."))
	.settings(commonSettings: _*)
  .settings(
    sbtPlugin := true,
    name := "speccer-plugin",
    description := "Swagger for PLay",
    scalacOptions ++= Seq("-deprecation", "-feature"),
    licenses +=("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
    publishMavenStyle := false,
    repository in bintray := "sbt-plugins",
    bintrayOrganization in bintray := None,
    initialCommands in console := "import com.typesafe.sbt.rss._",
    resolvers += Resolver.sonatypeRepo("snapshots"),
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "2.2.4" % "test",
      "io.swagger" % "swagger-parser" % "1.0.8",
      "ch.qos.logback" % "logback-classic" % "1.1.3",
      "junit" % "junit" % "4.12" % "test"
    )
)