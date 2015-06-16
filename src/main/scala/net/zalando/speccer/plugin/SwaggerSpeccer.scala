package net.zalando.speccer.plugin

import net.zalando.speccer.{RoutesGenerator, SpeccerParser}
import sbt.Keys._
import sbt._

object SwaggerSpeccer extends AutoPlugin {

  object autoImport {
    lazy val swagger = settingKey[String]("the swagger specification file")
    lazy val speccer = inputKey[Unit]("generates a play routes file from swagger specification")
  }

  import autoImport._

  override def projectSettings: Seq[Setting[_]] = Seq(speccerSettings)

  def speccerSettings: Setting[_] = speccer := {
    streams.value.log.info(s"generating play routes from: ${swagger.value}")
    new RoutesGenerator(swagger.value).generate()
  }
}
