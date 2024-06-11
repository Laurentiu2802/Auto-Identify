package org.example.persistance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "s3_model")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "modelID")
    private long modelID;

    @Length(min = 1, max = 50)
    @Column(name = "modelName")
    private String modelName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "brandID")
    private CarBrandEntity carBrand;
}
