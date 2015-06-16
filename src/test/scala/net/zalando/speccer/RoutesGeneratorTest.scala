package net.zalando.speccer

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

@RunWith(classOf[JUnitRunner])
class RoutesGeneratorTest extends FlatSpec with Matchers with TestResources {
  behavior of "the RoutesGenerator"

  it should "generate from swagger" in {
    new RoutesGenerator(uberApi).routes should be(List(
      Route(GET, "/me", "SomeController.someMethod"),
      Route(GET, "/products", "SomeController.someMethod"),
      Route(GET, "/estimates/time", "SomeController.someMethod"),
      Route(GET, "/estimates/price", "SomeController.someMethod"),
      Route(GET, "/history", "SomeController.someMethod")
    ))
  }

  it should "recognize path parameter" in {
    val routes = new RoutesGenerator(apiWithPathParameter).routes
    routes should be(List(Route(GET, "/path/{parameter}", "Controller.method")))
  }
}
