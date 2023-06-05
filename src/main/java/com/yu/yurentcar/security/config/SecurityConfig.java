package com.yu.yurentcar.security.config;

import com.yu.yurentcar.domain.user.repository.UserRepository;
import com.yu.yurentcar.domain.user.service.CustomOAuth2UserService;
import com.yu.yurentcar.domain.user.service.CustomUserDetailsService;
import com.yu.yurentcar.global.utils.CookieProvider;
import com.yu.yurentcar.global.utils.TokenProvider;
import com.yu.yurentcar.security.filter.JwtFilter;
import com.yu.yurentcar.security.handler.UserLoginSuccessHandler;
import com.yu.yurentcar.security.service.TokenRedisService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Log4j2
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${my.web.base-url}")
    private String webBaseUrl;

    @Value("${my.web.domain}")
    private String DOMAIN;

    private final UserRepository userRepository;

    private final TokenRedisService tokenRedisService;
    private final CustomUserDetailsService customUserDetailsService;

    private final TokenProvider tokenProvider;
    private final CookieProvider cookieProvider;

    public CustomOAuth2UserService oAuth2UserDetailsService() {
        return new CustomOAuth2UserService(userRepository, passwordEncoder());
    }

    public UserLoginSuccessHandler userLoginSuccessHandler() {
        return new UserLoginSuccessHandler(tokenProvider, cookieProvider, tokenRedisService, webBaseUrl);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain springSecurity(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    // 필요한 권한 없이 접근하려 할때 403
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                })
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(new JwtFilter(tokenProvider, customUserDetailsService), UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests((authz) -> authz
                .requestMatchers("/api/v1/**").permitAll()
                        .anyRequest().denyAll());


        //로컬 로그인
        //http.formLogin();

        //소셜 로그인
        http.oauth2Login()
                //.defaultSuccessUrl(webBaseUrl)
                .userInfoEndpoint()
                .userService(oAuth2UserDetailsService())
                .and()
                .successHandler(userLoginSuccessHandler());

        http.logout( logout -> logout
                //.logoutSuccessHandler()
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/v1/logout"))
                .addLogoutHandler((request, response, authentication) -> {
                    Cookie refreshToken = new Cookie("refreshToken", null);
                    refreshToken.setPath("/");
                    refreshToken.setHttpOnly(true);
                    refreshToken.setMaxAge(0);
                    refreshToken.setDomain(DOMAIN);
                    Cookie accessToken = new Cookie("accessToken", null);
                    accessToken.setPath("/");
                    accessToken.setHttpOnly(true);
                    accessToken.setMaxAge(0);
                    accessToken.setDomain(DOMAIN);

                    response.addCookie(accessToken);
                    response.addCookie(refreshToken);

                    /*
                    String tokenString = request.getHeader("Authorization");
                    try {
                        tokenRedisService.deleteToken(tokenString);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                     */
                    SecurityContextHolder.clearContext();
                })
                .deleteCookies("accessToken", "refreshToken")
                .invalidateHttpSession(true)
                .logoutSuccessHandler((request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK))
        );


        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //configuration.setAllowedOrigins(Collections.singletonList("http://42.82.185.184:3000")); // singletonList : 하나짜리 리스트
        configuration.setAllowedOrigins(Arrays.asList(webBaseUrl, "http://localhost:3000"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Arrays.asList("X-Page-Count", "Access-Control-Allow-Origin","Access-Control-Allow-Credentials"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
