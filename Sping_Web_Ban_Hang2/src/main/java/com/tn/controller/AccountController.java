package com.tn.controller;

import com.tn.dto.AccountCreateDTO;
import com.tn.dto.AccountDTO;
import com.tn.dto.AccountUpdateDTO;
import com.tn.entity.Account;
import com.tn.entity.Department;
import com.tn.repository.AccountRepository;
import com.tn.repository.DeparmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController// restfulAPI
@Transactional
@CrossOrigin(value = "*")
@Slf4j
public class AccountController {

    @Autowired
    private AccountRepository accountRepo ;

    @Autowired
    private DeparmentRepository departmentRepo ;

    // show all Account
    @GetMapping("account2")
    public ResponseEntity<?> getAll(Pageable pageable) {
        log.info("get all list account");

        System.out.println(pageable);
        Page<Account> pageAccounts = accountRepo.findAll(pageable) ;
        System.out.println(pageAccounts.getSize());
        List<Account> accounts = pageAccounts.stream().collect(Collectors.toList());

        List<AccountDTO> accountDTOS = new ArrayList<>();

        for (int i = 0; i < accounts.size(); i++) {
            AccountDTO accountDTO = new AccountDTO() ;

            accountDTO.setId(accounts.get(i).getId());
            accountDTO.setFullName(accounts.get(i).getFullName());
            accountDTO.setUserName(accounts.get(i).getUserName());
            accountDTO.setAge(accounts.get(i).getAge());
            accountDTO.setAddress(accounts.get(i).getAddress());
            accountDTO.setPassWord(accounts.get(i).getPassWord());

            Department department = accounts.get(i).getDepartment();
            if (department != null) {
                String a =  accounts.get(i).getDepartment().getDepartmentName();
                accountDTO.setDepartmentName(a);
            }
            accountDTOS.add(accountDTO) ;
        }


        return new ResponseEntity<>(accountDTOS, HttpStatus.OK) ;
    }

    // get by id
    @GetMapping("account/{id}")
    public ResponseEntity<?> getById (@PathVariable Integer id) {
        log.info("get account by id" + id);
        Optional<Account> account = accountRepo.findById(id);

        if (account.isEmpty()){
            return new ResponseEntity<>("id ko tồn tại "+ id, HttpStatus.BAD_REQUEST ) ;
        }
        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setId(account.get().getId());
        accountDTO.setFullName(account.get().getFullName());
        accountDTO.setUserName(account.get().getUserName());
        accountDTO.setAge(account.get().getAge());
        accountDTO.setAddress(account.get().getAddress());
        accountDTO.setPassWord(account.get().getPassWord());

        Department department = account.get().getDepartment();
        if (department != null) {
            String a =  account.get().getDepartment().getDepartmentName();
            accountDTO.setDepartmentName(a);
        }
        return new ResponseEntity<>(accountDTO, HttpStatus.OK) ;
    }

    // thêm mới account
    @PostMapping("account")
    public ResponseEntity<?> creat(@RequestBody AccountCreateDTO accountCreateDTO) {

        Account account = new Account();

        account.setUserName(accountCreateDTO.getUserName());
        account.setFullName(accountCreateDTO.getFullName());
        account.setAge(accountCreateDTO.getAge());
        account.setAddress(accountCreateDTO.getAddress());

        String passWordEncoder = new BCryptPasswordEncoder().encode(accountCreateDTO.getPassWord());
        account.setPassWord(passWordEncoder);

        // check nếu khác null sẽ đẩy dữ liệu lên db luôn còn ko sẽ đẩy thẳng DL lên DB
        if (accountCreateDTO.getDepartmentId() != null) {
            Department department = departmentRepo.findById(accountCreateDTO.getDepartmentId()).get();
            account.setDepartment(department);
        }
        log.info("Insert account: " + accountCreateDTO);
        accountRepo.save(account);
        return new ResponseEntity<>("Create thành công", HttpStatus.OK) ;
    }

    // update account
    @PutMapping("account")
    public ResponseEntity<?> update (@RequestBody AccountUpdateDTO accountUpdateDTO) {
        log.info("update account" + accountUpdateDTO);

        Account account = accountRepo.findById(accountUpdateDTO.getId()).get();
        account.setUserName(accountUpdateDTO.getUserName());
        account.setFullName(accountUpdateDTO.getFullName());
        account.setAge(accountUpdateDTO.getAge());
        account.setAddress(accountUpdateDTO.getAddress());

        String passWordEncoder = new BCryptPasswordEncoder().encode(accountUpdateDTO.getPassWord());
        account.setPassWord(passWordEncoder);

        // check nếu khác null sẽ đẩy dữ liệu lên db luôn còn ko sẽ đẩy thẳng DL lên DB
        if (accountUpdateDTO.getDepartmentId() != null) {
            Department department = departmentRepo.findById(accountUpdateDTO.getDepartmentId()).get();
            account.setDepartment(department);
        }
        accountRepo.save(account);
        return new ResponseEntity<>("Update thành công", HttpStatus.OK) ;
    }

    // delete account by id
    @DeleteMapping("account/{id}")
    public ResponseEntity<?> delete (@PathVariable Integer id) {
        log.info("delete account by id" + id);
        Optional<Account> account = accountRepo.findById(id);

        if (account.isEmpty()){
            return new ResponseEntity<>("id ko tồn tại "+ id, HttpStatus.BAD_REQUEST ) ;
        }
        accountRepo.deleteById(id);
        return new ResponseEntity<>("delete thành công", HttpStatus.OK) ;
    }

    // update account by id
    @PutMapping("account/{id}")
    public ResponseEntity<?> updateById (@PathVariable Integer id) {
        log.info("update account by id" + id);

        Optional<Account> account = accountRepo.findById(id);
        System.out.println(id);

        if (account.isEmpty()){
            return new ResponseEntity<>("id ko tồn tại "+ id, HttpStatus.BAD_REQUEST ) ;
        }

//        Account account1 = account.get() ;
//        account1.setFullName("Ngân");
//        account1.setUserName("t20");
//        accountRepo.save(account1);
        return new ResponseEntity<>("Update thành công", HttpStatus.OK) ;
    }


            //-----------------------------------------------------------------//
    @GetMapping("account")
    public ResponseEntity<?> showAll() {
        List<Account> accounts = accountRepo.getData() ;
        System.out.println(accounts);
        return new ResponseEntity<>(accounts, HttpStatus.OK) ;
    }

    // get by Username
    @GetMapping("accountByUserName")
    public ResponseEntity<?> getByUsername(@RequestParam String userName) {
        List<Account> accounts = accountRepo.getByUserName(userName) ;
        System.out.println(accounts);
        return new ResponseEntity<>(accounts, HttpStatus.OK) ;
    }

    @GetMapping("deleteById")
    public ResponseEntity<?> deleteById(@RequestParam Integer id) {
        accountRepo.deleteById(id); ;
        return new ResponseEntity<>("delete thành công", HttpStatus.OK) ;
    }


}
