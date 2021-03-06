package com.example
package todo

import scala.concurrent._

object Main extends App {
  scala.util.Random.nextInt(3) match {
    case 0 =>
      println(inColor("Running on cats.effect.IO")(scala.Console.RED))

      Program.dsl[cats.effect.IO].unsafeRunSync()

    case 1 =>
      println(inColor("Running on monix.eval.Task")(scala.Console.GREEN))

      import monix.execution.Scheduler.Implicits.global

      Program.dsl[monix.eval.Task].runSyncUnsafe(duration.Duration.Inf)

    case _ =>
      println(inColor("Running on zio.Task")(scala.Console.CYAN))

      import zio.interop.catz._

      zio.Runtime.default.unsafeRun(Program.dsl[zio.Task])
  }

  private def inColor(line: String)(color: String): String =
    color + line + scala.Console.RESET
}
