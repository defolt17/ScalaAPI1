import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.Await
import scala.util.{Failure, Success}

object Main extends App {
    val host = "0.0.0.0"
    val port = 9000

    implicit val system: ActorSystem = ActorSystem(name = "todoapi")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    import system.dispatcher

    val todoRepository = new InMemoryTodoRepository(Seq(
        Todo("1", "Buy eggs", "ran out of eggs", false),
        Todo("2", "Buy milk", "The cat is thirsty", true)
    ))

    val router = new TodoRouter(todoRepository)
    val server = new Server(router, host, port)

    val binding = server.bind()
    binding.onComplete {
        case Success(_) => println("YES! YES! YES! \n NICE~E!")
        case Failure(error) => println(s"NO! NO! NO! \n ${error.getMessage}")
    }

    import scala.concurrent.duration._
    Await.result(binding, 3.seconds)


}
