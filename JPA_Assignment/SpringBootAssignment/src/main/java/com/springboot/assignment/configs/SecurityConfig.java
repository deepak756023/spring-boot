//1
package com.springboot.assignment.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http

                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request
                        // 7
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Only ADMIN can access
                        .requestMatchers("/employees/**", "/employee/**").hasAnyRole("USER", "ADMIN") // Both
                                                                                                                   // USER
                                                                                                                   // and
                        // ADMIN can
                        // access
                        .requestMatchers("/welcome-page/**",
                                "/registration/**", "/login/**")
                        .permitAll() // Public access
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 15
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
        // http.formLogin(Customizer.withDefaults());

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // 6
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));//
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    // 11
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Hardcoded User Details(UserName, Password and rules)//
    // @Bean
    // public UserDetailsService userDetailsService() {
    // UserDetails user1 = User
    // .withUsername("deepak")
    // .password(passwordEncoder().encode("d@123")) // Encode password
    // .roles("USER")
    // .build();

    // UserDetails user2 = User
    // .withUsername("swaraj")
    // .password(passwordEncoder().encode("s@123")) // Encode password
    // .roles("ADMIN")
    // .build();

    // return new InMemoryUserDetailsManager(user1, user2);
    // }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    // return new BCryptPasswordEncoder();
    // }
}
