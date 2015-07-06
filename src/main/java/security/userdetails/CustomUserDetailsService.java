package security.userdetails;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String userId)
			throws UsernameNotFoundException {
		
		// TODO : 取得 UserDetails 資訊包含權限等資訊
		
		System.out.println(this.getClass().toString());
		
		return new User(userId, "1234", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
	}

}
