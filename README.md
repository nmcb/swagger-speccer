# swagger-speccer

An SBT plugin that generates Play2 routes files from Swagger 2.0 specifications.

*Disclaimer:* the current state of the plugin is heavily untested and with a lot of the possible swagger-to-play mappings missing.  We thought we should get some release out though, so we can get some feedback.

## configuration

In your Play project's build.sbt file enable both the `PlayScala` plugin and the `SwaggerSpeccer`.  Add a `swagger` key pointing to the swagger api specification that you want to use as Play's routes definition. E.g.:
 
```
lazy val root = (project in file(".")).enablePlugins(PlayScala, SwaggerSpeccer)

swagger := "conf/swagger.api.yaml"
```

Please note that the plugin **will override any `conf/routes` file** present.  This file shuld not longer be seen as the source of routing definitions, instead the plugin will generate a routes file from the swagger api specification.

## enabling

In your `project` directory add the following lines to `plugins.sbt`:

```
resolvers += Resolver.bintrayRepo("zalando", "sbt-plugins")

addSbtPlugin("org.zalando" % "speccer-plugin" % "0.0.1")
```



