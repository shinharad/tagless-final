package cats

trait Applicative[F[_]] extends Functor[F] {
  def pure[A](a: A): F[A]
}

trait Delay[F[_]] extends Applicative[F] with Defer[F] {
  def delay[A](a: => A): F[A] =
    defer(pure(a))
}
