package com.tn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data // tạo get/set/toString()
@NoArgsConstructor  // tạo sẵn controcter ko có tham số
@AllArgsConstructor // tạo sẵn controcter có tất cả tham số
@Entity
@Table
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    private String departmentName ;

    @OneToMany(mappedBy = "department")
    private List<Account> accounts ;
}
