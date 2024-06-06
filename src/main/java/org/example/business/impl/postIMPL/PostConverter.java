package org.example.business.impl.postIMPL;

import org.example.business.impl.userIMPL.UserConverter;
import org.example.domain.Post;
import org.example.domain.User;
import org.example.persistance.entity.PostEntity;

public class PostConverter {

    private PostConverter(){

    }

    public static Post convert(PostEntity post){
        User user = UserConverter.convert(post.getUser());
    return Post.builder()
            .postID(post.getPostID())
            .description(post.getDescription())
            .user(user)
            .build();
    }
}
