package cats

trait Applicative[F[_]] extends Functor[F] {
  def pure[A](a: A): F[A]
}

trait Defer[F[_]] {
  def defer[A](fa: => F[A]): F[A]
}
