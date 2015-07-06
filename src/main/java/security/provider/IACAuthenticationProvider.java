package security.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import security.token.IACAuthorizationToken;

@Component
public class IACAuthenticationProvider implements AuthenticationProvider  {

	@Override
	public Authentication authenticate(Authentication arg0)
			throws AuthenticationException {
		
		// TODO : 處理 SSO 認證
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		
		System.out.println("test : " + attr.getRequest().getParameter("ssoheader"));
		
		System.out.println(this.getClass().toString());
		
		List<GrantedAuthority> grantedAuths = new ArrayList<>();
		grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
		Authentication auth = new UsernamePasswordAuthenticationToken(arg0.getPrincipal(),
				arg0.getCredentials(), grantedAuths);
		
		System.out.println(this.getClass().toString()+"-2");
		
		return auth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		
		return authentication.equals(IACAuthorizationToken.class);
	}

}
