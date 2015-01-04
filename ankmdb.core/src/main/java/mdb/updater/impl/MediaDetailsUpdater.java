package mdb.updater.impl;

import java.util.Map;

import mdb.updater.IMediaDetailsUpdater;

import org.jsoup.nodes.Document;

public abstract class MediaDetailsUpdater implements IMediaDetailsUpdater {

	public Map<String, Object> updateMedia(final String mediaName) {
		final Document responseDoc = fetchResponse(mediaName);
		return parseResponse(responseDoc);
	}

	protected abstract Document fetchResponse(String mediaName);

	protected abstract Map<String, Object> parseResponse(Document responseDoc);

}
