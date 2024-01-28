package task

import com.twitter.finatra.http.Controller
import com.twitter.finagle.http.Request
import scala.collection.mutable.SortedMap

class TaskResource extends Controller {
	var todoList: SortedMap[Int, Todo] = SortedMap.empty[Int, Todo]

	get("/ping") { _: Request => "pong" }

	post("/todo") { todo: Todo => 
		val item = todoList.lastOption
		val id = item match {
			case Some(value) => value._1 + 1
			case None => 0
		}
		todoList.addOne(id -> Todo(id, todo.title))
		response.created(s"created $id")
	}

	get("/todo") { _:Request => todoList.values.toList }
}
