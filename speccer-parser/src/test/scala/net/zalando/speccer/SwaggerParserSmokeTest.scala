package net.zalando.speccer

import org.junit.runner.RunWith
import org.scalatest.{Matchers, FlatSpec}
import org.scalatest.junit.JUnitRunner

/**
 * Asserts Java/Scala interoperability.
 */
@RunWith(classOf[JUnitRunner])
class SwaggerParserSmokeTest extends FlatSpec with Matchers {
  behavior of "the ParserWrapper"

  it should "should load swagger specifications natively" in {
    val spec = ParserWrapper.read("speccer-parser/src/test/resources/uber.api.yaml")

    spec.version should be("2.0")
    spec.host should be("api.uber.com")
    spec.schemes should be(List(HTTPS))
    spec.consumes should be(List.empty)
    spec.produces should be(List("application/json"))
    spec.info should be(Info("Uber API", "Move your app forward with the Uber API", "1.0.0"))
    spec.paths("/products").operations should not be empty
  }
}
