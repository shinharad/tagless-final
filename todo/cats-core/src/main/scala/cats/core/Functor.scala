package cats

trait Functor[F[_]]:
  extension[A] (fa: F[A])
    def map[B](ab: A => B): F[B]

    def as[B](b: B): F[B] =
      map(_ => b)

    def void: F[Unit] =
      map(_ => ())
