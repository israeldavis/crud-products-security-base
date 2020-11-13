package com.crud.product.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin")
                .password("$2a$10$F3GY/45trIzQXIBe64IhhO9wxPkaGggCei66F.qj09.q13Z9xFCH.")
                .roles("ADMIN")
            .and()
                .withUser("user")
                .password("$2a$10$9ZBml2uieCMe.IZEhDtN8uVwXz0FO1HK7c/OnkfXQvQmgqKbaYjmm")
                .roles("USER")
                ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/productos/create").hasAnyRole("USER", "ADMIN")
                .antMatchers("/productos/editar/*", "/productos/borrar/*").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .exceptionHandling().accessDeniedPage("/errores/403")
                ;
    }
}
