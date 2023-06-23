package com.tn.controller;

import com.tn.dto.DepartmentDTO;
import com.tn.entity.Account;
import com.tn.entity.Department;
import com.tn.repository.DeparmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*")

public class DeparmentController {
    @Autowired
    private DeparmentRepository deparmentRepo ;

    // thêm mới deparment
    @PostMapping("deparment")
    public ResponseEntity<?> creat(@RequestBody Department department) {
        System.out.println(department);

        deparmentRepo.save(department);
        return new ResponseEntity<>("Create thành công", HttpStatus.OK) ;
    }

    // show all deparment
    @GetMapping("deparment")
    public ResponseEntity<?> getAll() {
        List<Department> departments = deparmentRepo.findAll() ;

        List<DepartmentDTO> departmentDTOS = new ArrayList<>() ;

            for (int i = 0; i < departments.size(); i++) {
                DepartmentDTO departmentDTO = new DepartmentDTO() ;

                departmentDTO.setId(departments.get(i).getId());
                departmentDTO.setDepartmentName(departments.get(i).getDepartmentName());

                departmentDTOS.add(departmentDTO);

        }
        return new ResponseEntity<>(departmentDTOS, HttpStatus.OK) ;
    }

    // get by id
    @GetMapping("department/{id}")
    public ResponseEntity<?> getById (@PathVariable Integer id) {
        Optional<Department> department = deparmentRepo.findById(id);

        if (department.isEmpty()){
            return new ResponseEntity<>("id ko tồn tại "+ id, HttpStatus.BAD_REQUEST ) ;
        }

        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(department.get().getId());
        departmentDTO.setDepartmentName(department.get().getDepartmentName());

        return new ResponseEntity<>(departmentDTO, HttpStatus.OK) ;
    }

    // update by id
    @PutMapping("deparment/{id}")
    public ResponseEntity<?> updateById (@PathVariable Integer id) {
        Optional<Department> department = deparmentRepo.findById(id);
        System.out.println(id);

        if (department.isEmpty()){
            return new ResponseEntity<>("id ko tồn tại "+ id, HttpStatus.BAD_REQUEST ) ;
        }

//        Department department1 = department.get() ;
//        department1.setDepartmentName("Bán hàng");
//        deparmentRepo.save(department1);
        return new ResponseEntity<>("Update thành công", HttpStatus.OK) ;
    }

    // update department
    @PutMapping("deparment")
    public ResponseEntity<?> update (@RequestBody Department department) {

        deparmentRepo.save(department);
        return new ResponseEntity<>("Update thành công", HttpStatus.OK) ;
    }

    // delete by id
    @DeleteMapping("deparment/{id}")
    public ResponseEntity<?> delete (@PathVariable Integer id) {
        Optional<Department> department = deparmentRepo.findById(id);
        System.out.println("Run in delete deparment");

        if (department.isEmpty()){
            return new ResponseEntity<>("id ko tồn tại "+ id, HttpStatus.BAD_REQUEST ) ;
        }

        List<Account> accounts = department.get().getAccounts();

        if (accounts.size() >0) {
            return new ResponseEntity<>("Department is user by account with department id: "+ id, HttpStatus.BAD_REQUEST) ;
        }

        deparmentRepo.deleteById(id);
        return new ResponseEntity<>("delete thành công", HttpStatus.OK) ;
    }

}
