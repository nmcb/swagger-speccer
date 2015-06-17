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

---

## licencing

Copyright (c) 2015 Zalando SE

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


