package org.example.persistance.entity;

import lombok.Builder;
import lombok.Data;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="s3_user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userID")
    private Long userID;

    @Column(name="username")
    private  String username;

    @Column(name="password")
    @Length(max = 5000)
    private  String password;

    @Column(name="description")
    private String description;

@ManyToOne
@JoinColumn(name = "user_role_id")
    private UserRoleEntity role;
}
