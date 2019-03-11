package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan("system")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterAfter(tokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin().loginPage("/fias/signin")
                .and()
                .exceptionHandling().accessDeniedPage("/fias/signin")
                .and()
                .logout().logoutUrl("/fias/signout").logoutSuccessUrl("/fias/signin")
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }

    @Bean(name = "tokenAuthenticationFilter")
    public TokenFilter tokenFilter() {
        return new TokenFilter();
    }
}
