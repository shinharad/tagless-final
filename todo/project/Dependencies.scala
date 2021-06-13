import sbt._

object Dependencies {

  case object dev {
    case object zio {
      val zio =
        "dev.zio" %% "zio" % "1.0.9"

      // monix が依存する cats-effect のバージョンに合わせている
      val `zio-interop-cats` =
        "dev.zio" %% "zio-interop-cats" % "2.5.1.0"
        // "dev.zio" %% "zio-interop-cats" % "3.1.1.0"
    }
  }

  case object io {
    case object circe {
      val `circe-generic` =
        dependency("generic")

      private def dependency(artifact: String): ModuleID =
        "io.circe" %% s"circe-$artifact" % "0.14.1"
    }

    case object monix {
      val `monix-eval` =
        "io.monix" %% "monix-eval" % "3.4.0"
    }
  }

  case object org {
    case object http4s {
      val `http4s-blaze-server` =
        dependency("blaze-server")

      val `http4s-dsl` =
        dependency("dsl")

      val `http4s-circe` =
        dependency("circe")

      private def dependency(artifact: String): ModuleID =
        "org.http4s" %% s"http4s-$artifact" % "0.21.7"
    }

    case object scalacheck {
      val scalacheck =
        "org.scalacheck" %% "scalacheck" % "1.15.4"
    }

    case object scalatest {
      val scalatest =
        "org.scalatest" %% "scalatest" % "3.2.9"
    }

    case object scalatestplus {
      val `scalacheck-1-15` =
        "org.scalatestplus" %% "scalacheck-1-15" % "3.2.9.0"
    }

    case object slf4j {
      val `slf4j-simple` =
        "org.slf4j" % "slf4j-simple" % "1.7.30"
    }

    case object tpolecat {
      // monix が依存する cats-effect のバージョンに合わせている
      val `skunk-core` =
        "org.tpolecat" %% "skunk-core" % "0.0.28"
        // "org.tpolecat" %% "skunk-core" % "0.2.0"
    }

    case object typelevel {
      val `cats-core` =
        "org.typelevel" %% "cats-core" % "2.6.1"

      // monix の依存に合わせて 2.5.1 を使用
      val `cats-effect` =
        "org.typelevel" %% "cats-effect" % "2.5.1"
        // "org.typelevel" %% "cats-effect" % "3.1.1"

      val `discipline-scalatest` =
        "org.typelevel" %% "discipline-scalatest" % "2.0.1"
    }
  }
}
