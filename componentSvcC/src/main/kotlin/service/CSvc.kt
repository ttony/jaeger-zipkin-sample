package service

import brave.Tracer.SpanInScope
import io.opentracing.contrib.annotation.NewSpan


class CSvc {

    @NewSpan
    fun checkout(): String {
        Thread.sleep(600)
        return "checkout"
    }
}