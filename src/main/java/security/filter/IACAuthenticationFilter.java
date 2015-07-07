package security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import security.token.IACAuthenticationToken;

public class IACAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
//	public IACAuthenticationFilter() {
//
//		super("/ssologin");
//	}
//	
//	public IACAuthenticationFilter(String defaultFilterProcessesUrl) {
//
//		super(defaultFilterProcessesUrl);
//	    super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(defaultFilterProcessesUrl));
//	}

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		// Authentication authRequest = super.attemptAuthentication(request, response);

		System.out.println(this.getClass().toString());
		
		String ssoUserId = request.getParameter("sso_userid");
		String ssoTicketId = request.getParameter("sso_ticketid");		
		
		if (ssoUserId == null || ssoTicketId == null) {

			return null; // no header found, continue on to other security
							// filters
		}

		// return a new authentication token to be processed by the
		// authentication provider
		
		// return new IACAuthorizationToken(request.getParameter("ssoheader"));

		IACAuthenticationToken authRequest2 = new IACAuthenticationToken(ssoUserId, ssoTicketId);
		
		return this.getAuthenticationManager().authenticate(authRequest2);
	}
	
	@Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		
        super.setAuthenticationManager(authenticationManager);
    }
}
