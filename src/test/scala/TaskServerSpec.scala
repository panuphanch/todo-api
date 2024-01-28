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

}