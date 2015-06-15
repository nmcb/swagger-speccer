package net.zalando.speccer

case class Route(method: Method, path: String, handler: String)

class RoutesGenerator(val speccer: Speccer) {
  def routes: List[Route] = {
    val routes = for {
      p <- speccer.paths
      o <- p._2.operations
    } yield Route(o._1, p._1, o._2.extensions.getOrElse("x-handler", "SomeController.someMethod"))
    routes.toList
  }
}

object RoutesGenerator {
  def apply(speccer: Speccer) = {
    new RoutesGenerator(speccer)
  }
}
