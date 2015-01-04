package mdb.services;

import java.util.List;
import java.util.Map;

public interface IIndexFileHandlerService {

	Map<String, List<String>> getSha1SourceMapFromFile();

	void saveMaptoIndexFile(Map<String, List<String>> indexMap);

	boolean checkFileExists();
}
