package cats

trait Traverse[F[_]] extends Functor[F] {
  def traverse[G[_]: Applicative, A, B](fa: F[A])(agb: A => G[B]): G[F[B]]
}
