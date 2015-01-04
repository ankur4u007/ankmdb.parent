package mdb.processors;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import mdb.bo.EntryBO;
import mdb.bo.MediaBO;

public interface IMediaProcessor {

	List<MediaBO> findMedia(List<String> mediaLocations, String mediaSource, List<String> mediaFormatLists) throws IOException;

	List<EntryBO> updateMediaDetails(Map<String, MediaBO> mediaAndSha1KeyMap);

	String getSHA1ofMediaBO(final MediaBO mediaBO) throws IOException;
}
