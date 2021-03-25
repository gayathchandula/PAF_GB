package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from user where username=?")
                .authoritiesByUsernameQuery("select username, role from user where username=?")
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()

                .antMatchers("/").access("hasAuthority('User') or hasAuthority('Admin')")
                .antMatchers("/reasearch","/update","/delete","/file","user/research").access("hasAuthority('Researcher') or hasAuthority('Admin')")
                .antMatchers("/retrive").access("hasAuthority('Funder')")
                .antMatchers("/admin").access("hasAuthority('Admin')")
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin().defaultSuccessUrl("/", true)
                .and()
                .httpBasic()
                .and()
                .logout().permitAll();
    }

}
