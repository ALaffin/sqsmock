name := "sqsmock"

version := "0.3.2"

organization := "io.findify"

scalaVersion := "2.11.8"

val akkaVersion = "2.4.9"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.1.3",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.5",
  "joda-time" % "joda-time" % "2.9.4",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "com.amazonaws" % "aws-java-sdk-sqs" % "1.11.200",
  "org.codehaus.groovy" % "groovy" % "2.4.12"
)

licenses += ("MIT", url("https://opensource.org/licenses/MIT"))

bintrayOrganization := Some("findify")

parallelExecution in Test := false