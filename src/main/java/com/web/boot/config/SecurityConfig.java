package com.web.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;    
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring().antMatchers("/", "/css/**", "/js/**", "/images/**", "/lib/**", "/webfonts/**");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
        	.csrf().disable()
	        .authorizeRequests()
        		.antMatchers("/admin/**").hasRole("ADMIN")
        		.antMatchers("/user/**").hasRole("USER")
        		//.antMatchers("/**").permitAll()
        		.antMatchers("/authenticate").permitAll()
        		.antMatchers("/registration").permitAll()
        		.antMatchers("/login").permitAll()
        		.antMatchers("/token").permitAll()
                .anyRequest().authenticated()    
        	.and()
            	.headers().frameOptions().disable()
            	/*
            .and()
            	.formLogin()
            	.loginPage("/login")
            	.successForwardUrl("/loginSuccess")
                .permitAll()
                
            .and()
            	.logout()
            	.logoutSuccessUrl("/login")
            	.invalidateHttpSession(true)
            	*/
   			.and()
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);    		
    	
    	http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
