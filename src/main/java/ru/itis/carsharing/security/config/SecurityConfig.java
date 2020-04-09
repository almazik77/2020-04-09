package ru.itis.carsharing.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;
import ru.itis.carsharing.security.filters.TokenAuthFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Order(2)
    @Configuration
    public static class ApiConfiguration extends WebSecurityConfigurerAdapter {
        @Autowired
        private AuthenticationProvider authenticationProvider;

        @Autowired
        @Qualifier(value = "jwtAuthenticationFilter")
        private GenericFilterBean jwtAuthenticationFilter;

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/api/login");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.formLogin().disable();
            http.logout().disable();
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class);
        }

        @Autowired
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider);
        }


    }

    @Order(1)
    @Configuration
    public static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private String rememberMeKey;

        @Qualifier("userDetailsServiceImpl")
        @Autowired
        private UserDetailsService userDetailsService;


        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.authorizeRequests()
                    .and()
                    .formLogin()
                    .loginPage("/signIn")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/profile")
                    .failureUrl("/signIn?error")
                    .permitAll()

                    .and()
                    .logout()
                    .logoutSuccessUrl("/signIn").permitAll()

                    .and()
                    .rememberMe()
                    .key(rememberMeKey);

        }


        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }
    }
}
