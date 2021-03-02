package com.example.demo;

import com.example.demo.security.CustomUserDetailService;
import com.example.demo.security.MyAuthenticationFailureHandler;
import com.example.demo.security.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.session.MapSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@RequestMapping("/resource")
	public Map<String,Object> home() {
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello World");
		return model;
	}

	@RequestMapping("/user")
	public Principal user(Principal user){
	    return user;
    }

    @EnableSpringHttpSession
    @EnableWebSecurity
    @Configuration
    //@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected  static class  SecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Bean
        public SessionRepository<MapSession> sessionRepository() {
            return new MapSessionRepository(new ConcurrentHashMap<>());
        }

        @Bean
        public HttpSessionIdResolver sessionIdResolver() {
            return HeaderHttpSessionIdResolver.xAuthToken();
        }

        @Autowired
        private CustomUserDetailService customUserDetailService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //super.configure(http);
            http
                    //.httpBasic()
                    //.and().
                    .formLogin()
                    .successHandler(new MyAuthenticationSuccessHandler())
                    .failureHandler(new MyAuthenticationFailureHandler())
                    //.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .and().csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/index.html", "/", "home", "login", "/tel/login").permitAll()
                    .anyRequest().authenticated();


        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(customUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
        }


    }
}
