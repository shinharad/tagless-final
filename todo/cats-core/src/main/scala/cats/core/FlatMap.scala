package cats

trait FlatMap[F[_]]:
  extension[A] (fa: F[A])
    def flatMap[B](afb: A => F[B]): F[B]

    def >>[B](fb: => F[B]): F[B] =
      flatMap(_ => fb)
