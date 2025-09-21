package com.pucpr.devops.server;

import com.pucpr.devops.grpc.PasswordServiceGrpc;

import java.security.SecureRandom;

import com.pucpr.devops.grpc.PasswordRequest;
import com.pucpr.devops.grpc.PasswordResponse;

import io.grpc.stub.StreamObserver;

public class PasswordServiceImpl extends PasswordServiceGrpc.PasswordServiceImplBase {
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_+=<>?";

    public String generatePassword(int length, boolean includeNumbers, boolean includeSymbols, boolean includeUppercase) {
        String chars = LOWER;
        if (includeUppercase) chars += UPPER;
        if (includeNumbers) chars += NUMBERS;
        if (includeSymbols) chars += SYMBOLS;

        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            SecureRandom random = new SecureRandom();
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }

    @Override
    public void generatePassword(PasswordRequest request, StreamObserver<PasswordResponse> responseObserver) {
        String password = generatePassword(
            request.getLength(),
            request.getIncludeNumbers(),
            request.getIncludeSymbols(),
            request.getIncludeUppercase()
        );

        PasswordResponse response = PasswordResponse.newBuilder()
            .setPassword(password)
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void generateMultiplePasswords(PasswordRequest request, StreamObserver<PasswordResponse> responseObserver) {
        for (int i = 0; i < request.getQuantity(); i++) {
            String password = generatePassword(
                request.getLength(),
                request.getIncludeNumbers(),
                request.getIncludeSymbols(),
                request.getIncludeUppercase()
            );

            PasswordResponse response = PasswordResponse.newBuilder()
                .setPassword(password)
                .build();

            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }
}
