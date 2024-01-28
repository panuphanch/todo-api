import com.twitter.inject.server.FeatureTest
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.finagle.http.Status

class TaskServerSpec extends FeatureTest {
  
	override val server: EmbeddedHttpServer = new EmbeddedHttpServer(
		twitterServer = new TaskServer
	)

	test("Ping should return pong") {
		server.httpGet(path = "/ping", andExpect = Status.Ok)
	}

	test("Create new todo should return status created with id equal to 0") {
		server.httpPost(
			path = "/todo", 
			postBody = """
				|{
				|	"id": -1,
				|	"title": "buy banana"
				|}
			""".stripMargin,
			andExpect = Status.Created,
			withBody = "created 0"
		)
	}

	test("Move unavailable item to doing should return status notFound and json body is 'Item was not found'") {
		server.httpPost(
			path = "/todo/next",
			postBody = """
				|{
				|	"id": 1,
				|	"title": "buy apple"
				|}
			""".stripMargin,
			andExpect = Status.NotFound,
			withJsonBody = "Item was not found"
		)
	}

	test("Move item to doing should return status created and json body is 'Item was moved to doing'") {
		server.httpPost(
			path = "/todo/next",
			postBody = """
				|{
				|	"id": 0,
				|	"title": "buy banana"
				|}
			""".stripMargin,
			andExpect = Status.Created,
			withJsonBody = "Item was moved to doing"
		)
	}

	test("After move item to doing todo list should be empty") {
		server.httpGet(
			path = "/todo",
			withJsonBody = "[]"
		)
	}

	test("After move item to doing, doing list should contain 1 item") {
		server.httpGet(
			path = "/doing",
			withJsonBody = """[{"id":0, "title":"buy banana"}]"""
		)
	}

	test("Move unavailable item to done should return status notFound and json body is 'Item was not found'") {
		server.httpPost(
			path = "/doing/next",
			postBody = """
				|{
				|	"id": 1,
				|	"title": "buy apple"
				|}
			""".stripMargin,
			andExpect = Status.NotFound,
			withJsonBody = "Item was not found"
		)
	}

	test("Move item to done should return status created and json body is 'Item was moved to done'") {
		server.httpPost(
			path = "/doing/next",
			postBody = """
				|{
				|	"id": 0,
				|	"title": "buy banana"
				|}
			""".stripMargin,
			andExpect = Status.Created,
			withJsonBody = "Item was moved to done"
		)
	}

	test("After move item to done doing list should be empty") {
		server.httpGet(
			path = "/doing",
			withJsonBody = "[]"
		)
	}

	test("After move item to done, done list should contain 1 item") {
		server.httpGet(
			path = "/done",
			withJsonBody = """[{"id":0, "title":"buy banana"}]"""
		)
	}

	test("Move unavailable item back to doing should return status notFound and json body is 'Item was not found'") {
		server.httpPost(
			path = "/done/revert",
			postBody = """
				|{
				|	"id": 1,
				|	"title": "buy apple"
				|}
			""".stripMargin,
			andExpect = Status.NotFound,
			withJsonBody = "Item was not found"
		)
	}

	test("Moving item back from done to doing should return status created and JSON body 'Item was moved back to doing'") {
		server.httpPost(
			path = "/done/revert",
			postBody = """
				|{
				|	"id": 0,
				|	"title": "buy banana"
				|}
			""".stripMargin,
			andExpect = Status.Created,
			withJsonBody = "Item was moved back to doing"
		)
	}

	test("After moving item back to doing, the done list should be empty") {
		server.httpGet(
			path = "/done",
			withJsonBody = "[]"
		)
	}

	test("After moving item back to doing, the doing list should contain 1 item") {
		server.httpGet(
			path = "/doing",
			withJsonBody = """[{"id":0, "title":"buy banana"}]"""
		)
	}

	test("Move unavailable item back to todo should return status notFound and json body is 'Item was not found'") {
		server.httpPost(
			path = "/doing/revert",
			postBody = """
				|{
				|	"id": 1,
				|	"title": "buy apple"
				|}
			""".stripMargin,
			andExpect = Status.NotFound,
			withJsonBody = "Item was not found"
		)
	}

	test("Moving item back from doing to todo should return status created and JSON body 'Item was moved back to todo'") {
		server.httpPost(
			path = "/doing/revert",
			postBody = """
				|{
				|	"id": 0,
				|	"title": "buy banana"
				|}
			""".stripMargin,
			andExpect = Status.Created,
			withJsonBody = "Item was moved back to todo"
		)
	}

	test("After moving item back to todo, the doing list should be empty") {
		server.httpGet(
			path = "/doing",
			withJsonBody = "[]"
		)
	}

	test("After moving item back to todo, the todo list should contain 1 item") {
		server.httpGet(
			path = "/todo",
			withJsonBody = """[{"id":0, "title":"buy banana"}]"""
		)
	}
}
