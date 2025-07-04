package com.techiesbytes.service;

import com.techiesbytes.entity.department;

import java.util.List;

public interface departmentService {
    department save(department dept);

    List<department> getAllDept();

    department getDept(Long id);

    void deleteDepartmentById(Long id);

    department updateDept(Long id, department dept);

    department getDeptByCode(String code);
}
