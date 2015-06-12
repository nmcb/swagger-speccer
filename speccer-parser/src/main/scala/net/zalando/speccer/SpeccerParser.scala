package net.zalando.speccer

import io.swagger.models.Swagger
import io.swagger.parser.SwaggerParser

import scala.util.Try

case class Speccer(version: String, host: String, schemes: List[Scheme], consumes: List[String], produces: List[String], info: Info, paths: Map[String, Path])
case class Info(title: String, description: String, version: String)
case class Path(operations: Map[Method, Operation], parameters: List[Parameter])
case class Operation(tags: List[String], description: String, summary: String, operationId: String, extensions: Map[String, String])
case class Parameter(name: String, description: String, access: String, in: String, required: Boolean)

sealed trait Scheme
case object HTTP extends Scheme
case object HTTPS extends Scheme
case object WS extends Scheme
case object WSS extends Scheme

sealed trait Method
case object GET extends Method
case object PUT extends Method
case object POST extends Method
case object DELETE extends Method
case object PATCH extends Method
case object OPTIONS extends Method

object SpeccerParser {

  import scala.language.implicitConversions

  def load(filename: String): Try[Speccer] = {
    val spec = new SwaggerParser().read(filename)
    Try(convert(spec))
  }

  def parse(yaml: String): Try[Speccer] = {
    val spec = new SwaggerParser().parse(yaml)
    Try(convert(spec))
  }

  private[this] def convert(spec: Swagger): Speccer = {
    import SwaggerConverters._

    val swagger = spec.getSwagger
    val host = spec.getHost
    val schemes = spec.getSchemes
    val consumes = spec.getConsumes
    val produces = spec.getProduces
    val info = spec.getInfo
    val paths = spec.getPaths

    Speccer(swagger, host, schemes, consumes, produces, info, paths)
  }

  // converts to immutable model
  object SwaggerConverters {

    import java.{util => ju}

    import io.swagger.models.parameters.{Parameter => JParameter}
    import io.swagger.models.{Info => JInfo, Operation => JOperation, Path => JPath, Scheme => JScheme}

    import scala.collection.JavaConverters._

    implicit def asSafeScalaString(str: String): String = {
      Option(str).getOrElse("")
    }

    implicit def asScalaStringList(list: ju.List[String]): List[String] = {
      Option(list).getOrElse(new ju.ArrayList()).asScala.toList
    }

    implicit def asScalaInfo(info: JInfo): Info = {
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
      val ops = collection.mutable.Map[Method, Operation]()
      if (path.getGet != null) ops += (GET -> path.getGet)
      if (path.getPut != null) ops += (PUT -> path.getPut)
      if (path.getPost != null) ops += (POST -> path.getPost)
      if (path.getDelete != null) ops += (DELETE -> path.getDelete)
      if (path.getPatch != null) ops += (PATCH -> path.getPatch)
      if (path.getOptions != null) ops += (OPTIONS -> path.getOptions)

      Path(ops.toMap, path.getParameters)
    }

    implicit def asScalaOperation(op: JOperation): Operation = {
      Operation(
        op.getTags,
        op.getDescription,
        op.getSummary,
        op.getOperationId,
        op.getVendorExtensions
      )
    }

    implicit def asScalaParameters(ps: ju.List[JParameter]): List[Parameter] = {
      if (ps == null)
        List.empty
      else
        ps.asScala.toList map { p =>
          Parameter(
            p.getName,
            p.getDescription,
            p.getAccess,
            p.getIn,
            p.getRequired
          )
        }
    }

    implicit def asScalaVendorExtensions(ve: ju.Map[String, AnyRef]): Map[String, String] = {
      if (ve == null)
        Map.empty
      else
        ve.asScala.mapValues(_.toString).toMap
    }
  }
}
