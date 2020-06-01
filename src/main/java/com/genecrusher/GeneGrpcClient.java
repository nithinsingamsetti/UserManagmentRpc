package com.genecrusher;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.Random;

public class GeneGrpcClient {
    private static final String HOST = "192.168.56.1";
    private static final int PORT = 9000;

    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(HOST, PORT).usePlaintext().
                build();

        UserDetailsServiceGrpc.UserDetailsServiceBlockingStub stub
                = UserDetailsServiceGrpc.newBlockingStub(managedChannel);

        Random rand = new Random();
        int randomNum = 99999 + rand.nextInt((99999 - 9999) + 1);

        createUser(stub, randomNum);
        getUser(stub, randomNum);
    }

    private static void getUser(UserDetailsServiceGrpc.UserDetailsServiceBlockingStub stub, int randomNum) {
        UserResponse userResponse = stub.getUser(UserFetchRequest.newBuilder().setLoginName("user" + randomNum).build());
        System.out.println("Fetched user with loginName: " + "user " + randomNum);
        System.out.println(userResponse.toString());
    }

    private static void createUser(UserDetailsServiceGrpc.UserDetailsServiceBlockingStub stub, int randomNum) {
        UserCreationRequest userCreationRequest = UserCreationRequest.newBuilder().setLoginName("user" + randomNum)
                .setEmail("user" + randomNum + "@genecrusher.com").setPassword("Gene@123").addAllRoles(new ArrayList<String>() {{
                    add("GC_ADMIN");
                    add("GC_DEVELOPER");
                    add("GC_PRODUCT");
                }}).build();

        UserCreationResponse response = stub.createUser(userCreationRequest);
        System.out.println("Response after creation: " + response.getCreatedMessage());
    }
}
