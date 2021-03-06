package com.example
package errorhandling

import cats._
import cats.data._
import cats.syntax.all._

import cats.mtl.implicits._

object Main3 extends App {
  println("─" * 100)

  type TechnicalError = Throwable

  sealed abstract class BusinessError extends Product with Serializable
  object BusinessError {
    case object Error1 extends BusinessError
    final case class Error2(msg: String) extends BusinessError
  }

  trait Console[F[_]] {
    def good(in: Any): F[Unit]
    def bad(in: Any): F[Unit]
  }

  object Console {
    def dsl[F[_]: effect.Sync]: Console[F] =
      new Console[F] {
        import scala.Console._

        override def good(in: Any): F[Unit] =
          F.delay(println(GREEN + in + RESET))

        override def bad(in: Any): F[Unit] =
          F.delay(err.println(RED + in + RESET))

      }
  }

  trait ErrorProducer[F[_]] {
    def goodTechnical: F[Int]
    def badTechnical: F[Int]
    def goodBusiness: F[Int]
    def badBusiness: F[Int]
  }

  object ErrorProducer {
    def dsl[
        F[_]: ApplicativeError[*[_], TechnicalError]: mtl.FunctorRaise[*[_], BusinessError]
      ]: ErrorProducer[F] =
      new ErrorProducer[F] {
        override def goodTechnical: F[Int] =
          F.pure(1337)

        override def badTechnical: F[Int] =
          F.raiseError(new RuntimeException("db is down"))

        override def goodBusiness: F[Int] =
          F.pure(1338)

        override def badBusiness: F[Int] =
          F.raise(BusinessError.Error2("some business error"))

      }
  }

  trait ErrorHandler[F[_]] {
    def goodTechnicalProgram: F[Unit]
    def badTechnicalProgram: F[Unit]
    def goodBusinessProgram: F[Unit]
    def badBusinessProgram: F[Unit]
  }

  object ErrorHandler {
    def dsl[F[_]: MonadError[*[_], TechnicalError]: mtl.ApplicativeHandle[*[_], BusinessError]](
        dsl: ErrorProducer[F],
        console: Console[F]
      ): ErrorHandler[F] =
      new ErrorHandler[F] {
        override def goodTechnicalProgram: F[Unit] =
          dsl
            .goodTechnical
            .flatMap(console.good)

        override def badTechnicalProgram: F[Unit] =
          dsl
            .badTechnical
            .flatMap(console.good)
            .handleErrorWith(console.bad)

        override def goodBusinessProgram: F[Unit] =
          dsl
            .goodBusiness
            .flatMap(console.good)
            .handleWith[BusinessError](console.bad)

        override def badBusinessProgram: F[Unit] =
          dsl
            .badBusiness
            .flatMap(console.good)
            .handleWith[BusinessError](console.bad)
      }

  }

  val handler: ErrorHandler[EitherT[effect.IO, BusinessError, *]] =
    ErrorHandler.dsl(ErrorProducer.dsl, Console.dsl)

  handler
    .goodTechnicalProgram
    .value
    .unsafeRunSync()

  handler
    .badTechnicalProgram
    .value
    .unsafeRunSync()

  handler
    .goodBusinessProgram
    .value
    .unsafeRunSync()

  handler
    .badBusinessProgram
    .value
    .unsafeRunSync()

  println("─" * 100)
}
