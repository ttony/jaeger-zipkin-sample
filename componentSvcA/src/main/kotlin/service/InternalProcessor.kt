package service

import io.opentracing.contrib.annotation.NewSpan

class InternalProcessor {

    @NewSpan
    fun process() {
        println("processing some stuff")
        Thread.sleep(2000)
    }
}