package com.techiesbytes.entity;

import com.techiesbytes.controller.DepartmentController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
// Or @Data
@Entity
public class department {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dId;

    @NotBlank(message = "dName is required")
    private String dName;

    @Length(max = 200, min = 1)
    // or @Size(max = 200, min = 100)
    private String dAddress;
    private String dCode;

}
