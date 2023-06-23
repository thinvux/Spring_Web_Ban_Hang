package com.tn.configSecurity;

import com.tn.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ConfigSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountService accountService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService);
    }

    @Bean // chuyển pass thành mã code
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();

        // cần xác nhận các request, bất kỳ request cũng cần đc xác nhận (login)
        http.authorizeRequests().anyRequest().authenticated();

        // http basic dùng cho @RestController (gửi từ postman, ajax)
        // formLogin dùng cho @Controller việc gửi thẻ <form> từ trang html (Thymeleaf)
        http.httpBasic();
        http.csrf().disable();
    }

}
