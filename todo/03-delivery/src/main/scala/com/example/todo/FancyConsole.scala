package com.example
package todo

import cats._
import cats.implicits._

trait FancyConsole[F[_]] {
  def getStrLnTrimmed: F[String]
  def getStrLnTrimmedWithPrompt(prompt: String): F[String]
  def putStrLn(line: String): F[Unit]
  def putSuccess(line: String): F[Unit]
  def putWarning(line: String): F[Unit]
  def putErrLn(line: String): F[Unit]
  def putStrLnInColor(line: String)(color: String): F[Unit]
}

object FancyConsole {
  def dsl[F[_]: Functor](console: Console[F]): FancyConsole[F] =
    new FancyConsole[F] {
      override val getStrLnTrimmed: F[String] =
        console.getStrLn.map(_.trim)

      override def getStrLnTrimmedWithPrompt(prompt: String): F[String] =
        console.getStrLnWithPrompt(prompt + " ").map(_.trim)

      override def putStrLn(line: String): F[Unit] =
        console.putStrLn(line)

      override def putStrLnInColor(line: String)(color: String): F[Unit] =
        console.putStrLn(inColor(line)(color))

      private def inColor(line: String)(color: String): String =
        color + line + scala.Console.RESET

      override def putSuccess(line: String): F[Unit] =
        putStrLnInColor(line)(scala.Console.GREEN)

      override def putWarning(line: String): F[Unit] =
        putStrLnInColor(line)(scala.Console.YELLOW)

      override def putErrLn(line: String): F[Unit] =
        putStrLnInColor(line)(scala.Console.RED)
    }
}
