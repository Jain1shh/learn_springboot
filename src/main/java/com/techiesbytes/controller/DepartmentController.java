package com.techiesbytes.controller;


import com.techiesbytes.entity.department;
import com.techiesbytes.exception.DepartmentNotFoundException;
import com.techiesbytes.service.departmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    private departmentService deptService;

    private final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @PostMapping("/saveDepartmnt")
    public department saveDepartment(@Validated @RequestBody department dept){
        LOGGER.info("In saveDepartmnt method of DepartmentController.class");
        return deptService.save(dept);
    }

    @GetMapping("/getDepts")
    public List<department> getAllDepartments(){
        LOGGER.info("In getAllDepartments method of DepartmentController.class");
        return deptService.getAllDept();
    }

    @GetMapping("/getDept/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable("id") Long id){
        LOGGER.info("In getDepartmentById method of DepartmentController.class");
        return deptService.getDept(id);
    }

    @GetMapping("/getDeptByCode/{code}")
    public department getDepartmentByCode(@PathVariable("code") String code){
        LOGGER.info("In getDepartmentByCode method of DepartmentController.class");
        return deptService.getDeptByCode(code);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDepartmentById(@PathVariable("id") Long id){
        LOGGER.info("In deleteDepartmentById method of DepartmentController.class");
        deptService.deleteDepartmentById(id);
        return "Department deleted successfully";
    }

    @PutMapping("/update/{id}")
    public department updateDepartment(@PathVariable("id") Long id,@RequestBody department dept){
        LOGGER.info("In updateDepartmentById method of DepartmentController.class");
        return deptService.updateDept(id, dept);
    }
}
