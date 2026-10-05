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

	
	/**
	 * Reads the credentials named by {@code GOOGLE_APPLICATION_CREDENTIALS} (environment variable, classpath or file),
	 * refreshes an access token with the {@code cloud-platform} scope and returns it.
	 * @return the access token
	 * @throws CcpErrorGcpOauthTokenRefresh when the credentials cannot be read or the token cannot be refreshed
	 */
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

	/** Raised when the GCP access token cannot be obtained. */
	@SuppressWarnings("serial")
	private static class CcpErrorGcpOauthTokenRefresh extends RuntimeException {
		/**
		 * Wraps the cause.
		 * @param cause the original failure
		 */
		private CcpErrorGcpOauthTokenRefresh(Throwable cause) {
			super(cause);
		}
	}
}
