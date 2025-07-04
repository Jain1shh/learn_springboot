package com.techiesbytes.entity;

import com.techiesbytes.controller.DepartmentController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;


//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
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

    public department() {
    }

    public department(Long dId, String dName, String dAddress, String dCode) {
        this.dId = dId;
        this.dName = dName;
        this.dAddress = dAddress;
        this.dCode = dCode;
    }

    public Long getdId() {
        return dId;
    }

    public void setdId(Long dId) {
        this.dId = dId;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getdAddress() {
        return dAddress;
    }

    public void setdAddress(String dAddress) {
        this.dAddress = dAddress;
    }

    public String getdCode() {
        return dCode;
    }

    public void setdCode(String dCode) {
        this.dCode = dCode;
    }
}
