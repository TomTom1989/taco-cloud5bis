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
            // other configurations
            .authorizeRequests(authorize -> authorize
                .requestMatchers("/ingredients/**").permitAll()
                // specify other secured endpoints
                .anyRequest().authenticated()
            )
            // other configurations, like formLogin, httpBasic, logout, etc.
            .csrf().disable(); // Only disable CSRF if you're sure about it

        return http.build();
    }
}

