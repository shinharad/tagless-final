package cats

trait Traverse[F[_]] extends Functor[F]:
  def sequence[G[_]: Applicative, A](fa: F[G[A]]): G[F[A]] =
    traverse(fa)(identity)

  def traverse[G[_]: Applicative, A, B](fa: F[A])(agb: A => G[B]): G[F[B]]

object Traverse:
  def apply[F[_]](using F: Traverse[F]): F.type = F
