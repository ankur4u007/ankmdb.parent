package mdb.bo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class JsonBO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected String source;
	protected List<String> sha1keyList;
	protected Map<String, MediaBO> mediaAndSha1KeyMap;

	public String getSource() {
		return source;
	}

	public void setSource(final String source) {
		this.source = source;
	}

	public List<String> getSha1keyList() {
		return sha1keyList;
	}

	public void setSha1keyList(final List<String> sha1keyList) {
		this.sha1keyList = sha1keyList;
	}

	public Map<String, MediaBO> getMediaAndSha1KeyMap() {
		return mediaAndSha1KeyMap;
	}

	public void setMediaAndSha1KeyMap(final Map<String, MediaBO> mediaAndSha1KeyMap) {
		this.mediaAndSha1KeyMap = mediaAndSha1KeyMap;
	}

}
