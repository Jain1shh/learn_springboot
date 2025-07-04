package com.techiesbytes.repository;

import com.techiesbytes.entity.department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface departmentRepo extends JpaRepository<department,Long> {

    department findByDCode(String code);
}
