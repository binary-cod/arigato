package com.binarycod.arigato.config;

import com.binarycod.arigato.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthService authService;

    // authentication management
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(authService)
                .passwordEncoder(passwordEncoder());
    }

    //HTTP filtering
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 1. for the endpoints /admin --> allow only roles ADMIN
        // 2. for the endpoints /customer --> allow only roles USER, authenticated
        // 3. /register, /login, /  -> must be public to all users

        http
                .authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/customer/**").hasAuthority("USER")
                .antMatchers("/cart/checkout/**").hasAuthority("USER")
                .antMatchers("/", "/login", "/register","/**").permitAll()
                .and()
                .formLogin()
                .and()
                .logout().permitAll().logoutSuccessUrl("/");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
