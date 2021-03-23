name := "scala-demo"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies += "com.lihaoyi" %% "ujson" % "1.3.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test

connectInput in run := true
