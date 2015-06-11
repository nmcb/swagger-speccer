package net.zalando.speccer

import io.swagger.parser.SwaggerParser

case class Speccer(version: String, host: String, schemes: List[Scheme], consumes: List[String], produces: List[String], info: Info, paths: Map[String, Path])
case class Info(title: String, description: String, version: String)
case class Path(operations: List[Operation])
case class Operation(tags: List[String], description: String, summary: String)

sealed trait Scheme
case object HTTP extends Scheme
case object HTTPS extends Scheme
case object WS extends Scheme
case object WSS extends Scheme

object ParserWrapper {
  import scala.language.implicitConversions

  def read(filename: String): Speccer = {
    import SwaggerConverters._

    val spec = new SwaggerParser().read(filename)
    val swagger = spec.getSwagger
    val host = spec.getHost
    val schemes = spec.getSchemes
    val consumes = spec.getConsumes
    val produces = spec.getProduces
    val info: Info = spec.getInfo
    val paths: Map[String, Path] = spec.getPaths

    Speccer(swagger, host, schemes, consumes, produces, info, paths)
  }

  object SwaggerConverters {

    import scala.collection.JavaConverters._
    import java.{util => ju}
    import io.swagger.models.{Scheme => JScheme}
    import io.swagger.models.{Info => JInfo}
    import io.swagger.models.{Path => JPath}
    import io.swagger.models.{Operation => JOperation}

    implicit def asScalaStringList(list: ju.List[String]): List[String] = {
      if (list == null)
        List.empty[String]
      else
        list.asScala.toList
    }

    implicit def asScalaInfo(info: JInfo): Info = {
      if (info == null)
        Info("", "", "")
      else
        Info(info.getTitle, info.getDescription, info.getVersion)
    }

    implicit def asScalaSchemes(schemes: ju.List[JScheme]): List[Scheme] = {
      if (schemes == null)
        List.empty
      else
        schemes.asScala.toList map {
          case JScheme.HTTP  => HTTP
          case JScheme.HTTPS => HTTPS
          case JScheme.WS    => WS
          case JScheme.WSS   => WSS
        }
    }

    implicit def asScalaPaths(paths: ju.Map[String, JPath]): Map[String, Path] = {
      if (paths == null)
        Map.empty
      else
        paths.asScala.mapValues(asScalaPath(_)).toMap
    }

    implicit def asScalaPath(path: JPath): Path = {
      if (path == null)
        Path(List.empty)
      else
        Path(path.getOperations)
    }

    implicit def asScalaOperations(ops: ju.List[JOperation]): List[Operation] = {
      if (ops == null)
        List.empty
      else
        ops.asScala.toList map (op => Operation(op.getTags, op.getDescription, op.getSummary))
    }
  }
}
