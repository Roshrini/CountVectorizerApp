name := "CountVectorizerApp"

version := "1.1-SNAPSHOT"

scalaVersion := "2.10.4"

val flinkVersion = "1.0.0"

val excludeBreeze = ExclusionRule(organization = "org.scalanlp", name = "*")

val flinkDependencies = Seq(
  "org.apache.flink" %% "flink-scala" % flinkVersion % "provided",
  "org.apache.flink" %% "flink-streaming-scala" % flinkVersion % "provided",    
 "org.scalanlp" %% "breeze" % "0.11.2" 
)


lazy val root = (project in file(".")).
  settings(
    libraryDependencies ++= flinkDependencies
  )

mainClass in assembly := Some("org.example.CountVectorizerApp")

run in compile <<= Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run))

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
