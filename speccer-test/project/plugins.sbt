// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.4.0")

// The Speccer plugin

resolvers += Resolver.url("sbt-plugins", url("http://dl.bintray.com/zalando/sbt-plugins"))(Resolver.ivyStylePatterns)

// The version should be kept manually in sync with the speccer version as
// this is an independent directory (i.e. non-module of swagger=speccer).  

addSbtPlugin("org.zalando" %% "speccer-plugin" % "0.0.2-SNAPSHOT")