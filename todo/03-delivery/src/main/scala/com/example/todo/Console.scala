package com.example
package todo

import cats._
import cats.implicits._

trait Console[F[_]] {
  def getStrLn: F[String]
  def getStrLnWithPrompt(prompt: String): F[String]
  def putStrLn(line: String): F[Unit]
  def putErrLn(line: String): F[Unit]
}

object Console {
  def dsl[F[_]: effect.Sync]: F[Console[F]] =
    F.delay {
      new Console[F] {

        override def getStrLn: F[String] =
          F.delay(scala.io.StdIn.readLine())

        override def getStrLnWithPrompt(prompt: String): F[String] =
          F.delay(scala.io.StdIn.readLine(prompt))

        override def putStrLn(line: String): F[Unit] =
          F.delay(println(line))

        override def putErrLn(line: String): F[Unit] =
          F.delay(scala.Console.err.println(line))
      }
    }
}
