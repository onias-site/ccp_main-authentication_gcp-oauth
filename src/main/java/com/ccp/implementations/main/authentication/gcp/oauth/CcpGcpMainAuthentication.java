package com.ccp.implementations.main.authentication.gcp.oauth;

import com.ccp.dependency.injection.CcpInstanceProvider;
import com.ccp.especifications.main.authentication.CcpAuthenticationProvider;

/**
 * DI provider that exposes {@code GcpOauthAuthenticationProvider} as the
 * {@code CcpAuthenticationProvider} implementation.
 */
public class CcpGcpMainAuthentication implements CcpInstanceProvider<CcpAuthenticationProvider> {

	public CcpAuthenticationProvider getInstance() {
		GcpOauthAuthenticationProvider gcpOauthAuthenticationProvider = new GcpOauthAuthenticationProvider();
		return gcpOauthAuthenticationProvider;
	}

}
