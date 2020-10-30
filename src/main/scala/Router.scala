import akka.http.scaladsl.server.{Directives, Route}

trait Router {

    def route: Route

}

class TodoRouter(todoRepository: TodoRepository) extends Router with Directives {

    import de.heikoseebergeeeer.akkahttpcirce.FailFastCirceSupport._
    import io.circe.geneic.auto._

    override def route: Route = pathPrefix("todos") {
        pathEndOrSingleSlash {
            get {
                complete(todoRepository.all())
            }
        }
    }
}
