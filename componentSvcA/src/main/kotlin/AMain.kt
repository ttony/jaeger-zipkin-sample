import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    BeanConfig.initTracer()

    val app: HttpHandler = routes(
        "/ping" bind GET to { _: Request -> Response(OK).body("pong!") },
        "/svc-a/{name}" bind GET to { req: Request ->
            val name: String? = req.path("name")
            val aSvc = BeanConfig.aSvc()
            Response(OK).body(aSvc.greet(name))
        }
    )

    app.asServer(Jetty(9000)).start()

}