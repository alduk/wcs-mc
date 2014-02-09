import sbt._
import Keys._

object Build {
  lazy val model = project.in(file("../test-slick"))

  lazy val root = project.in(file(".")).aggregate(model)
}