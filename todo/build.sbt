import Dependencies._
import Dependencies.io
import Util._

val scala213 = "2.13.6"
val scala3 = "3.0.0"

ThisBuild / organization := "com.myorganization"
ThisBuild / version := "0.0.1-SNAPSHOT"

lazy val `todo` =
  project
    .in(file("."))
    .aggregate(
      util,
      `cats-core`,
      `cats-effect`,
      domain,
      core,
      delivery,
      `delivery-http-http4s`,
      persistence,
      `persistence-postgres-skunk`,
      main,
      `main-http-http4s`,
      `main-postgres-skunk`,
      `main-http-http4s-postgres-skunk`
    )

lazy val util =
  project
    .in(file("util"))
    .settings(commonSettings: _*)
    .settings(
      scalaVersion := scala3
    )
    .settings(
      libraryDependencies ++= Seq(
        (org.typelevel.`cats-core`).cross(CrossVersion.for3Use2_13)
      )
    )

lazy val `cats-core` =
  project
    .in(file("cats-core"))
    .settings(
      scalaVersion := scala3
    )
    .settings(commonSettings: _*)

lazy val `cats-effect` =
  project
    .in(file("cats-effect"))
    .settings(
      scalaVersion := scala3
    )
    .dependsOn(`cats-core` % Cctt)
    .settings(commonSettings: _*)

lazy val domain =
  project
    .in(file("domain"))
    .settings(
      scalaVersion := scala213
    )
    .settings(commonSettings: _*)

lazy val core =
  project
    .in(file("core"))
    .settings(
      scalaVersion := scala213
    )
    // .dependsOn(`cats-core` % Cctt)
    .dependsOn(domain % Cctt)
    .settings(commonSettings: _*)
    .settings(
      libraryDependencies ++= Seq(
        org.typelevel.`cats-core`
      ),
      libraryDependencies ++= Seq(
        org.scalacheck.scalacheck,
        org.scalatest.scalatest,
        org.scalatestplus.`scalacheck-1-15`,
        org.typelevel.`discipline-scalatest`
      ).map(_ % Test)
    )

lazy val delivery =
  project
    .in(file("delivery"))
    .dependsOn(util % Cctt)
    .dependsOn(core % Cctt)
    // .dependsOn(`cats-effect` % Cctt)
    .settings(
      scalaVersion := scala213
    )
    .settings(commonSettings: _*)
    .settings(
      libraryDependencies ++= Seq(
        org.typelevel.`cats-effect`
      )
    )

lazy val `delivery-http-http4s` =
  project
    .in(file("delivery-http-http4s"))
    .dependsOn(util % Cctt)
    .dependsOn(core % Cctt)
    .settings(
      scalaVersion := scala213
    )
    .settings(commonSettings: _*)
    .settings(
      libraryDependencies ++= Seq(
        io.circe.`circe-generic`,
        org.http4s.`http4s-blaze-server`,
        org.http4s.`http4s-circe`,
        org.http4s.`http4s-dsl`,
        org.typelevel.`cats-effect`
      )
    )

lazy val persistence =
  project
    .in(file("persistence"))
    .dependsOn(core % Cctt)
    // .dependsOn(`cats-effect` % Cctt)
    .settings(
      scalaVersion := scala213
    )
    .settings(commonSettings: _*)
    .settings(
      libraryDependencies ++= Seq(
        org.typelevel.`cats-effect`
      )
    )

lazy val `persistence-postgres-skunk` =
  project
    .in(file("persistence-postgres-skunk"))
    .dependsOn(core % Cctt)
    .settings(
      scalaVersion := scala213
    )
    .settings(commonSettings: _*)
    .settings(
      libraryDependencies ++= Seq(
        org.tpolecat.`skunk-core`,
        org.typelevel.`cats-effect`
      )
    )

lazy val main =
  project
    .in(file("main"))
    .dependsOn(delivery % Cctt)
    .dependsOn(persistence % Cctt)
    .settings(
      scalaVersion := scala213
    )
    .settings(commonSettings: _*)
    .settings(libraryDependencies ++= effects)

lazy val `main-http-http4s` =
  project
    .in(file("main-http-http4s"))
    .dependsOn(`delivery-http-http4s` % Cctt)
    .dependsOn(persistence % Cctt)
    .settings(
      scalaVersion := scala213
    )
    .settings(commonSettings: _*)
    .settings(libraryDependencies ++= effects)
    .settings(
      libraryDependencies ++= Seq(
        org.slf4j.`slf4j-simple`
      )
    )

lazy val `main-postgres-skunk` =
  project
    .in(file("main-postgres-skunk"))
    .dependsOn(delivery % Cctt)
    .dependsOn(`persistence-postgres-skunk` % Cctt)
    .settings(
      scalaVersion := scala213
    )
    .settings(commonSettings: _*)
    .settings(libraryDependencies ++= effects)

lazy val `main-http-http4s-postgres-skunk` =
  project
    .in(file("main-http-http4s-postgres-skunk"))
    .dependsOn(`delivery-http-http4s` % Cctt)
    .dependsOn(`persistence-postgres-skunk` % Cctt)
    .settings(
      scalaVersion := scala213
    )
    .settings(commonSettings: _*)
    .settings(libraryDependencies ++= effects)
    .settings(
      libraryDependencies ++= Seq(
        org.slf4j.`slf4j-simple`
      )
    )

lazy val commonSettings = Seq(
  Compile / compile / scalacOptions ++= {
    Seq(
      "-encoding", "UTF-8",
      "-feature",
      // "-Xfatal-warnings", // disabled during the migration
    ) ++
      (CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((3, _)) => Seq(
          "-unchecked",
          // "-source:3.0-migration"
        )
        case _ => Seq(
          "-deprecation",
          "Wvalue-discard",
          "-Ymacro-annotations",
          "-Xfatal-warnings",
          "-Wunused:imports,privates,locals",
          "Wvalue-discard",
          "-Ytasty-reader"
        )
      })
  },
  Compile / console / scalacOptions --= {
    (CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((3, _)) => Seq(
      )
      case _ => Seq(
        "-Wunused:_",
        "-Xfatal-warnings"
      )
    })
  },
  Test / console / scalacOptions :=
    (Compile / console / scalacOptions).value
)

lazy val effects = Seq(
  dev.zio.`zio-interop-cats`,
  dev.zio.zio,
  io.monix.`monix-eval`,
  org.typelevel.`cats-effect`
)
