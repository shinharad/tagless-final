package object fplibrary {
  final implicit class FunctorOps[F[_]: Functor, A](private val fa: F[A]) {
    def map[B](ab: A => B): F[B] =
      implicitly[Functor[F]].map(fa)(ab)
  }

  final implicit class AnyOps[A](private val a: A) extends AnyVal {
    def pure[F[_]: Applicative]: F[A] =
      implicitly[Applicative[F]].pure(a)
  }
}
