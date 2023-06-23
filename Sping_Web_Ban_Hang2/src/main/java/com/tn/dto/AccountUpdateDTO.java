package com.tn.dto;

import lombok.Data;

@Data
public class AccountUpdateDTO {
    private Integer id ;

    private String userName ;

    private String fullName ;

    private Integer age ;

    private String address ;

    private String passWord ;

    private Integer departmentId;
}
