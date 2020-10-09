package com.example
package todo
package crud

import java.time.format.DateTimeFormatter

import scala.util.chaining._

import cats._
import cats.implicits._

import io.circe.syntax._

import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router

object Controller {
  def dsl[F[_]: Defer: Monad](
      pattern: DateTimeFormatter,
      boundary: Boundary[F]
    ): Controller[F] =
    new Controller[F] with Http4sDsl[F] {
      override def routes: HttpRoutes[F] =
        Router {
          "todos" -> HttpRoutes.of {
            case GET -> Root :? Description(d) => searchByDescription(d)
            case GET -> Root                   => showAll
            case GET -> Root / id              => searchById(id)

            case DELETE -> Root      => deleteAll
            case DELETE -> Root / id => delete(id)
          }
        }

      object Description extends QueryParamDecoderMatcher[String]("description")

      private val showAll: F[Response[F]] =
        boundary.readAll.flatMap { todos =>
          todos
            .sortBy(_.deadline)
            .map(response.Todo(pattern))
            .asJson
            .pipe(Ok(_))
        }

      private def searchById(id: String): F[Response[F]] =
        withIdPrompt(id) { id =>
          withReadOne(id) { todo =>
            todo
              .pipe(response.Todo(pattern))
              .pipe(_.asJson)
              .pipe(Ok(_))
          }
        }

      private def searchByDescription(description: String): F[Response[F]] =
        boundary.readManyByPartialDescription(description).flatMap { todos =>
          todos
            .map(response.Todo(pattern))
            .asJson
            .pipe(Ok(_))
        }

      private def delete(id: String): F[Response[F]] =
        withIdPrompt(id) { id =>
          withReadOne(id) { todo =>
            boundary.deleteOne(todo) >>
              NoContent()
          }
        }

      private def withIdPrompt(
          id: String
        )(
          onValidId: String => F[Response[F]]
        ): F[Response[F]] =
        id.pipe(toId).pipe {
          case Right(id)   => onValidId(id)
          case Left(error) => BadRequest(error)
        }

      private def toId(userInput: String): Either[String, String] =
        if (userInput.isEmpty || userInput.contains(" "))
          Left(s"\n$userInput is not a valid id.")
        else
          Right(userInput)

      private def withReadOne(
          id: String
        )(
          onFound: Todo.Existing => F[Response[F]]
        ): F[Response[F]] =
        boundary
          .readOneById(id)
          .flatMap {
            case Some(todo) => onFound(todo)
            case None       => displayNoTodosFoundMessage
          }

      private val displayNoTodosFoundMessage: F[Response[F]] =
        NotFound("No todos found!")

      private val deleteAll: F[Response[F]] =
        boundary.deleteAll >> NoContent()
    }
}
