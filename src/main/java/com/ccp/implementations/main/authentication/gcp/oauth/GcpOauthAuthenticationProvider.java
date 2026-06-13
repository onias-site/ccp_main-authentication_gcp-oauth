package com.ccp.implementations.main.authentication.gcp.oauth;

import java.io.InputStream;
import java.util.Collections;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.ccp.decorators.CcpInputStreamDecorator;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.especifications.main.authentication.CcpAuthenticationProvider;

/**
 * Implementação de {@code CcpAuthenticationProvider} via GCP OAuth. Lê as credenciais do arquivo
 * indicado pela variável de ambiente {@code GOOGLE_APPLICATION_CREDENTIALS} e retorna um token
 * de acesso JWT com escopo {@code cloud-platform}.
 */
public class GcpOauthAuthenticationProvider implements CcpAuthenticationProvider{

	
	public String getJwtToken() {
		try {
			CcpStringDecorator ccpStringDecorator = new CcpStringDecorator("GOOGLE_APPLICATION_CREDENTIALS");
			CcpInputStreamDecorator inputStreamFrom = ccpStringDecorator.inputStreamFrom();
			InputStream file = inputStreamFrom.fromEnvironmentVariablesOrClassLoaderOrFile();
			GoogleCredential credential = GoogleCredential.fromStream(file)
					.createScoped(Collections.singleton("https://www.googleapis.com/auth/cloud-platform"));
			credential.refreshToken();
			String accessToken = credential.getAccessToken();
			return accessToken;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
