package com.example
package todo

trait Console {
  def getStrLn: String
  def getStrLnWithPrompt(prompt: String): String
  def putStrLn(line: String): Unit
  def putErrLn(line: String): Unit
}

object Console {
  implicit def dsl: Console =
    new Console {

      override def getStrLn: String =
        scala.io.StdIn.readLine()

      override def getStrLnWithPrompt(prompt: String): String =
        scala.io.StdIn.readLine(prompt)

      override def putStrLn(line: String): Unit =
        println(line)

      override def putErrLn(line: String): Unit =
        scala.Console.err.println(line)
    }
}
