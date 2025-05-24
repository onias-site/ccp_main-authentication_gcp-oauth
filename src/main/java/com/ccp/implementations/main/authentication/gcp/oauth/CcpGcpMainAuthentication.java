package com.ccp.implementations.main.authentication.gcp.oauth;

import com.ccp.dependency.injection.CcpInstanceProvider;
import com.ccp.especifications.main.authentication.CcpAuthenticationProvider;

public class CcpGcpMainAuthentication implements CcpInstanceProvider<CcpAuthenticationProvider> {

	public CcpAuthenticationProvider getInstance() {
		return new GcpOauthAuthenticationProvider();
	}

}
