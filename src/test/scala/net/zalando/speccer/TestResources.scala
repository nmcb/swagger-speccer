package net.zalando.speccer

trait TestResources {

  val uberApi: Speccer =
    SpeccerParser.load("src/test/resources/uber.api.yaml").get

  val apiWithPathParameter =
    SpeccerParser.load("src/test/resources/test-path-parameters.yaml").get
}
