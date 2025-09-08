package com.pucpr.devops.client;

import com.pucpr.devops.grpc.PasswordServiceGrpc;
import com.pucpr.devops.grpc.PasswordRequest;
import com.pucpr.devops.grpc.PasswordResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class PasswordClient {
    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder
            .forAddress("localhost", 5003)
            .usePlaintext()
            .build();

        PasswordServiceGrpc.PasswordServiceBlockingStub stub = PasswordServiceGrpc.newBlockingStub(channel);

        // Unary call
        PasswordRequest unaryRequest = PasswordRequest.newBuilder()
            .setLength(12)
            .setIncludeNumbers(true)
            .setIncludeSymbols(true)
            .setIncludeUppercase(true)
            .build();

        PasswordResponse unaryResponse = stub.generatePassword(unaryRequest);
        System.out.println("Generated password (unary): " + unaryResponse.getPassword());

        // Server streaming call
        PasswordRequest streamRequest = PasswordRequest.newBuilder()
            .setLength(10)
            .setIncludeNumbers(true)
            .setIncludeSymbols(false)
            .setIncludeUppercase(true)
            .setQuantity(5)
            .build();

        System.out.println("Generated passwords (streaming):");
        stub.generateMultiplePasswords(streamRequest).forEachRemaining(response -> {
            System.out.println("→ " + response.getPassword());
        });

        channel.shutdown();
    }
}
