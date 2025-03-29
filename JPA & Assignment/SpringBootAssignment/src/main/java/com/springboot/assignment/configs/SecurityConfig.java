//1
package com.springboot.assignment.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    
    @Autowired
    private UserDetailsService userDetailsService;

    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http

                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
        //http.formLogin(Customizer.withDefaults());

    }

    
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //6
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));//
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

                                   //Hardcoded User Details(UserName, Password and rules)//
    // @Bean
    // public UserDetailsService userDetailsService() {
    //     UserDetails user1 = User
    //        .withUsername("deepak")
    //        .password(passwordEncoder().encode("d@123")) // Encode password
    //        .roles("USER")
    //             .build();
           

    //     UserDetails user2 = User
    //        .withUsername("swaraj")
    //        .password(passwordEncoder().encode("s@123")) // Encode password
    //        .roles("ADMIN")
    //        .build();

    //     return new InMemoryUserDetailsManager(user1, user2);
    // }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }
}
