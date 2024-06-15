package com.luanvan.luanvan.securityService.config;


import com.luanvan.luanvan.securityService.filter.JwtAuthenticationFilter;
import com.luanvan.luanvan.securityService.service.UserDetailsServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final UserDetailsServiceImp userDetailsServiceImp;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    //Danh sach duong dan chi GV dc phep truy cap
    private static final String[] GV_ACCESS={
            "/api-gv/**"
    };
    //TUONG TU NHU TREN NHUNG LA ADMIN
    private static final String[] ADMIN_ACCESS={
            "/api-admin/**"
    };
    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req->req.requestMatchers("/api/authenticate/**","/**")
                                .permitAll()
                                .requestMatchers(GV_ACCESS).hasAnyAuthority("GV","ADMIN")
                                .requestMatchers(ADMIN_ACCESS).hasAnyAuthority("GV","ADMIN")
                                .anyRequest()
                                .authenticated()
                ).userDetailsService(userDetailsServiceImp)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
