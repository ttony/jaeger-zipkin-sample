
import brave.Tracing
import brave.opentracing.BraveTracer
import io.opentracing.Tracer
import service.CSvc
import zipkin2.reporter.AsyncReporter
import zipkin2.reporter.okhttp3.OkHttpSender


object BeanConfig {

    lateinit var tracer: Tracer

    fun initTracer() {
        val sender = OkHttpSender.create("http://localhost:9411/api/v2/spans")
        val reporter = AsyncReporter.create(sender)
        val braveTracing = Tracing.newBuilder()
            .localServiceName("componentSvcC")
            .traceId128Bit(true)
            .spanReporter(reporter)
            .build()

        tracer = BraveTracer.create(braveTracing)
    }

    fun cSvc(): CSvc {
        return CSvc()
    }
}