package com.example
package todo

import cats.syntax.option._

import cats.effect._

import skunk._

object SessionPool {
  def dsl[F[_]: ContextShift: Concurrent: natchez.Trace]: SessionPool[F] =
    Session.pooled(
      host = "localhost",
      port = 5432,
      user = "user",
      password = "password".some,
      database = "tagless-final-todo",
      max = 10,
      debug = false
    )
}
