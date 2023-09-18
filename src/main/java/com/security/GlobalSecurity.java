package com.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.services.UserService;

@EnableWebSecurity
@Configuration
public class GlobalSecurity {
	private final BCryptPasswordEncoder encoder;
	private final UserService userDetailsService;

	@Autowired
	public GlobalSecurity(BCryptPasswordEncoder encoder, UserService userDetailsService) {
		super();

		this.encoder = encoder;
		this.userDetailsService = userDetailsService;
	}

	@Bean
	SecurityFilterChain setSecuerity(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {

		AuthenticationManagerBuilder authMangerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authMangerBuilder.userDetailsService(userDetailsService).passwordEncoder(encoder);

		AuthenticationManager authManger = authMangerBuilder.build();
		http.authenticationManager(authManger);

		MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

//		 http.csrf(csrfConfigurer ->
//		 csrfConfigurer.ignoringRequestMatchers(mvcMatcherBuilder.pattern("/users/**")));

		http.authorizeHttpRequests(auth -> auth
				.requestMatchers(mvcMatcherBuilder.pattern("/users/save"),
						AntPathRequestMatcher.antMatcher("/orders/all"),
						AntPathRequestMatcher.antMatcher("/swagger-ui/**"))
				.permitAll().requestMatchers(AntPathRequestMatcher.antMatcher("/h2/**")).authenticated().anyRequest()
				.authenticated());

		http.addFilter(new AuthenticationFilter(authManger));
		http.addFilter(new AuthorizationFilter(authManger));
		http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// CSRF and the header X-Frame-Options

		http.headers((headers) -> headers.frameOptions(f -> f.sameOrigin()));

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("*"));

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}
