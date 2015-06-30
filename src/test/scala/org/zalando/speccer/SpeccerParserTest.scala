package org.zalando.speccer

import java.io.{IOException, File}

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

/**
 * Asserts Java/Scala interoperability.
 *
 * @see https://github.com/swagger-api/swagger-spec/blob/master/versions/2.0.md
 */
@RunWith(classOf[JUnitRunner])
class SpeccerParserTest extends FlatSpec with Matchers {
  behavior of "the SpeccerParser"

  it should "require version to be 2.0" in {
    load("test-required.yaml").version should be("2.0")
    intercept[IOException](load("test-empty.yaml").version)
  }

  def load(filename: String): Speccer = {
    val qualified = "src/test/resources/" + filename
    new File(qualified) should exist
    SpeccerParser.load(qualified)
  }
}
