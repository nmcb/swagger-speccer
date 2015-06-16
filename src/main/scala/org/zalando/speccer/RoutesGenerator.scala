package org.zalando.speccer

import java.io.File

case class Route(method: Method, path: String, handler: String) {
  def toRouteString = f"$method%-10s\t$path%-30s\t$handler"
}

class RoutesGenerator(val input: String, val output: String = "conf/routes") {
  val speccer = SpeccerParser.load(input)

  def routes: List[Route] = {
    val routes = for {
      (name, path) <- speccer.paths
      (method, operation) <- path.operations
    } yield Route(method, name, operation.extensions.getOrElse("x-handler", "SomeController.someMethod"))
    routes.toList
  }

  def generate() = {
    printToFile(new File(output)) { writer =>
      writer.println(s"# DO NOT CHANGE!  This file is generated from: $input")
      routes.foreach( r => writer.println(r.toRouteString))
    }
  }

  private[this] def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f)
    try { op(p) } finally { p.close() }
  }
}
