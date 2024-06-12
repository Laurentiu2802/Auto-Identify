package org.example.persistance.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="s3_post")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postID")
    private Long postID;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "userID")
    private UserEntity user;

    @Length(min =5, max = 200)
    @Column(name = "description")
    private String description;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "categoryID")
    private CategoryEntity category;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "brandID")
    private CarBrandEntity carBrand;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "modelID")
    private CarModelEntity carModel;

}
