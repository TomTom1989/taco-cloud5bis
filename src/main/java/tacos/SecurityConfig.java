package tacos;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Authorize requests configuration
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    // Permit all POST and GET requests to /api/tacos
                    .requestMatchers(HttpMethod.POST, "/api/tacos").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/tacos/**").permitAll()
                    // Permit POST and GET requests to /api/tacos/order
                    .requestMatchers(HttpMethod.POST, "/api/tacos/order").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/tacos/order/**").permitAll()
                    // Any other requests must be authenticated
                    .anyRequest().authenticated()
            )
            // CSRF is disabled for simplicity, but consider the implications in a real-world application
            .csrf().disable();

        return http.build();
    }
}
