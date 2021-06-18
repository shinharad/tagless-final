package com.example
package todo
package crud

import java.time.LocalDateTime

import cats._
import cats.implicits._

import cats.effect.Ref

trait Statement[F[_]] {
  def insertOne(
      description: String,
      deadline: LocalDateTime
    ): F[Todo.Existing[Int]]
  def updateOne(id: Int, data: Todo.Data): F[Todo.Existing[Int]]
  def selectAll: F[Vector[Todo.Existing[Int]]]
  def deleteMany(todos: Vector[Todo.Existing[Int]]): F[Unit]
  def deleteAll: F[Unit]
}

object Statement {
  def dsl[F[_]: Functor: FlatMap](
      state: Ref[F, Vector[Todo.Existing[Int]]]
    ): Statement[F] =
    new Statement[F] {
      override val selectAll: F[Vector[Todo.Existing[Int]]] =
        state.get

      private val nextId: F[Int] =
        selectAll.map(_.size)

      override def insertOne(
          description: String,
          deadline: LocalDateTime
        ): F[Todo.Existing[Int]] =
        nextId
          .map(Todo.Existing[Int](_, Todo.Data(description, deadline)))
          .flatMap { created =>
            state.modify(s => (s :+ created) -> created)
          }

      override def updateOne(id: Int, data: Todo.Data): F[Todo.Existing[Int]] =
        state.modify { s =>
          val todo = Todo.Existing(id, data)
          (s.filterNot(_.id === todo.id) :+ todo) -> todo
        }

      override def deleteMany(todos: Vector[Todo.Existing[Int]]): F[Unit] =
        state.update(_.filterNot(todo => todos.map(_.id).contains(todo.id)))

      override val deleteAll: F[Unit] =
        state.set(Vector.empty)
    }
}
