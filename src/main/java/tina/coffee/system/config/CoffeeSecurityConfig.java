package tina.coffee.system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import tina.coffee.system.security.encoder.SystemPasswordEncoder;
import tina.coffee.system.security.handler.CoffeeAccessDeniedHandler;
import tina.coffee.system.security.handler.CoffeeAuthenticationSuccessHandler;
import tina.coffee.system.security.handler.CoffeeLogoutSuccessHandler;
import tina.coffee.system.security.service.CoffeeUserDetailsService;

@Configuration
@EnableWebSecurity
public class CoffeeSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CoffeeAccessDeniedHandler deniedHandler;

    @Autowired
    private CoffeeAuthenticationSuccessHandler successHandler;

    @Autowired
    private CoffeeLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    public CoffeeUserDetailsService userDetailsService;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        String[] staticResources = new String[]{
                "/resources/**",
                "/jquery/**",
                "/webjars/**",
                "/bootstrap/**"
        };
        web.ignoring().antMatchers(staticResources);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        //This is for normal web page
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/about").permitAll()
                .antMatchers("/poc/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().permitAll()
                .successHandler(successHandler)
                .and().exceptionHandling().accessDeniedHandler(deniedHandler)
                .and().logout().logoutSuccessHandler(logoutSuccessHandler)
                .and().csrf().disable();


        //This is for REST
        http.httpBasic();

    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new SystemPasswordEncoder();
    }
}
