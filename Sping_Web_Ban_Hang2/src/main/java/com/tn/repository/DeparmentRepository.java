package com.tn.repository;

import com.tn.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeparmentRepository extends JpaRepository<Department, Integer> {


}
