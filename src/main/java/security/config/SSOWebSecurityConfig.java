package security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import security.manager.IACAuthenticationManager;
import security.filter.IACAuthenticationFilter;
import security.provider.CustomAuthenticationProvider;
import security.provider.IACAuthenticationProvider;
import security.userdetails.CustomUserDetailsService;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@ConditionalOnProperty(prefix = "cht.security", name = "authtype", havingValue = "sso", matchIfMissing = false)
public class SSOWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired 
	private IACAuthenticationFilter iacAuthenticationFilter;
		
	@Autowired 
	private IACAuthenticationProvider iacauthenticationProvider;
	
    @Override    
    protected void configure(HttpSecurity http) throws Exception {

    	http
        	.addFilterBefore(iacAuthenticationFilter, UsernamePasswordAuthenticationFilter.class )
            .authorizeRequests()
                .antMatchers("/", "/home").permitAll()  
                .antMatchers("/ssologin").permitAll() 
                .antMatchers("/hello").hasRole("USER")
                .anyRequest().authenticated()
                .and()
            .formLogin().loginPage("/ssologin")
            	.and()
            .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

//        auth
//            .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
    	auth.authenticationProvider(iacauthenticationProvider);
    }
    
//    @Bean
//    public AuthenticationManager IACAuthenticationManager() {
//
//        return new IACAuthenticationManager();
//    }
   
    @Bean
    public IACAuthenticationFilter iacAuthenticationFilter() {
    	
    	IACAuthenticationFilter filter = new IACAuthenticationFilter();
    	filter.setFilterProcessesUrl("/ssologin");
    	//filter.setAuthenticationSuccessHandler(savedRequestAwareAuthenticationSuccessHandler());
    	//filter.setAuthenticationFailureHandler(simpleUrlAuthenticationFailureHandler());
    	// filter.setUsernameParameter("sso_userid");
    	// filter.setPasswordParameter("sso_ticketid");
    	filter.setPostOnly(false);
    	
    	return filter;
    }
    
    @Bean
    public LoginUrlAuthenticationEntryPoint authenticationEntryPoint() {

        return new LoginUrlAuthenticationEntryPoint("/ssologin");
    }
    
    @Bean
    public SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler() {
    	
    	return new SimpleUrlAuthenticationFailureHandler("/ssologin?error=1");
    }
    
    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
    	
    	SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    	
    	successHandler.setDefaultTargetUrl("/home");
    			
    	return successHandler;
    }
}