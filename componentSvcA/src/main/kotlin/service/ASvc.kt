package service

import io.opentracing.contrib.annotation.NewSpan
import io.opentracing.contrib.okhttp3.TracingCallFactory
import io.opentracing.util.GlobalTracer
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request

class ASvc {

    @NewSpan
    fun greet(name: String?): String {
        InternalProcessor().process()
        doHandshake()
        doCheckout()
        return "hello $name"
    }

    @NewSpan
    fun doCheckout() {
        val tracer = GlobalTracer.get()
        val client = TracingCallFactory(OkHttpClient(), tracer)

        val request = Request.Builder()
            .url("http://localhost:9002/svc-c")
            .build()

        val networkResponse = client.newCall(request).execute()
        println(networkResponse)
    }

    @NewSpan
    fun doHandshake() {
        val tracer = GlobalTracer.get()
        val client = TracingCallFactory(OkHttpClient(), tracer)

        val request = Request.Builder()
            .url("http://localhost:9001/svc-b")
            .build()

        val networkResponse = client.newCall(request).execute()
        println(networkResponse)
    }
}