package com.example.pr2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    public WebSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/registration", "/css/**", "/style.css").permitAll()
                        .requestMatchers("/customers/**").hasRole("MANAGER")
                        .requestMatchers("/inventory/**").hasRole("MANAGER")
                        .requestMatchers("/orders/**").hasRole("MANAGER")
                        .requestMatchers("/order_details/**").hasRole("MANAGER")
                        .requestMatchers("/manufacturers/**").hasRole("MANAGER")
                        .requestMatchers("/roles/**").hasRole("ADMIN")
                        .requestMatchers("/users/**").hasRole("ADMIN")
                        .requestMatchers("/vehicles/update").hasRole("MANAGER")
                        .requestMatchers("/vehicles/delete").hasRole("MANAGER")
                        .requestMatchers("/vehicles/add").hasRole("MANAGER")
                        .requestMatchers("/vehicles/view").hasAnyRole("MANAGER", "USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable());
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }
}
