package com.example
package todo

import java.util.UUID

import cats._

trait UUIDGenerator[F[_]] {
  def genUUID: F[UUID]
}

object UUIDGenerator {
  implicit def dsl[F[_]: effect.Sync]: F[UUIDGenerator[F]] =
    implicitly[effect.Sync[F]].delay {
      new UUIDGenerator[F] {
        override val genUUID: F[UUID] =
          implicitly[effect.Sync[F]].delay(UUID.randomUUID())
      }
    }
}