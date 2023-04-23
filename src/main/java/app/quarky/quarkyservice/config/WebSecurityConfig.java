package app.quarky.quarkyservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.security.SecureRandom;
import java.util.Random;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebFluxSecurity
@Configuration
public class WebSecurityConfig {

    @Bean
    SecurityWebFilterChain security(ServerHttpSecurity http) {
        return http.authorizeExchange(
                        request -> request
                                .pathMatchers("/users/register").permitAll()
                                .anyExchange().authenticated())
                .csrf().disable()
                .formLogin().disable()
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    Random random() {
        return new SecureRandom();
    }
}
