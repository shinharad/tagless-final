package cats

trait Monad[F[_]] extends Applicative[F] with FlatMap[F] {

}

