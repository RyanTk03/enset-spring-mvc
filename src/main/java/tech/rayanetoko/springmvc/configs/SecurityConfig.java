package tech.rayanetoko.springmvc.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		PasswordEncoder bcrypt = passwordEncoder();
		return new InMemoryUserDetailsManager(
			User.withUsername("user1").password(bcrypt.encode("1234")).roles("USER").build(),
			User.withUsername("user2").password(bcrypt.encode("1234")).roles("USER").build(),
			User.withUsername("admin").password(bcrypt.encode("1234")).roles("USER", "ADMIN").build()
		);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
		.formLogin(fl -> fl.loginPage("/login").permitAll())
		.authorizeHttpRequests(ar -> ar.requestMatchers("/user/**").hasRole("USER"))
		.authorizeHttpRequests(ar -> ar.requestMatchers("/admin/**").hasRole("ADMIN"))
		.authorizeHttpRequests(ar -> ar.requestMatchers("/public/**", "/webjars/**").permitAll())
		.authorizeHttpRequests(ar -> ar.anyRequest().authenticated())
		.exceptionHandling(eh -> eh.accessDeniedPage("/not-authorized"))
		.build();
	}
}
