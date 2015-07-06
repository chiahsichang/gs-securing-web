package security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import security.provider.CustomAuthenticationProvider;
import security.provider.IACAuthenticationProvider;
import security.userdetails.CustomUserDetailsService;

@Configuration
@EnableWebMvcSecurity
@ConditionalOnProperty(prefix = "cht.security", name = "authtype", havingValue = "sdptest", matchIfMissing = true)
public class SDPTestWebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired 
	private CustomAuthenticationProvider authenticationProvider;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	   	
        http
            .authorizeRequests()
                .antMatchers("/", "/home").permitAll()  
                .antMatchers("/hello").hasRole("USER")
                .anyRequest().authenticated()
                .and()            
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
    	auth.authenticationProvider(authenticationProvider);
    }
}