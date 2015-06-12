package net.zalando.speccer

import java.io.{FileNotFoundException, File}

import net.zalando.speccer
import org.junit.runner.RunWith
import org.scalatest.{Matchers, FlatSpec}
import org.scalatest.junit.JUnitRunner

/**
 * Asserts Java/Scala interoperability.
 */
@RunWith(classOf[JUnitRunner])
class SpeccerParserSmokeTest extends FlatSpec with Matchers {
  behavior of "the ParserWrapper"

  it should "load swagger specifications natively" in {
    val spec = load("uber.api.yaml")
    spec.version should be("2.0")
    spec.host should be("api.uber.com")
    spec.schemes should be(List(HTTPS))
    spec.consumes should be(List.empty)
    spec.produces should be(List("application/json"))
    spec.info should be(Info("Uber API", "Move your app forward with the Uber API", "1.0.0"))

    val get = spec.paths("/products").operations(GET)
    get.summary should be("Product Types")
  }

  it should "load info objects" in {
    load("test-info.yaml").info should be(Info("title", "description", "version"))
  }

  it should "load operation 'x-handler' extensions" in {
    val handler = load("test-x-handler.yaml")
                  .paths("/path")
                  .operations(GET)
                  .extensions("x-handler")

    handler should be("OrderHandler.getOrder")
  }

  def load(filename: String): Speccer = {
    val fullname = "speccer-parser/src/test/resources/" + filename
    new File(fullname) should exist

    val loadedModel = SpeccerParser.load(fullname)
    if (!loadedModel.isSuccess)
      throw new IllegalStateException("parsing from model file: " + fullname)
    loadedModel.get
  }
}
