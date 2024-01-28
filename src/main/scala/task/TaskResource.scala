package task

import com.twitter.finatra.http.Controller
import com.twitter.finagle.http.Request
import scala.collection.mutable.SortedMap

class TaskResource extends Controller {
	var todoList: SortedMap[Int, Todo] = SortedMap.empty[Int, Todo]
	var doingList: SortedMap[Int, Doing] = SortedMap.empty[Int, Doing]

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

	post("/todo/next") { todo: Todo =>
		val item = todoList.get(todo.id)
		val result = item match {
			case Some(value) => 
				todoList.remove(value.id)
				doingList.addOne(value.id -> Doing(value.id, value.title))
				Some(doingList.last._2)
			case None => None
		}

		result match {
			case Some(value) => response.created(s"Item was moved to doing")
			case None => response.notFound(s"Item was not found")
		}
	}

	get("/todo") { _:Request => todoList.values.toList }
	get("/doing") { _:Request => doingList.values.toList }
}
