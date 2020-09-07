import Dependencies._
import Util._

val dottyVersion = "0.26.0-RC1"
val scala213Version = "2.13.3"

ThisBuild / organization := "com.example"
ThisBuild / scalaVersion := dottyVersion

ThisBuild / crossScalaVersions := Seq(dottyVersion, scala213Version)

ThisBuild / version := "0.0.1-SNAPSHOT"

ThisBuild / scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-language:implicitConversions",
  "-unchecked",
  "-Ykind-projector"
)

lazy val `tagless-final-scala3` =
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
  update / evictionWarningOptions := EvictionWarningOptions.empty,
  Compile / console / scalacOptions --= Seq(
    "-Wunused:_",
    "-Xfatal-warnings"
  ),
  Test / console / scalacOptions :=
    (Compile / console / scalacOptions).value
)

lazy val dependencies = Seq(
  libraryDependencies ++= Seq(
    // main dependencies
  ),
  libraryDependencies ++= Seq(
    com.github.alexarchambault.`scalacheck-shapeless_1.14`,
    org.scalacheck.scalacheck,
    org.scalatest.scalatest,
    org.scalatestplus.`scalacheck-1-14`,
    org.typelevel.`discipline-scalatest`
  ).map(_ % Test)
)
