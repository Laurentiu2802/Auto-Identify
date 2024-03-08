package org.example.business.impl.userIMPL;

import org.example.persistance.entity.UserEntity;
import org.example.domain.User;

public class UserConverter {

    private UserConverter(){

    }

    public static User convert(UserEntity user){
        if(user == null){
            return null;
        }
        return User.builder()
                .userID(user.getUserID())
                .username(user.getUsername())
                .password(user.getPassword())
                .description(user.getDescription())
                .build();
    }
}
