package site.netlex;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import site.netlex.web.UserDetailServiceImpl;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailServiceImpl userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
        	.authorizeRequests().antMatchers("/css/**").permitAll()
        .and()
        	.authorizeRequests().antMatchers("/rekisteroidy","/saadokset").permitAll()
        .and()
        	// 1/2 uncomment to enable H2 Console 
        	.authorizeRequests().antMatchers("/h2-console/**").permitAll()
        	//-------------------
        .and()
        	.authorizeRequests().anyRequest().authenticated()
        .and()
        // 2/2 uncomment to enable H2 Console 
        .csrf().ignoringAntMatchers("/h2-console/**")
	    .and()
	    .headers().frameOptions().sameOrigin()
	    //-------------------------
	    .and()
	    	.formLogin()
	    		.loginPage("/kirjaudu")
	    		.loginProcessingUrl("/login")
	    		.defaultSuccessUrl("/saadokset",true).permitAll()
	    		.and()
	    		.logout().permitAll();
	}
	
	//Used to ignore spring security when post request is sent to an endpoint.
	public void configure(WebSecurity web)
            throws Exception {
	web.ignoring().antMatchers(HttpMethod.POST, "/api/**");
	}
	
	//Creating USER and ADMIN type custom classes.
	@Bean
    @Override
    public UserDetailsService userDetailsService() {
        List<UserDetails> users = new ArrayList();

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        UserDetails user = User
        		.withUsername("user")
        		.password(passwordEncoder.encode("user"))
        		.roles("USER")
        		.build();

        users.add(user);

        user = User
        		.withUsername("admin")
        		.password(passwordEncoder.encode("admin"))
        		.roles("ADMIN")
        		.build();

    	users.add(user);

        return new InMemoryUserDetailsManager(users);
    }

	
}
