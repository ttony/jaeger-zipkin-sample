package service

import io.opentracing.contrib.annotation.NewSpan

class BSvc {

    @NewSpan
    fun handshake(): String {
        Thread.sleep(1500)
        return "handshake"
    }
}