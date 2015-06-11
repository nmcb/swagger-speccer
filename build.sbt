lazy val commonSettings = Seq(
	name         := "speccer-scala",
	organization := "net.zalando",
	version      := "0.0.1-SNAPSHOT",
	scalaVersion := "2.11.2"
)

lazy val root = project.in(file("."))
	.settings(commonSettings: _*)
	.aggregate(parser, plugin)

lazy val parser = project.in(file("speccer-parser"))
	.settings(commonSettings: _*)

lazy val plugin = project.in(file("speccer-sbt-plugin"))
	.settings(commonSettings: _*)
	.dependsOn(parser)	