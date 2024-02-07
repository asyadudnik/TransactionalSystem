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

import static com.optum.payment.system.global.InstallConstants.PASS;
import static com.optum.payment.system.global.InstallConstants.USER;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * This section defines the user accounts which can be used for authentication as well as the roles each user has.
     */
    @Bean
    InMemoryUserDetailsManager userDetailsManager() {


        var builder = User.builder().passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode);

        var springuser = builder.username(USER).password(PASS).roles(RoleName.ADMIN.name()).build();
        var root = builder.username("root").password("Libra28091963!").roles(RoleName.ADMIN.name(), RoleName.PROGRAMMER.name()).build();

        return new InMemoryUserDetailsManager(springuser, root);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher(EndpointRequest.toAnyEndpoint());
        http.authorizeHttpRequests(requests -> requests.anyRequest().hasRole("ADMIN"));
        http.httpBasic(withDefaults());
        return http.build();
    }

}
