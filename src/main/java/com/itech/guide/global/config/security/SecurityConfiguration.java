package com.itech.guide.global.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtProvider jwtProvider;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
            .httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .antMatcher("/**").authorizeRequests() // 보호된 리소스 URI에 접근할 수 있는 권한 설정

                .antMatchers("/").permitAll() // 전체 접근 허용
                .antMatchers("/exception/**").permitAll()

                .antMatchers("/api/**/re-issue").authenticated() // 인증된 사용자만 접근 허용
                .antMatchers(GET,"/api/**/members","/api/**/members/**").authenticated()

                .antMatchers(POST,"/api/**/members").anonymous() // 인증되지 않은 사용자만 접근 허용
                .antMatchers(POST,"/api/**/login").anonymous()

                .antMatchers("/").hasRole("ADMIN") // ROLE_ADMIN 권한을 가진 사용자만 접근 허용
                .antMatchers("/").hasAnyRole("ADMIN", "USER") // ROLE_ADMIN 혹은 ROLE_USER 권한을 가진 사용자만 접근 허용
                // 그 외 항목 전부 인증 적용
                .anyRequest()
                .authenticated()

                .and()
                .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)

                .and()
                .addFilterBefore(new CustomAuthorizationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/resources/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
