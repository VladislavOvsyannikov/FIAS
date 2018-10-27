package javaconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import system.service.Provider;

@Configuration
@EnableWebSecurity
@ComponentScan("system")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private Provider authProvider;
    @Autowired
    public void setAuthProvider(Provider authProvider) {
        this.authProvider = authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/admin").hasAuthority("ROLE_ADMIN")
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
}
