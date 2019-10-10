import io.opentracing.propagation.Format
import io.opentracing.propagation.TextMapAdapter
import io.opentracing.tag.Tags
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    BeanConfig.initTracer()

    val app: HttpHandler = routes(
        "/ping" bind GET to { _: Request -> Response(OK).body("pong!") },
        "/svc-c" bind GET to {
            val cSvc = BeanConfig.cSvc()
            Response(OK).body(cSvc.checkout())
        }
    )

    val openTracingFilter = Filter {
        next: HttpHandler ->
        {
            request: Request ->
            val headers = request.headers.toMap()
            val tracer = BeanConfig.tracer
            val extractedContext = tracer.extract(Format.Builtin.HTTP_HEADERS, TextMapAdapter(headers))

            if (extractedContext != null) {
                val span = tracer.buildSpan(request.method.name)
                    .asChildOf(extractedContext)
                    .withTag(Tags.SPAN_KIND.key, Tags.SPAN_KIND_SERVER)
                    .start()

                try {
                    tracer.scopeManager().activate(span)
                } catch (e: Exception) {
                    throw e
                } finally {
                    span.finish()
                }
            }

            next(request)
        }
    }

    val filteredApp = openTracingFilter.then(app)

    filteredApp.asServer(Jetty(9002)).start()

}