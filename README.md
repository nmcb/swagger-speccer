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
resolvers += Resolver.url("sbt-plugins", url("http://dl.bintray.com/zalando/sbt-plugins"))(Resolver.ivyStylePatterns)

addSbtPlugin("org.zalando" % "speccer-plugin" % "0.0.1")
```

## running

Create a swagger api specification file in your Play project with the same name as specified above and run the following command from within sbt:
 
```
~ ;speccer ;compile
```

This will continually re-generate Play's routes file and compile the project.


