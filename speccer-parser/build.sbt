name := "speccer-parser"

libraryDependencies ++= Seq(
	"org.parboiled" %% "parboiled" % "2.1.0",
	"org.yaml" % "snakeyaml" % "1.15"
)

initialCommands in console := "import net.zalando.speccer._"
