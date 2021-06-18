package com.example
package todo
package crud

import cats._
import cats.implicits._

import cats.effect.Ref

object InMemoryEntityGateway {
  def dsl[F[_]: Monad](
      state: Ref[F, Vector[Todo.Existing[Int]]]
    ): EntityGateway[F, Int] =
    new EntityGateway[F, Int] {
      val statement: Statement[F] =
        Statement.dsl(state)

      override def writeMany(
          todos: Vector[Todo[Int]]
        ): F[Vector[Todo.Existing[Int]]] =
        todos.traverse(_.fold(statement.updateOne, statement.insertOne))

      override def readManyById(
          ids: Vector[Int]
        ): F[Vector[Todo.Existing[Int]]] =
        statement
          .selectAll
          .map(_.filter(todo => ids.contains(todo.id)))

      override def readManyByPartialDescription(
          partialDescription: String
        ): F[Vector[Todo.Existing[Int]]] =
        statement
          .selectAll
          .map { state =>
            state.filter(
              _.description
                .toLowerCase
                .contains(partialDescription.toLowerCase)
            )
          }

      override def readAll: F[Vector[Todo.Existing[Int]]] =
        statement.selectAll

      override def deleteMany(todos: Vector[Todo.Existing[Int]]): F[Unit] =
        statement.deleteMany(todos)

      override def deleteAll: F[Unit] =
        statement.deleteAll

    }
}
