
import io.jaegertracing.Configuration
import io.jaegertracing.Configuration.*
import io.opentracing.util.GlobalTracer
import service.BSvc


object BeanConfig {

    fun initTracer() {
        val samplerConfig = SamplerConfiguration().withType("const").withParam(1)
        val reporterConfig = ReporterConfiguration()
            .withLogSpans(true)
        val codecConfig = CodecConfiguration().withPropagation(Propagation.B3)
        val tracer = Configuration("componentSvcB")
            .withCodec(codecConfig)
            .withSampler(samplerConfig)
            .withReporter(reporterConfig)
            .withTraceId128Bit(true)
            .tracer
        GlobalTracer.registerIfAbsent { tracer }
    }

    fun bSvc(): BSvc {
        return BSvc()
    }
}