package com.techiesbytes.service;

import com.techiesbytes.entity.department;
import com.techiesbytes.repository.departmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class departmentServiceImpl implements departmentService {

    @Autowired
    departmentRepo deptRepo;

    @Override
    public department save(department dept) {
        return deptRepo.save(dept);
    }

    @Override
    public List<department> getAllDept() {
        return deptRepo.findAll();
    }

    @Override
    public department getDept(Long id) {
        return deptRepo.findById(id).get();
    }

    @Override
    public void deleteDepartmentById(Long id) {
        deptRepo.deleteById(id);
    }

    @Override
    public department updateDept(Long id, department dept) {
        department deptDb = deptRepo.findById(id).get();

        if(Objects.nonNull(dept.getdName()) && !"".equalsIgnoreCase(dept.getdName())){
            deptDb.setdName(dept.getdName());
        }

        if(Objects.nonNull(dept.getdCode()) && !"".equalsIgnoreCase(dept.getdCode())){
            deptDb.setdCode(dept.getdCode());
        }

        if(Objects.nonNull(dept.getdAddress()) && !"".equalsIgnoreCase(dept.getdAddress())){
            deptDb.setdAddress(dept.getdAddress());
        }
        return deptRepo.save(deptDb);
    }

    @Override
    public department getDeptByCode(String code) {
        return deptRepo.findByDCode(code);
    }
}
