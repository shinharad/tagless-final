package cats

trait Semigroupal[F[_]] {
  def product[A, B](fa: F[A], fb: F[B]): F[(A, B)]
}

trait Applicative[F[_]] extends Functor[F] {
  def pure[A](a: A): F[A]
}
