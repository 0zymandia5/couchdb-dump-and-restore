name := "sbtproject"

version := "0.1"

scalaVersion := "2.12.12"
showSuccess := true

libraryDependencies += "com.couchbase.client" %% "scala-client" % "1.6.2"
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.7"
libraryDependencies += "org.apache.hadoop" % "hadoop-common" % "2.7.7"
libraryDependencies += "io.github.cdimascio" % "java-dotenv" % "3.0.0"
libraryDependencies += "com.softwaremill.sttp.client4" %% "core" % "4.0.0-M6"
libraryDependencies += "com.lihaoyi" %% "os-lib" % "0.10.1"
libraryDependencies += "com.lihaoyi" %% "ujson" % "3.3.0"

mainClass := Some("Hello")
mainClass in (Compile, bgRun) := Some("Hello")
mainClass in (Compile, packageBin) := Some("Hello")