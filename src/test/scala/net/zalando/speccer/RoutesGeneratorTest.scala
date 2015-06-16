package net.zalando.speccer

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

@RunWith(classOf[JUnitRunner])
class RoutesGeneratorTest extends FlatSpec with Matchers with TestResources {
  behavior of "the RoutesGenerator"

  it should "generate from swagger" in {
    val routes = new RoutesGenerator(uberApi).routes
    routes should contain (Route(GET, "/me", "SomeController.someMethod"))
    routes should contain (Route(GET, "/products", "SomeController.someMethod"))
    routes should contain (Route(GET, "/estimates/time", "SomeController.someMethod"))
    routes should contain (Route(GET, "/estimates/price", "SomeController.someMethod"))
    routes should contain (Route(GET, "/history", "SomeController.someMethod"))
  }

  it should "recognize path parameter" in {
    val routes = new RoutesGenerator(apiWithPathParameter).routes
    routes should be(List(Route(GET, "/path/{parameter}", "Controller.method")))
  }

  it should "recognize all methods" in {
    val routes = new RoutesGenerator(apiWithAllMethods).routes
    routes should contain (Route(GET, "/required", "Controller.get"))
    routes should contain (Route(POST, "/required", "Controller.post"))
    routes should contain (Route(PUT, "/required", "Controller.put"))
    routes should contain (Route(PATCH, "/required", "Controller.patch"))
    routes should contain (Route(DELETE, "/required", "Controller.delete"))
    routes should contain (Route(OPTIONS, "/required", "Controller.options"))
  }
}
