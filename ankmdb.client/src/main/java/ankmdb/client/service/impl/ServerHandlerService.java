package ankmdb.client.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import mdb.bo.JsonBO;
import mdb.bo.JsonBOBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import ankmdb.client.service.IServerHandlerService;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("sp-serverHandlerService")
public class ServerHandlerService implements IServerHandlerService {

	@Override
	public List<String> getSha1ListOnServer(final String source, final String ankMdbServerURL) throws IOException {
		Preconditions.checkNotNull(ankMdbServerURL, "ankMdbServerURL cant be null");
		List<String> sha1List = null;
		final String result = postToServer(ankMdbServerURL + getSha1ListMethod, JsonBOBuilder.buildJsonFromParameters(null, null, source));
		final Gson gson = new Gson();
		sha1List = gson.fromJson(result.toString(), new TypeToken<List<String>>() {
		}.getType());
		return sha1List;
	}

	@Override
	public String saveMediaMapOnServer(final JsonBO jsonBOToPost, final String ankMdbServerURL) throws IOException {
		Preconditions.checkNotNull(ankMdbServerURL, "ankMdbServerURL cant be null");
		return postToServer(ankMdbServerURL + saveSha1ListMethod, jsonBOToPost);
	}

	private String postToServer(final String ankMdbServerURLAndMethod, final JsonBO jsonBOToPost) throws IOException {
		final CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			final Gson gson = new Gson();
			final HttpPost post = new HttpPost(ankMdbServerURLAndMethod);
			final StringEntity postingString = new StringEntity(gson.toJson(jsonBOToPost));// convert
			post.setEntity(postingString);
			post.setHeader("Content-type", "application/json");
			final HttpResponse response = httpClient.execute(post);
			final BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			final StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
			// handle response here...
		} catch (final UnsupportedEncodingException e) {
			throw e;
		} catch (final ClientProtocolException e) {
			throw e;
		} catch (final IOException e) {
			throw e;
		} finally {
			httpClient.close();
		}
	}

}
