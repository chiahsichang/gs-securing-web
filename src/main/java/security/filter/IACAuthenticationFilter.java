package security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import security.token.IACAuthorizationToken;

@Component
public class IACAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public IACAuthenticationFilter() {
		
		super("/ssologin"); // allow any request to contain an authorization header
	}
	
	public Authentication attemptAuthentication(HttpServletRequest req,
			HttpServletResponse res) throws AuthenticationException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		System.out.println(this.getClass().toString());
		
		if (request.getParameter("ssoheader") == null) {
			
			return null; // no header found, continue on to other security
					     // filters
		}

		IACAuthorizationToken authRequest = new IACAuthorizationToken(request.getParameter("ssoheader"));
		
		// return a new authentication token to be processed by the
		// authentication provider
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	@Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		
        super.setAuthenticationManager(authenticationManager);
    }
}
