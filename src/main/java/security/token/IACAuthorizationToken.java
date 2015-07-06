package security.token;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class IACAuthorizationToken extends AbstractAuthenticationToken {

	String name;		
	
	public IACAuthorizationToken(String token) {
		
		super(null);
		name = token;
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return name;
	}

}
