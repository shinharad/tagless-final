package com.example
package todo
package crud

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import scala.util.Try

import cats._
import cats.implicits._

trait Controller[F[_]] {
  def run(): F[Unit]
}

object Controller {
  def dsl[F[_]: FancyConsole: Random: Functor](
      boundary: Boundary[F],
      pattern: DateTimeFormatter
    ): Controller[F] =
    new Controller[F] {
      override val run: F[Unit] = {
        val colors: Vector[String] =
          Vector(
            // scala.Console.BLACK,
            scala.Console.BLUE,
            scala.Console.CYAN,
            scala.Console.GREEN,
            scala.Console.MAGENTA,
            scala.Console.RED,
            // scala.Console.WHITE,
            scala.Console.YELLOW
          )

        val randomColor: F[String] =
          F.nextInt(colors.size).map(colors)

        val hyphens: F[String] =
          randomColor.map(inColor("─" * 100))

        val menu: F[String] =
          hyphens.map { h =>
            s"""|
              |$h
              |
              |c                   => create new todo
              |d                   => delete todo
              |da                  => delete all todos
              |sa                  => show all todos
              |sd                  => search by partial description
              |sid                 => search by id
              |ud                  => update description
              |udl                 => update deadline
              |e | q | exit | quit => exit the application
              |anything else       => show the main menu
              |
              |Please enter a command:""".stripMargin
          }

        ???
      }
    }

  private val DeadlinePromptPattern: String =
    "yyyy-M-d H:m"

  private val DeadlinePromptFormat: String =
    inColor(DeadlinePromptPattern)(scala.Console.MAGENTA)

  private def inColor(line: String)(color: String): String =
    color + line + scala.Console.RESET
}
