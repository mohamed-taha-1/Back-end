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

import com.model.dao.UserEntityRepository;
import com.services.UserService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity(securedEnabled=true, prePostEnabled=true)
@EnableWebSecurity
@Configuration
public class GlobalSecurity {
	private final UserService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserEntityRepository userRepository;

	@Autowired
	public GlobalSecurity(UserService userDetailsService,
    		BCryptPasswordEncoder bCryptPasswordEncoder,
    		UserEntityRepository userRepository
    		) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
	}

	@Bean
	SecurityFilterChain setSecuerity(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {

		// Configure AuthenticationManagerBuilder
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
       
        // Get AuthenticationManager
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

		MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
		http
        .csrf((csrf) -> csrf.disable())
         .authorizeHttpRequests((authz) -> authz
					.requestMatchers(
							AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/users/save"),
							AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/orders/all"),
							AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/h2/**"),
					        AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/swagger-ui/**"),
					        AntPathRequestMatcher.antMatcher(HttpMethod.GET , "/api-docs"),
					        AntPathRequestMatcher.antMatcher( HttpMethod.GET,"/swagger-ui/index.html"))
					.permitAll().anyRequest().authenticated())
     
        .addFilter(getAuthenticationFilter(authenticationManager))
        .addFilter(new AuthorizationFilter(authenticationManager, userRepository))
        .authenticationManager(authenticationManager)
        .sessionManagement((session) -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
         http.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.sameOrigin()));
        
        return http.build();
    }
 
    
       protected AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager);
        filter.setFilterProcessesUrl("/users/login");
        return filter;
    }
 
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource()
    {
    	final CorsConfiguration configuration = new CorsConfiguration();
    	   
    	configuration.setAllowedOrigins(Arrays.asList("*"));
    	configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE","OPTIONS"));
    	configuration.setAllowCredentials(true);
    	configuration.setAllowedHeaders(Arrays.asList("*"));
    	
    	final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    	source.registerCorsConfiguration("/**", configuration);
    	
    	return source;
    }
}
