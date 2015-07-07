package security.token;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class IACAuthenticationToken extends UsernamePasswordAuthenticationToken {

	public IACAuthenticationToken(Object principal, Object credentials) {
		
		super(principal, credentials);
		// TODO Auto-generated constructor stub
	}
	
	public IACAuthenticationToken(Object principal, Object credentials,  Collection<? extends GrantedAuthority> authorities) {
		
		super(principal, credentials, authorities);
		// TODO Auto-generated constructor stub
	}
}
