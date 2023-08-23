package com.siller.applifting.monitor.security;

import com.siller.applifting.monitor.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static com.siller.applifting.monitor.endpointMonitoring.api.MonitoredEndpointsApi.MONITORED_ENDPOINT_PATH_PREFIX;
import static com.siller.applifting.monitor.user.api.UsersApi.USER_PATH_PREFIX;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserService userService;

    @Value("${security.http.authorization.schemaName}")
    String schemaName;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return  httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new AuthenticationFilter(schemaName, userService), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(MONITORED_ENDPOINT_PATH_PREFIX + "/**").authenticated()
                                .requestMatchers(USER_PATH_PREFIX).permitAll()
                                .anyRequest().permitAll()
                )
                .build();
    }
}
