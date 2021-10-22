package org.zerock.sb.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {

@Bean
public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}


    @Override
    protected void configure(HttpSecurity http) throws Exception {
       log.info("CustomSecurityConfig..configure....");
        log.info("CustomSecurityConfig..configure....");
        log.info("CustomSecurityConfig..configure....");
        log.info("CustomSecurityConfig..configure....");
        http.formLogin().loginPage("/customLogin").loginProcessingUrl("/login"); //인가/인증에 문제시 로그인 화면
        http.csrf().disable();
        http.logout();

    }
}
