package task

import com.twitter.finatra.http.Controller
import com.twitter.finagle.http.Request
import scala.collection.mutable.SortedMap

class TaskResource extends Controller {
	get("/ping") { _: Request => "pong" }
}
