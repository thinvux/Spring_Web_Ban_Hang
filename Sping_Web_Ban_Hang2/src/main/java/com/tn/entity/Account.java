package com.tn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data // tạo get/set
@NoArgsConstructor  // tạo sẵn controcter ko có tham số
@AllArgsConstructor // tạo sẵn controcter có tất cả tham số
@Entity
@Table
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    private Integer age ;

    private String address ;

    private String userName ;

    private String fullName ;

    private String passWord ;

    @ManyToOne
    @JoinColumn(name = "deparment_Id")
    private Department department ;
}


