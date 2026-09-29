package com.ccp.implementations.main.authentication.gcp.oauth;

import java.io.InputStream;
import java.util.Collections;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.ccp.decorators.CcpInputStreamDecorator;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.especifications.main.authentication.CcpAuthenticationProvider;

/**
 * {@code CcpAuthenticationProvider} implementation via GCP OAuth. Reads the credentials from the file
 * pointed to by the {@code GOOGLE_APPLICATION_CREDENTIALS} environment variable and returns a JWT
 * access token with the {@code cloud-platform} scope.
 */
public class GcpOauthAuthenticationProvider implements CcpAuthenticationProvider{

	
	public String getJwtToken() {
		CcpStringDecorator credentialsVariableName = new CcpStringDecorator("GOOGLE_APPLICATION_CREDENTIALS");
		CcpInputStreamDecorator credentialsInputStreamDecorator = credentialsVariableName.inputStreamFrom();
		InputStream credentialsStream = credentialsInputStreamDecorator.fromEnvironmentVariablesOrClassLoaderOrFile();
		try {
			GoogleCredential googleCredential = GoogleCredential.fromStream(credentialsStream);
			GoogleCredential credential = googleCredential
					.createScoped(Collections.singleton("https://www.googleapis.com/auth/cloud-platform"));
			credential.refreshToken();
			String accessToken = credential.getAccessToken();
			return accessToken;
		} catch (Exception e) {
			CcpErrorGcpOauthTokenRefresh ccpErrorGcpOauthTokenRefresh = new CcpErrorGcpOauthTokenRefresh(e);
			throw ccpErrorGcpOauthTokenRefresh;
		}
	}

	@SuppressWarnings("serial")
	private static class CcpErrorGcpOauthTokenRefresh extends RuntimeException {
		private CcpErrorGcpOauthTokenRefresh(Throwable cause) {
			super(cause);
		}
	}
}
