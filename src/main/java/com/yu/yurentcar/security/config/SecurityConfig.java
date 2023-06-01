package com.yu.yurentcar.security.config;

import com.yu.yurentcar.domain.user.repository.UserRepository;
import com.yu.yurentcar.domain.user.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${my.web.base-url}")
    private String webBaseUrl;

    @Autowired
    private UserRepository userRepository;

    public CustomOAuth2UserService oAuth2UserDetailsService() {
        return new CustomOAuth2UserService(userRepository, passwordEncoder());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain springSecurity(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable();

        //로컬 로그인
        //http.formLogin();

        //소셜 로그인
        http.oauth2Login()
                .defaultSuccessUrl(webBaseUrl)
                //.successHandler()
                .userInfoEndpoint()
                .userService(oAuth2UserDetailsService());


        return http.build();
    }
}
