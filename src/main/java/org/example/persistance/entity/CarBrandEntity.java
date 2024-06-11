package org.example.persistance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "s3_brand")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarBrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brandID")
    private long brandID;

    @Length(min = 3, max = 50)
    @Column(name = "brandName")
    private String brandName;
}
