package net.zalando.speccer.plugin

import net.zalando.speccer.{RoutesGenerator, SpeccerParser}
import sbt.Keys._
import sbt._

object SwaggerSpeccer extends AutoPlugin {

  object autoImport {
    val swagger = settingKey[String]("The swagger specification file.")
    val speccer = inputKey[Unit]("Generates a play routes file from swagger specification.")
  }

  import autoImport._

  override def projectSettings: Seq[Setting[_]] = Seq(speccerSettings)

  def speccerSettings: Setting[_] = speccer := {
    val log = streams.value.log
    val speccer = SpeccerParser.load(swagger.?.value.getOrElse(""))
    if (speccer.isSuccess) {
      log.info(s"generating play routes from: $swagger")
      val routes = RoutesGenerator(speccer.get).routes.mkString("\n")
      log.info(s"generated routes: $routes")
    }
  }
}
