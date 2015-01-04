package mdb.bo;

import java.util.List;
import java.util.Map;

public class JsonBOBuilder {

	private JsonBOBuilder() {
		// precvent init
	}

	public static JsonBO buildJsonFromParameters(final Map<String, MediaBO> mediaAndSha1KeyMap, final List<String> sha1keyList, final String source) {
		final JsonBO jsonBO = new JsonBO();
		jsonBO.setMediaAndSha1KeyMap(mediaAndSha1KeyMap);
		jsonBO.setSha1keyList(sha1keyList);
		jsonBO.setSource(source);
		return jsonBO;
	}
}
