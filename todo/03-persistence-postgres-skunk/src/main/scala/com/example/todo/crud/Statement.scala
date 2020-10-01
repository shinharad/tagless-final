package com.example
package todo
package crud

import java.util.UUID

import skunk._
import skunk.implicits._

object Statement {
  object Delete {
    val all: Command[Void] =
      sql"""
            DELETE
              FROM todo
        """.command

    def many(size: Int): Command[List[UUID]] =
      sql"""
            DELETE
              FROM todo
              WHERE id IN (${uuid.list(size)})
        """.command

  }
}
