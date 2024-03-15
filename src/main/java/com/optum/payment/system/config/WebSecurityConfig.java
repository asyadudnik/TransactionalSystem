package com.optum.payment.system.config;

import com.optum.payment.system.entities.enums.RoleName;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.optum.payment.system.global.InstallConstants.PASS;
import static com.optum.payment.system.global.InstallConstants.USER;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class WebSecurityConfig  implements WebMvcConfigurer {
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/",
            "classpath:/resources/",
            "classpath:/static/",
            "classpath:/static/css",
            "classpath:/static/js",
            "classpath:/public/"};

    /**
     * This section defines the user accounts which can be used for authentication as well as the roles each user has.
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {


        var builder = User.builder()
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode);

        var springuser = builder
                .username(USER)
                .password(PASS)
                .roles(RoleName.ADMIN.name())
                .build();
        var root = builder
                .username("root")
                .password("Libra28091963!")
                .roles(RoleName.ADMIN.name(), RoleName.PROGRAMMER.name())
                .build();

        return new InMemoryUserDetailsManager(springuser, root);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher(EndpointRequest.toAnyEndpoint());
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/user/login/")
                .hasRole("ADMIN"));
        return http.build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/**" )
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


}
