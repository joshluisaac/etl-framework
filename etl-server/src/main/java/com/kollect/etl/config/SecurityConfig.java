package com.kollect.etl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";

    /**
     * This is used in conjunction with Spring Boot Security for login
     *
     * @param httpSecurity calls the HTTPSecurity method from HttpSecurity.java class, necessary to add various configurations
     * @throws Exception throws exception if any
     */

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests()
                .antMatchers("/", "/index**", "/datavisualiser", "/dctablesettings",
                        "/dsv", "/misc", "/addbatch", "/batch", "/run**", "/allbatchhistory", "/logerrorvisualizer").hasAnyRole(ADMIN, USER)
                .antMatchers("/adminEmailSettings", "/adminSftp", "/adminDatabase", "/host**", "/usermanagement**"
                , "/emailupdate").hasRole(ADMIN)
                .and()
                .formLogin().loginPage("/login")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                .and()
                .exceptionHandling().accessDeniedPage("/403");

    }

    /**
     * This is used to create users (hardcoded)
     *
     * @param auth this calls the AuthenticationManagerBuilder.java class
     * @throws Exception throws exception if any
     */

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("kvadmin@kollect.my").password("kv1234").roles(ADMIN)
                .and()
                .withUser("kvuser@kollect.my").password("kv1234").roles(USER)
                .and()
                .withUser("naveen.kollect@gmail.com").password("kv1234").roles(ADMIN);

    }

}
