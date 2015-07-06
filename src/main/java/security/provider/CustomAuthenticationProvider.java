package security.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		
		System.out.println(this.getClass().toString());
						
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		// TODO : 呼叫  YBD API 或測試認證方式進行帳號驗證
				
		if (name.equals("admin") && password.equals("system")) {
			List<GrantedAuthority> grantedAuths = new ArrayList<>();
			grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
			Authentication auth = new UsernamePasswordAuthenticationToken(name,
					password, grantedAuths);
			
			System.out.println(this.getClass().toString()+"-2");
			
			return auth;
		} else {
			
			System.out.println(this.getClass().toString()+"-3");
			
			throw new BadCredentialsException("BadCredentialsException");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}