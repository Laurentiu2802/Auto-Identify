package org.example.business.impl.commentIMPL;

import org.example.business.impl.postIMPL.PostConverter;
import org.example.business.impl.userIMPL.UserConverter;
import org.example.domain.Comment;
import org.example.domain.Post;
import org.example.domain.User;
import org.example.persistance.entity.CommentEntity;

public class CommentConverter {
    private CommentConverter(){

    }

    public static Comment convert(CommentEntity comment){
        User user = UserConverter.convert(comment.getUser());
        Post post = PostConverter.convert(comment.getPost());

        return Comment.builder()
                .commentID(comment.getCommentID())
                .user(user)
                .post(post)
                .description(comment.getDescription())
                .build();

    }
}
