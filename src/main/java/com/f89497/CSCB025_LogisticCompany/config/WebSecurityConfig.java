package com.f89497.CSCB025_LogisticCompany.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.f89497.CSCB025_LogisticCompany.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
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
            .antMatchers(
				"/registration**",
                "/register**",
				"/js/**",
				"/css/**",
				"/img/**",
				"/about-me").permitAll()
            .antMatchers("/").hasAnyAuthority("CUSTOMER", "EMPLOYEE","ADMIN")
            .antMatchers("/add/**","/delete/**").hasAnyAuthority("ADMIN", "EMPLOYEE")
            .antMatchers("/edit/employee/**","/edit/office/**","/edit/company/**").hasAuthority("ADMIN")
            .antMatchers("/update/employee/**","/update/office/**","/update/company/**").hasAuthority("ADMIN")
            .antMatchers("/delete/employee/**","/delete/office/**","/delete/company/**").hasAuthority("ADMIN")
            .antMatchers("/add/employee/**","/add/office/**","/add/company/**").hasAuthority("ADMIN")
            .antMatchers("/edit/shipment/**","/update/shipment/**").hasAnyAuthority("ADMIN","EMPLOYEE")
            .antMatchers("/customers/**","employee","/shipments","/shipment/**").hasAnyAuthority("ADMIN", "EMPLOYEE")
            .antMatchers("/offices","/companies").hasAuthority("ADMIN")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
            .logout().permitAll()
            .and()
            .exceptionHandling().accessDeniedPage("/403")
            ;
    }
}
