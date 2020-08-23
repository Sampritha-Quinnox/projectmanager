package quinnox.sam.projectmanager.webconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import quinnox.sam.projectmanager.serviceimpl.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
/*@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)*/
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/edit/**","/new/**", "/delete/**").hasRole("ADMIN")
			//.antMatchers("/apply/**").hasRole("USER")
			.antMatchers("/registration/**" , "/view/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.permitAll()
			.and()
			.logout().permitAll()
			;
	}
	

	
	

}