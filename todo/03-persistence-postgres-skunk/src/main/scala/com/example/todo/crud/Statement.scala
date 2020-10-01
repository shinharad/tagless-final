package com.example
package todo
package crud

import java.util.UUID

import skunk._
import skunk.codec.all._
import skunk.implicits._

object Statement {
  final implicit private class UUIDCodecOps(
    private val uuid: Codec[UUID]
  ) extends AnyVal {
    def string: Codec[String] =
      uuid.imap(_.toString)(UUID.fromString)
  }

  final implicit private class TodoDataCompanionOps(
    private val data: Todo.Data.type
  ) {
    val codec: Codec[Todo.Data] =
      (text ~ timestamp).gimap[Todo.Data]
  }

  final implicit private class TodoExistingCompanionOps(
    private val existing: Todo.Existing.type
  ) {
    val codec: Codec[Todo.Existing] =
      (uuid.string ~ Todo.Data.codec).gimap[Todo.Existing]
  }

  object Insert {
    object WithUUID {
      def many(size: Int): Command[List[Todo.Existing]] =
        ???
    }
  }

  object Delete {
    val all: Command[Void] =
      sql"""
            DELETE
              FROM todo
        """.command

    def many(size: Int): Command[List[String]] =
      sql"""
            DELETE
              FROM todo
              WHERE id IN (${uuid.string.list(size)})
        """.command

  }
}
