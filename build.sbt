import sbt._
import Process._
import Keys._

name := "wcs-mc"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "com.typesafe.slick" %% "slick" % "2.0.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4"
)     

//ProjectRef(file("../test-slick"), "test-slick")

play.Project.playScalaSettings

//lazy val model = project.in(file("../test-slick"))

lazy val model = RootProject(file("../test-slick"))

lazy val root = project.in(file(".")).dependsOn(model)