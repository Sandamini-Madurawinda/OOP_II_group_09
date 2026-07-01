package g9.pulse.pulse.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
   public SecurityFilterChain filterChain(HttpSecurity http)
        throws Exception{

        return http

        .csrf(csrf -> csrf.disable())


                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/",
                                "/login",
                                "/register",
                                "/css/**",
                                "/profile",
                                "/edit-profile",
                                "/search"
                        ).permitAll()


                        .anyRequest()
                        .authenticated()

                )


                .formLogin(form -> form

                        .loginPage("/login")

                        .defaultSuccessUrl("/posts/feed",true)

                        .permitAll()

                )


                .logout(logout -> logout

                        .logoutSuccessUrl("/login")

                        .invalidateHttpSession(true)

                )


                .build();

    }



    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();



    }
}
