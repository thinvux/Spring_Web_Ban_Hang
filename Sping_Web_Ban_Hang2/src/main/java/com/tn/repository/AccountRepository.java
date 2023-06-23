package com.tn.repository;

import com.tn.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("FROM Account ")
    List<Account> getData();

    @Query("FROM Account WHERE userName = :userName")
    List<Account> getByUserName(String userName);

    @Modifying // delete phải dùng + vs @Transactional bên Controller
    @Query("DELETE FROM Account WHERE id= :id")
    void deleteById(Integer id);

//            ---------------------------------------------------  //

    @Override
    Page<Account> findAll(Pageable pageable);

    Optional<Account> findByUserName(String username);


}
