package portal.domain.impl;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

public class Authority extends DataEntity implements GrantedAuthority, Serializable {
	private static final long serialVersionUID = 1L;

	private String authority;

	@Override
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
