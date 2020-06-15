package com.olegshishkin.grpc;

import com.olegshishkin.grpc.proto.type.SimpleRq;
import com.olegshishkin.grpc.proto.type.SimpleServiceGrpc.SimpleServiceBlockingStub;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger("CLIENT");

    @GrpcClient("local-grpc-server")
    private SimpleServiceBlockingStub serviceBlockingStub;

    @PostConstruct
    public void setUp() throws InterruptedException {
        while (true) {
            TimeUnit.SECONDS.sleep(3);
            SimpleRq rq = SimpleRq.newBuilder()
                    .setName(UUID.randomUUID().toString())
                    .setAge(ThreadLocalRandom.current().nextInt())
                    .build();
            LOGGER.info("Rs: {}", serviceBlockingStub.call(rq).getMsg());
        }
    }

    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }
}
