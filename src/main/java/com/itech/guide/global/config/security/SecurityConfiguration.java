package com.itech.guide.global.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final JwtProvider jwtProvider;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().httpBasic().disable().cors().disable()
                .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint).and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests() // 보호된 리소스 URI에 접근할 수 있는 권한 설정

                .antMatchers(GET,"/exception/**").permitAll()
                .antMatchers(POST,"/api/**/members").permitAll()

                .antMatchers(POST,"/api/**/login").permitAll()

                .antMatchers(GET,"/api/v1/members").access("hasRole('ROLE_ADMIN')") // ROLE_ADMIN 권한을 가진 사용자만 접근 허용
                .antMatchers("/api/v1/members/**").access("hasRole('ROLE_TEMPORARY_MEMBER')") // ROLE_MEMBER 권한을 가진 사용자만 접근 허용

                .antMatchers("/api/**/re-issue").authenticated() // 인증된 사용자만 접근 허용
                .antMatchers(GET,"/api/**/members","/api/**/members/**").authenticated()

                .anyRequest().authenticated()// 그 외 항목 전부 인증 적용
                .and()
                .addFilterBefore(new CustomAuthorizationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/exception/**");
    }



}
