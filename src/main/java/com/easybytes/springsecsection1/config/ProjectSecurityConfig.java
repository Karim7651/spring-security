package com.easybytes.springsecsection1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import javax.sql.DataSource;

@Configuration
public class ProjectSecurityConfig {
    //    @Bean
//    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
//        http.authorizeHttpRequests((requests)->requests.anyRequest().authenticated()); //all requests require auth there's also denyAll permitAll
//        http.formLogin(Customizer.withDefaults()); redirect to login form is not authed
//        http.httpBasic(Customizer.withDefaults()); http basic header
//        return http.build();
//    }
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests((requests) ->
                requests.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated() // those require auth
                        .requestMatchers("/notices", "/contact", "/error","/register").permitAll()); // those don't require auth
        http.formLogin(Customizer.withDefaults()); //method reference disabling login form
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

//    @Bean
//    //User details service is an interface and InMemoryDetailsManager is an impl
//    public UserDetailsService userDetailsService(){
//        //create users with authorities
//        //{noop} => store as plain text
//        UserDetails user= User.withUsername("user").password("{bcrypt}12345").authorities("read").build();
//        UserDetails admin= User.withUsername("admin").password("{bcrypt}54321").authorities("admin").build();
//        return new InMemoryUserDetailsManager(user,admin);
//    }
//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource){
//        return new JdbcUserDetailsManager(dataSource);
//    }

    @Bean
    //passwordEncoder is an interface we'd return an implementation of it
    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();// allows all encoders to work
    }
//    @Bean
//    public CompromisedPasswordChecker compromisedPasswordChecker(){
//        return new HaveIBeenPwnedRestApiPasswordChecker(); //check if password is compromised
//    }
}
