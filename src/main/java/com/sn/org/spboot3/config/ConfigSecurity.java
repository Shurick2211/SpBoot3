package com.sn.org.spboot3.config;

import com.sn.org.spboot3.model.Role;
import com.sn.org.spboot3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ConfigSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
               // .httpBasic().disable()
               // .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/hello","/person/add","/person/{id}","/img/**").permitAll()
                .antMatchers("/enterUser").authenticated()
                .antMatchers("/enterModer").hasAnyAuthority(Role.MODER.name(),Role.ADMIN.name())
                .antMatchers("/enterAdmin", "/person/**").hasAuthority(Role.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .defaultSuccessUrl("/")

                    .permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/")

                    .permitAll()
                .and()
                //.rememberMe().userDetailsService(userDetailsService())
        ;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(userService)
        ;
    }
    @Bean
    public PasswordEncoder encoder(){
        return //NoOpPasswordEncoder.getInstance();
                new BCryptPasswordEncoder(12);
    }
}
