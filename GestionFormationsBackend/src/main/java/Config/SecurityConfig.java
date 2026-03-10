package Config;

import Security.JwtAuthenticationFilter;
import Security.CustomPersonDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

        private final CustomPersonDetailsService personDetailsService;
        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        public SecurityConfig(
                CustomPersonDetailsService personDetailsService,
                JwtAuthenticationFilter jwtAuthenticationFilter) {
                this.personDetailsService = personDetailsService;
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {

                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setUserDetailsService(personDetailsService);
                provider.setPasswordEncoder(passwordEncoder());

                return provider;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                        .csrf(csrf -> csrf.disable())
                        .sessionManagement(session ->
                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .anyRequest().permitAll()
                        )
                        .authenticationProvider(authenticationProvider())
                        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}