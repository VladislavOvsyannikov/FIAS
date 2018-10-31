package javaconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import system.service.TokenAuthenticationManager;

@Configuration
@EnableWebSecurity
@ComponentScan("system")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private TokenAuthenticationManager tokenAuthenticationManager;

    @Autowired
    public void setTokenAuthenticationManager(TokenAuthenticationManager tokenAuthenticationManager) {
        this.tokenAuthenticationManager = tokenAuthenticationManager;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        auth.parentAuthenticationManager(tokenAuthenticationManager);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterAfter(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests().antMatchers("/admin").hasAuthority("ROLE_ADMIN")
                                    .antMatchers("/rest/**").authenticated()
                                    .antMatchers("/user").authenticated()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/user").failureUrl("/login")
                .and()
                .exceptionHandling().accessDeniedPage("/login")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/user")
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }

    @Bean(name = "tokenAuthenticationFilter")
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }
}
