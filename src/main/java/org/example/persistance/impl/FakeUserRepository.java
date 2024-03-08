package org.example.persistance.impl;

import org.example.persistance.UserRepository;
import org.example.persistance.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class FakeUserRepository implements UserRepository {
    private static long userID = 1;

    private List<UserEntity> users;

    public FakeUserRepository(){
        this.users = new ArrayList<>();
    }

    @Override
    public Optional<UserEntity> getUserByID(Long userID){
        return this.users.stream()
                .filter(userEntity -> userEntity.getUserID().equals(userID))
                .findFirst();
    }

    @Override
    public UserEntity save(UserEntity user){
        if(user.getUserID() == null){
            user.setUserID(userID);
            userID++;
            this.users.add(user);
        }
        return user;
    }

    @Override
    public void deleteByID(Long userID){
        this.users.removeIf(userEntity -> userEntity.getUserID().equals(userID));
    }

    @Override
    public List<UserEntity> getAllUsers(){
        return Collections.unmodifiableList(this.users);
    }

    @Override
    public Optional<UserEntity> findByID(long userID){
        return this.users.stream()
                .filter(userEntity -> userEntity.getUserID().equals(userID))
                .findFirst();
    }

}
