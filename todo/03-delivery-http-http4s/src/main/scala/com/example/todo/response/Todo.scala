package com.example
package todo
package response

import java.time.format.DateTimeFormatter

import io.circe._
import io.circe.generic.semiauto._

import org.http4s._
import org.http4s.circe._

import com.{ example => domain }
import com.example.Todo.Existing

final case class Todo(
    id: String,
    description: String,
    deadline: String
  )

object Todo {
  def apply(
      pattern: DateTimeFormatter
    )(
      existing: domain.Todo.Existing
    ): Todo =
    Todo(
      id = existing.id.toString,
      description = existing.data.description,
      deadline = existing.data.deadline.format(pattern)
    )

  implicit val encoder: Encoder[Todo] =
    deriveEncoder

  implicit def entityEncoder[F[_]]: EntityEncoder[F, Todo] =
    jsonEncoderOf
}