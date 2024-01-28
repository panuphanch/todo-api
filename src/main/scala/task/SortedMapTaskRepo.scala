package task

import scala.collection.mutable.SortedMap

class SortedMapTaskRepo {
	var todoList: SortedMap[Int, Todo] = SortedMap.empty[Int, Todo]
	var doingList: SortedMap[Int, Doing] = SortedMap.empty[Int, Doing]
	var doneList: SortedMap[Int, Done] = SortedMap.empty[Int, Done]

	def getPong(): String = "pong"

	def createNewTodo(todo: Todo):Int = {
		val item = todoList.lastOption
		val id = item match {
			case Some(value) => value._1 + 1
			case None => 0
		}
		todoList.addOne(id -> Todo(id, todo.title))
		id
	}

	def moveToDoing(todo: Todo): Option[Doing] = {
		val item = todoList.get(todo.id)
		val result = item match {
			case Some(value) => 
				todoList.remove(value.id)
				doingList.addOne(value.id -> Doing(value.id, value.title))
				Some(doingList.last._2)
			case None => None
		}
		result
	}

	def moveToDone(doing: Doing): Option[Done] = {
		val item = doingList.get(doing.id)
		val result = item match {
			case Some(value) => 
				doingList.remove(value.id)
				doneList.addOne(value.id -> Done(value.id, value.title))
				Some(doneList.last._2)
			case None => None
		}
		result
	}

	def revertToDoing(done: Done): Option[Doing] = {
		val item = doneList.get(done.id)
		val result = item match {
			case Some(value) =>
				doneList.remove(value.id)
				doingList.addOne(value.id -> Doing(value.id, value.title))
				Some(doingList.last._2)
			case None => None
		}
		result
	}

	def revertToTodo(doing: Doing): Option[Todo] = {
		val item = doingList.get(doing.id)
		val result = item match {
			case Some(value) => 
				doingList.remove(value.id)
				todoList.addOne(value.id -> Todo(value.id, value.title))
				Some(todoList.last._2)
			case None => None
		}
		result
	}

	def getAllItemInTodo(): List[Todo] = todoList.values.toList
	def getAllItemInDoing(): List[Doing] = doingList.values.toList
	def getAllItemInDone(): List[Done] = doneList.values.toList
}
