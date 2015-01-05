package mdb.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import mdb.bo.EntryBO;
import mdb.bo.MediaBO;

public interface IIndexService {

	List<String> getSha1ListBySource(String source);

	Set<EntryBO> getAllMediaFromDBFile();

	void performSave(Map<String, MediaBO> mediaAndSha1KeyMap, String source);

	void performDelete(List<String> sha1ListToRemove, String source);

	String getPostUrl();
}
