name := "speccer-parser"

scalacOptions ++= Seq("-feature")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",

  // java dependencies
	"io.swagger" % "swagger-parser" % "1.0.8",
  "junit" % "junit" % "4.12"
)

initialCommands in console := "import net.zalando.speccer._"
