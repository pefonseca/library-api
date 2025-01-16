package com.pefonseca.library.api.config;

import com.pefonseca.library.api.security.CustomUserDetailsService;
import com.pefonseca.library.api.security.LoginSocialSuccessHandler;
import com.pefonseca.library.api.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSocialSuccessHandler successHandler) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults()) // auth basico com httpBasic
//                .formLogin(configurer -> {
//                    configurer.loginPage("/login").permitAll();
//                }) // configurando login alternativo
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/users/**").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> {
                    oauth2.successHandler(successHandler);
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    public UserDetailsService userDetailsService(UserService userService) {
//            UserDetails user1 = User.builder()
//                                    .username("user")
//                                    .password(encoder.encode("password"))
//                                    .roles("USER")
//                                    .build();
//
//            UserDetails user2 = User.builder()
//                                    .username("Admin")
//                                    .password(encoder.encode("password"))
//                                    .roles("ADMIN")
//                                    .build();
//
//            return new InMemoryUserDetailsManager(user1, user2);

        return new CustomUserDetailsService(userService);

    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

}
