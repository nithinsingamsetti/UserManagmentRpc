package com.genecrusher.service.impl;

import com.genecrusher.*;
import com.genecrusher.dao.User;
import com.genecrusher.utils.DbUtil;
import com.genecrusher.utils.HashUtil;
import com.genecrusher.utils.SqlUtil;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;

import java.util.Arrays;

public class UserDetailsServiceImpl extends UserDetailsServiceGrpc.UserDetailsServiceImplBase {
    
    @Override
    public void createUser(UserCreationRequest request, StreamObserver<UserCreationResponse> responseObserver) {
        Timestamp timestamp = Timestamp.newBuilder().getDefaultInstanceForType();

        DbUtil.executeStatement(DbUtil.getConnection(), SqlUtil.constructInsertUserStatement(request.getLoginName(),
                HashUtil.hashMe(request.getPassword()), request.getEmail(), String.join(",", request.getRolesList()), "GENECRUSHER"));
        UserCreationResponse creationResponse = UserCreationResponse.newBuilder().setLoginName(request.getLoginName())
                .setCreatedMessage("Successfully created user: " + request.getLoginName())
                .setCreatedTime(timestamp).build();
        responseObserver.onNext(creationResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void updateUser(UserUpdateRequest request, StreamObserver<UserUpdateResponse> responseObserver) {
        //TODO:Fetch user from db and update the field
        Timestamp timestamp = Timestamp.newBuilder().getDefaultInstanceForType();
        UserUpdateResponse userUpdateResponse = UserUpdateResponse.newBuilder().setLoginName(request.getLoginName())
                .setUpdatedMessage("Successfully updated user: " + request.getLoginName()).setUpdatedTime(timestamp)
                .build();
        responseObserver.onNext(userUpdateResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getUser(UserFetchRequest request, StreamObserver<UserResponse> responseObserver) {
        User fetchedUser = DbUtil.executeStatementAndFetchUser(DbUtil.getConnection(), SqlUtil.constructFetchUserStatement(request.getLoginName()));
        Timestamp timestamp = Timestamp.newBuilder().getDefaultInstanceForType();
        UserResponse userResponse = null;
        if (fetchedUser != null) {
            userResponse = UserResponse.newBuilder().setLoginName(fetchedUser.getLoginName())
                    .setCreatedTime(timestamp).setEmail(fetchedUser.getEmail()).addAllRoles(Arrays.asList(fetchedUser.getRoles().split(",")))
                    .build();
        }
        responseObserver.onNext(userResponse);
        responseObserver.onCompleted();
    }
}
