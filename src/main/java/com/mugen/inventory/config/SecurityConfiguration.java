package com.mugen.inventory.config;


import com.mugen.inventory.entity.Admin;
import com.mugen.inventory.entity.model.vo.response.AuthorizeVO;
import com.mugen.inventory.filter.JwtAuthorizeFilter;
import com.mugen.inventory.utils.HostHolder;
import com.mugen.inventory.utils.JwtUtils;
import com.mugen.inventory.utils.RestBean;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Resource
    JwtUtils utils;

    @Resource
    JwtAuthorizeFilter jwtAuthorizeFilter;

    @Resource
    HostHolder hostHolder;

    @Value("${spring.web.cors.origin}")
    List<String> origins;

    @Value("${spring.web.cors.header}")
    List<String> headers;

    @Value("${spring.web.cors.method}")
    List<String> methods;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(conf -> conf
                        .requestMatchers("/mugen/api/auth/**", "/mugen/api/error", "/mugen/api/error/**", "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(conf -> conf
                        .loginProcessingUrl("/auth/login")
                        .successHandler(this::onAuthenticationSuccess)
                        .failureHandler(this::onAuthenticationFailure)
                )
                .logout(conf -> conf
                        .logoutUrl("/auth/logout")
                        .logoutSuccessHandler(this::onLogoutSuccess)
                )
                .exceptionHandling(conf -> conf
                        .authenticationEntryPoint(this::onAuthenticationFailure)
                        .accessDeniedHandler(this::onAccessDeny)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(conf -> conf
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .cors((cors) -> cors
                        .configurationSource(corsConfigurationSource())
                )
                .addFilterBefore(jwtAuthorizeFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/josn;charset=utf-8");
        User user = (User) authentication.getPrincipal();
        Admin admin = hostHolder.getAdmin();
        String token = utils.createJwt(user, admin.getId(), admin.getUserName());
        AuthorizeVO vo = admin.asViewObject(AuthorizeVO.class, v -> {
            v.setExpire(utils.expireTime());
            v.setToken(token);
        });
        response.getWriter().write(RestBean.success(vo, "Success").asJsonString());
    }

    public void onAccessDeny(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException {
        response.setContentType("application/josn;charset=utf-8");
        response.getWriter().write(RestBean.failure(RestBean.FORBIDDEN, exception.getMessage()).asJsonString());
    }
    public void onUnauthorized(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType("application/josn;charset=utf-8");
        response.getWriter().write(RestBean.failure(RestBean.UNAUTHORIZED, exception.getMessage()).asJsonString());
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType("application/josn;charset=utf-8");
        response.getWriter().write(RestBean.failure(RestBean.UNAUTHORIZED, exception.getMessage()).asJsonString());
    }

    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType("application/josn;charset=utf-8");
        String authorization = request.getHeader("Authorization");
        if (utils.invalidataJwt(authorization)) {
            response.getWriter().write(RestBean.success(null, "退出成功").asJsonString());
        } else {
            response.getWriter().write(RestBean.failure(400, "退出失败").asJsonString());
        }
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        origins.forEach(config::addAllowedOrigin);
        headers.forEach(config::addAllowedHeader);
        methods.forEach(config::addAllowedMethod);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

