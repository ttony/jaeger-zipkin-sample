# Jaeger and Zipkin integration in OpenTracing world

To run the sample, you need to include VM Options into each project

 ```java
-javaagent:<PATH_TO_ASPECTJ>/aspectjweaver-1.9.4.jar
```

Start local Jaeger Collector

```sh
docker run -it  -e COLLECTOR_ZIPKIN_HTTP_PORT=9411 -e LOG_LEVEL=debug   -p 5775:5775/udp   -p 6831:6831/udp   -p 6832:6832/udp   -p 5778:5778   -p 16686:16686   -p 14268:14268   -p 9411:9411   jaegertracing/all-in-one:1.14
```

Go to URL:

```
http://localhost:9000/svc-a/helloworld
```

You will get traces something like this:

![Image of Traces](https://github.com/ttony/jaeger-zipkin-sample/blob/master/JaegerUI.png)

![Image of Traces](https://github.com/ttony/jaeger-zipkin-sample/blob/master/JaegerUIDetail.png)
