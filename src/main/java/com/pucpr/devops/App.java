package com.pucpr.devops;

import java.io.IOException;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import com.pucpr.devops.server.PasswordServiceImpl;

public class App {

    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(5003)
                .addService(new PasswordServiceImpl())
                .build();

        server.start();
        server.awaitTermination();
    }
}