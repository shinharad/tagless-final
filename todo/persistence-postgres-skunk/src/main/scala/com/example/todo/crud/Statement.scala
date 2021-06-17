package com.example
package todo
package crud

import java.time.LocalDateTime
import java.util.UUID

import skunk._
import skunk.codec.all._
import skunk.implicits._

object Statement {

  /*
  Scala 3 のサブプロジェクトで定義したcase classのコンパニオンオブジェクトに対し
  Scala 2.13側でimplicit classでメソッドを生やすと以下のエラーが発生する

  [error] Unsupported Scala 3 generic tuple type scala.Tuple in bounds of type MirroredElemLabels; found in trait scala.deriving.Mirror.
  [error] one error found
  [error] (persistence-postgres-skunk / Compile / compileIncremental) Compilation failed

  そのため以下は暫定的な対応
  */
  private val codecData: Codec[Todo.Data] =
    (text ~ timestamp).gimap[Todo.Data]

  private val codecExisting: Codec[Todo.Existing[UUID]] =
    (uuid ~ codecData).gimap[Todo.Existing[UUID]]

  // final implicit private class TodoDataCompanionOps(
  //     private val data: Todo.Data.type
  //   ) {
  //   val codec: Codec[Todo.Data] =
  //     (text ~ timestamp).gimap[Todo.Data]
  // }

  // final implicit private class TodoExistingCompanionOps(
  //     private val existing: Todo.Existing.type
  //   ) {
  //   val codec: Codec[Todo.Existing[UUID]] =
  //     (uuid ~ Todo.Data.codec).gimap[Todo.Existing[UUID]]
  // }

  object Insert {
    val one: Query[Todo.Data, Todo.Existing[UUID]] =
      sql"""
               INSERT INTO todo (description, deadline)
               VALUES ($codecData)
            RETURNING *
         """.query(codecExisting)
    // val one: Query[Todo.Data, Todo.Existing[UUID]] =
    //   sql"""
    //            INSERT INTO todo (description, deadline)
    //            VALUES (${Todo.Data.codec})
    //         RETURNING *
    //      """.query(Todo.Existing.codec)

    def many(size: Int): Query[List[Todo.Data], Todo.Existing[UUID]] =
      sql"""
               INSERT INTO todo (description, deadline)
               VALUES (${codecData.list(size)})
            RETURNING *
         """.query(codecExisting)

  //   def many(size: Int): Query[List[Todo.Data], Todo.Existing[UUID]] =
  //     sql"""
  //              INSERT INTO todo (description, deadline)
  //              VALUES (${Todo.Data.codec.list(size)})
  //           RETURNING *
  //        """.query(Todo.Existing.codec)

    object WithUUID {
      val one: Command[Todo.Existing[UUID]] =
        sql"""
              INSERT INTO todo (description, deadline)
              VALUES ($codecExisting)
           """.command
  //     val one: Command[Todo.Existing[UUID]] =
  //       sql"""
  //             INSERT INTO todo (description, deadline)
  //             VALUES (${Todo.Existing.codec})
  //          """.command

      def many(size: Int): Command[List[Todo.Existing[UUID]]] =
        sql"""
              INSERT INTO todo (description, deadline)
              VALUES (${codecExisting.list(size)})
           """.command
  //     def many(size: Int): Command[List[Todo.Existing[UUID]]] =
  //       sql"""
  //             INSERT INTO todo (description, deadline)
  //             VALUES (${Todo.Existing.codec.list(size)})
  //          """.command
    }
  }

  object Update {
    val one: Query[Todo.Existing[UUID], Todo.Existing[UUID]] =
      sql"""
               UPDATE todo
                  SET description = $text, deadline = $timestamp
                WHERE id = ${uuid}
            RETURNING *
         """.query(codecExisting).contramap(toTwiddle)
  //   val one: Query[Todo.Existing[UUID], Todo.Existing[UUID]] =
  //     sql"""
  //              UPDATE todo
  //                 SET description = $text, deadline = $timestamp
  //               WHERE id = ${uuid}
  //           RETURNING *
  //        """.query(Todo.Existing.codec).contramap(toTwiddle)

    object Command {
      val one: Command[Todo.Existing[UUID]] =
        sql"""
              UPDATE todo
                 SET description = $text, deadline = $timestamp
               WHERE id = ${uuid}
           """.command.contramap(toTwiddle)
    }

    private def toTwiddle(
        e: Todo.Existing[UUID]
      ): String ~ LocalDateTime ~ UUID =
      e.data.description ~ e.data.deadline ~ e.id
  }

  object Select {
    val all: Query[Void, Todo.Existing[UUID]] =
      sql"""
            SELECT *
              FROM todo
         """.query(codecExisting)
  //   val all: Query[Void, Todo.Existing[UUID]] =
  //     sql"""
  //           SELECT *
  //             FROM todo
  //        """.query(Todo.Existing.codec)

    def many(size: Int): Query[List[UUID], Todo.Existing[UUID]] =
      sql"""
            SELECT *
              FROM todo
             WHERE id IN (${uuid.list(size)})
         """.query(codecExisting)
  //   def many(size: Int): Query[List[UUID], Todo.Existing[UUID]] =
  //     sql"""
  //           SELECT *
  //             FROM todo
  //            WHERE id IN (${uuid.list(size)})
  //        """.query(Todo.Existing.codec)

    val byDescription: Query[String, Todo.Existing[UUID]] =
      sql"""
            SELECT *
              FROM todo
             WHERE description ~ $text
         """.query(codecExisting)
  //   val byDescription: Query[String, Todo.Existing[UUID]] =
  //     sql"""
  //           SELECT *
  //             FROM todo
  //            WHERE description ~ $text
  //        """.query(Todo.Existing.codec)
  }

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
