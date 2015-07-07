package security.provider;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import security.token.IACAuthenticationToken;

@Component
public class IACAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private HttpServletRequest request;

	@Override
	public Authentication authenticate(Authentication arg0) throws AuthenticationException {

		// TODO : 處理 SSO 認證

		String ssoUserId = request.getParameter("sso_userid");
		String ssoTicketId = request.getParameter("sso_ticketid");

		System.out.println(this.getClass().toString());

		List<GrantedAuthority> grantedAuths = new ArrayList<>();
		grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));

		User user = new User(ssoUserId, ssoTicketId, grantedAuths);

		Authentication auth = new IACAuthenticationToken(user, ssoTicketId, grantedAuths);

		System.out.println(this.getClass().toString() + "-2");

		return auth;
	}

	@Override
	public boolean supports(Class<?> authentication) {

		return authentication.equals(IACAuthenticationToken.class);
	}

}
