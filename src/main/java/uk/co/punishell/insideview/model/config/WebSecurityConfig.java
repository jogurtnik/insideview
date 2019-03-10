package uk.co.punishell.insideview.model.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                    .permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/showLoginPage")
                    .loginProcessingUrl("/authenticateTheUser")
                    .failureUrl("/login-error")
                    .permitAll()
                    .and()
                .logout()
                    .deleteCookies("remove")
                    .invalidateHttpSession(true)
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/logout-success")
                    .permitAll();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

        User.UserBuilder users = User.withDefaultPasswordEncoder();

        auth.inMemoryAuthentication()
                .withUser(users.username("admin").password("shitronica").roles("USER", "ADMIN"));
    }

}
