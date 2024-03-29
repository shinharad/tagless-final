package com.example

import java.util.UUID

import cats.syntax.all.*

trait Parse[-From, +To] extends Function1[From, Either[Throwable, To]]

object Parse:
  given Parse[String, UUID] = string =>
    Either.catchNonFatal(UUID.fromString(string))

  given Parse[String, Int] = string =>
    Either.catchNonFatal(string.toInt).leftMap { cause =>
      new IllegalArgumentException(
        s"""Attempt to convert "$string" to Int failed."""",
        cause
      )
    }
