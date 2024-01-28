package task

import com.twitter.finatra.http.Controller
import com.twitter.finagle.http.Request
import com.google.inject.Inject

class TaskResource @Inject() (repo: SortedMapTaskRepo) extends Controller {

	get("/ping") { _: Request => repo.getPong() }

	post("/todo") { todo: Todo =>
		val id = repo.createNewTodo(todo)
		response.created(s"created $id")
	}

	post("/todo/next") { todo: Todo =>
		val result = repo.moveToDoing(todo)
		result match {
			case Some(value) => response.created(s"Item was moved to doing")
			case None => response.notFound(s"Item was not found")
		}
	}

	post("/doing/next") { doing: Doing =>
		val result = repo.moveToDone(doing)
		result match {
			case Some(value) => response.created(s"Item was moved to done")
			case None => response.notFound(s"Item was not found")
		}
	}

	get("/todo") { _:Request => repo.getAllItemInTodo() }
	get("/doing") { _:Request => repo.getAllItemInDoing() }
	get("/done") { _:Request => repo.getAllItemInDone() }
}
