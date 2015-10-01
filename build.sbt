
assemblyJarName in assembly := "FileProcessor.jar"

exportJars := true
mainClass in (Compile, packageBin) := Some("com.epam.bdcc.trut.FileProcessor")
mainClass in assembly := Some("com.epam.bdcc.trut.FileProcessor")

name := "HW1_HDFS_ACCESS"

version := "1.0"

scalaVersion := "2.11.7"


libraryDependencies ++= Seq("org.apache.hadoop" % "hadoop-client" % "2.7.1")



assemblyMergeStrategy in assembly := {
  case "application.conf"                                   => MergeStrategy.concat
  case PathList("org", "apache", "hadoop", "yarn", xs @ _*) => MergeStrategy.first
  case PathList("org", "apache", "commons", xs @ _*)        => MergeStrategy.first
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}




