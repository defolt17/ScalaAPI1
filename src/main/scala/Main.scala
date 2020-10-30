import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http

import scala.util.{Failure, Success}

object Main extends App {
    val host = "0.0.0.0"
    val port = 9000

    implicit val system: ActorSystem = ActorSystem(name = "todoapi")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    import system.dispatcher
    import akka.http.scaladsl.server.Directives._
    def route = path("hello") {
        get {
            complete("Hello, World!")
        }
    }

    val binding = Http().bindAndHandle(route, host, port)
    binding.onComplete {
        case Success(_) => println("YES! YES! YES! \n NICE~E!")
        case Failure(error) => println("NO! NO! NO! \n NICE~E!")
    }


}
