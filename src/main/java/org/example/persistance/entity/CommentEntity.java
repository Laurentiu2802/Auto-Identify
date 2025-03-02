package org.example.persistance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "s3_comment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentID")
    private Long commentID;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "postID")
    private PostEntity post;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "userID")
    private UserEntity user;

    @Length(min = 1, max = 100)
    @Column(name = "description")
    private String description;


}
