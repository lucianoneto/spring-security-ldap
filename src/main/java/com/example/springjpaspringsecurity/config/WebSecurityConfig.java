package com.example.springjpaspringsecurity.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Slf4j
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${URL}")
    public String URL;

    @Value("${LOGIN}")
    public String managerDn;

    @Value("${PASSWORD}")
    public String managerPassword;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().fullyAuthenticated()
                .and()
                .formLogin();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0}")
                .groupSearchBase("ou=people")
                .userSearchFilter("(uid={0})")
                .contextSource()
                .url(URL).managerDn(managerDn).managerPassword(managerPassword)
                .and()
                .passwordCompare().passwordAttribute("userPassword")
                .and()
                .passwordEncoder(new BCryptPasswordEncoder());
    }


}
