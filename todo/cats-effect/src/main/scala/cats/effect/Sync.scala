package cats
package effect

trait Sync[F[_]] extends Monad[F] with Defer[F]:
  def delay[A](a: => A): F[A] =
    defer(pure(a))

object Sync:
  def apply[F[_]](using F: Sync[F]): F.type = F