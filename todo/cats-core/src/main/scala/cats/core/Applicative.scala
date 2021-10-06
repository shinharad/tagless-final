package cats

trait Applicative[F[_]] extends Apply[F]:
  def pure[A](a: A): F[A]

object Applicative:
  def apply[F[_]](using F: Applicative[F]): F.type = F
