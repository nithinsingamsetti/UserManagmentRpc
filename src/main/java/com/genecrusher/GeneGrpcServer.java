package com.genecrusher;

import com.genecrusher.service.impl.UserDetailsServiceImpl;
import com.genecrusher.utils.DbUtil;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GeneGrpcServer {
    private static final int SERVER_PORT = 9000;

    public static void main(String[] args) throws InterruptedException, IOException {
        Server server = ServerBuilder.forPort(SERVER_PORT).addService(new UserDetailsServiceImpl())
                .build();

        DbUtil.checkAndCreateTable();

        System.out.println("Server starting");
        server.start();
        System.out.println("Server started");
        server.awaitTermination();
    }
}
