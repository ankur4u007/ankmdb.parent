package ankmdb.client.service.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import mdb.bo.JsonBOBuilder;
import mdb.bo.MediaBO;
import mdb.processors.IMediaProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ankmdb.client.service.IClientService;
import ankmdb.client.service.IServerHandlerService;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;

@Component("sp-clientService")
public class ClientService implements IClientService {

	@Autowired
	@Qualifier(value = "sp-clientPropertiesBean")
	private Properties ankMdbProperties;

	@Autowired
	@Qualifier(value = "sp-mediaProcessor")
	private IMediaProcessor mediaProcessor;

	@Autowired
	@Qualifier(value = "sp-serverHandlerService")
	private IServerHandlerService serverHandlerService;

	@Override
	public void process() {
		System.out.println("Media List is being uploaded to server, please wait...");
		// TODO Auto-generated method stub
		Preconditions.checkNotNull(ankMdbProperties, "ankmdb.client.properties init failed");
		Preconditions.checkNotNull(mediaProcessor, "mediaProcessor cant be null");

		final String mediaLocations = ankMdbProperties.getProperty("media.location", null);
		String source = ankMdbProperties.getProperty("media.source", null);
		final String formats = ankMdbProperties.getProperty("media.formats", null);
		final String serverUrl = ankMdbProperties.getProperty("ankmdb.sever.url", null);
		if (source == null || "".equals(source.trim())) {
			source = getComputerName();
		}

		Preconditions.checkNotNull(mediaLocations, "mediaLocations cant be null");
		Preconditions.checkNotNull(formats, "formats cant be null");
		Preconditions.checkNotNull(source, "source cant be null");
		Preconditions.checkNotNull(serverUrl, "serverUrl cant be null");

		List<MediaBO> mediaList = null;
		String response = "No Media to add!";
		try {
			mediaList = mediaProcessor.findMedia(Arrays.asList(mediaLocations.split(";")), source, Arrays.asList(formats.split(";")));
			if (mediaList != null) {
				final List<String> sha1ListFromServer = serverHandlerService.getSha1ListOnServer(source, serverUrl);
				final List<String> sha1ListFromClient = new ArrayList<String>();

				final Map<String, MediaBO> mediaAndSha1KeyMap = new TreeMap<String, MediaBO>(String.CASE_INSENSITIVE_ORDER);
				for (final MediaBO mediaBO : mediaList) {
					final String sha1Key = mediaProcessor.getSHA1ofMediaBO(mediaBO);
					if (sha1Key != null) {
						if (sha1ListFromServer == null || sha1ListFromServer.isEmpty()) {
							mediaAndSha1KeyMap.put(sha1Key, mediaBO);
						} else if (!sha1ListFromServer.contains(sha1Key)) {
							mediaAndSha1KeyMap.put(sha1Key, mediaBO);
						}
						sha1ListFromClient.add(sha1Key);
					}
				}
				List<String> toRemoveSha1List = null;
				if (sha1ListFromServer != null && !sha1ListFromServer.isEmpty()) {
					toRemoveSha1List = FluentIterable
							.from(Collections2.filter(sha1ListFromServer, Predicates.not(Predicates.in(sha1ListFromClient)))).toList();
				}
				response = serverHandlerService.saveMediaMapOnServer(
						JsonBOBuilder.buildJsonFromParameters(mediaAndSha1KeyMap, toRemoveSha1List, source), serverUrl);
			}
		} catch (final IOException e) {
			response = e.getMessage();
		}
		System.out.println(response);
	}

	private String getComputerName() {
		String hostname = null;
		try {
			final InetAddress addr = InetAddress.getLocalHost();
			hostname = addr.getHostName();
		} catch (final UnknownHostException e) {
			hostname = null;
		}
		return hostname;
	}
}
