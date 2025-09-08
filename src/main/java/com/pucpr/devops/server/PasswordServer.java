package com.pucpr.devops.server;

import java.io.IOException;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class PasswordServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(5003)
                .addService(new PasswordServiceImpl())
                .build();

        server.start();
        server.awaitTermination();
    }
}
