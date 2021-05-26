import Dependencies._
import Util._

val scala3Version = "3.0.0"
val scala213Version = "2.13.3"

ThisBuild / organization := "com.example"
ThisBuild / scalaVersion := scala3Version

ThisBuild / crossScalaVersions := Seq(scala3Version, scala213Version)

ThisBuild / version := "0.0.1-SNAPSHOT"

ThisBuild / scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-language:implicitConversions",
  "-unchecked",
  "-Ykind-projector"
)

lazy val `scala3-tf-related-improvements` =
  project
    .in(file("."))
    .settings(commonSettings: _*)
    .aggregate(fplibrary, main)

lazy val fplibrary =
  project
    .in(file("fplibrary"))
    .settings(commonSettings: _*)

lazy val main =
  project
    .in(file("main"))
    .dependsOn(fplibrary % Cctt)
    .settings(commonSettings: _*)

lazy val commonSettings = Seq(
  Compile / console / scalacOptions --= Seq(
    "-Wunused:_",
    "-Xfatal-warnings"
  ),
  Test / console / scalacOptions :=
    (Compile / console / scalacOptions).value
)
