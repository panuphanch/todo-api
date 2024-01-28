package task

trait Task {
	val id: Int
	val title: String
}

case class Todo(id: Int, title: String) extends Task {
}

case class Doing(id: Int, title: String) extends Task {
}

case class Done(id: Int, title: String) extends Task {
}
