
import com.azure.core.credential.TokenCredential;
import com.microsoft.aad.msal4j.AuthenticationResult;
import com.microsoft.aad.msal4j.PublicClientApplication;
import com.microsoft.aad.msal4j.UserNamePasswordParameters;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.DirectoryObject;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.termstore.models.Set;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class PublicClient {

	private final static String APP_ID = "8f0930b6-30c6-4637-bb0a-a6f321d1f476";
	private final static String AUTHORITY = "https://login.microsoftonline.com/organizations";

	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {

		PublicClient pc = new PublicClient();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
//			System.out.print("Enter username: ");
//			String userName = "AlanJ@khupragenicstest.onmicrosoft.com";// br.readLine();
			String userName = "AlanJ@khupragenicstest.onmicrosoft.com";
			String password = "Dummy123!";
//			System.out.print("Enter password: ");
//			String password = "Dummy123!"; // br.readLine();

			// Request access token from AAD
			AuthenticationResult result = pc.getAccessToken(userName, password);

			// Get user info from Microsoft Graph
			Boolean userInfo = pc.validateADUser(result.accessToken());
			System.out.print(userInfo);
		}
	}

	public AuthenticationResult getAccessToken(String userName, String password)
			throws MalformedURLException, InterruptedException, ExecutionException {

		PublicClientApplication pca = PublicClientApplication.builder(APP_ID).authority(AUTHORITY).build();

		String scopes = "User.Read";
		UserNamePasswordParameters parameters = UserNamePasswordParameters
				.builder(Collections.singleton(scopes), userName, password.toCharArray()).build();

		AuthenticationResult result = pca.acquireToken(parameters).get();

		
		return result;
	}

	public Boolean validateADUser(String accessToken) throws IOException {
		URL url = new URL("https://graph.microsoft.com/v1.0/me");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Authorization", "Bearer " + accessToken);
		conn.setRequestProperty("Accept", "application/json");

		int httpResponseCode = conn.getResponseCode();
		if (httpResponseCode == HTTPResponse.SC_OK) {

			StringBuilder response;
			try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {

				String inputLine;
				response = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
			}
			return true;
		} else {
//			return String.format("Connection returned HTTP code: %s with message: %s", httpResponseCode,
//					conn.getResponseMessage());
			return false;
		}
	}

}