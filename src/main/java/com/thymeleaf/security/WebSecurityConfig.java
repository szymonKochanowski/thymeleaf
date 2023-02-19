package com.thymeleaf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder(12))
                .usersByUsernameQuery("select username, password, enabled from user where username =?")
                .authoritiesByUsernameQuery("select username, role from user where username =?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/user/addUser", "**/images/**.**", "/**/*.png", "/**/*.jpg", "/**/*.js", "templates/**.html", "/carDetail/{id}", "/webjars/**").permitAll()
                .antMatchers(HttpMethod.GET, "/loginPage").permitAll()
                .antMatchers("/user/deleteUser/**", "/user/userPanel", "/user/updateUserProfile", "/basket", "/basket/add/**/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/loginPage").hasAnyRole("USER", "ADMIN")
                .antMatchers( "/updateCar/**", "/deleteCar/**", "/user/all", "/addCar", "/admin/adminPanel").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/loginPage").successForwardUrl("/loginPage").failureForwardUrl("/loginPage").permitAll()
                .and()
                .logout().logoutSuccessUrl("/").permitAll()
                .and()
                .csrf().disable();
    }

}
