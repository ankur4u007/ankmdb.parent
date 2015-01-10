/**
 *
 */
package mdb.services.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import mdb.services.IIndexFileHandlerService;
import mdb.services.IIndexHandlerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

/**
 * @author CHANDRAYAN
 *
 */
@Component("sp-indexHandlerService")
public class IndexHandlerService implements IIndexHandlerService {

	@Autowired
	@Qualifier(value = "sp-indexFileHandlerService")
	private IIndexFileHandlerService indexFileHandlerService;

	@Override
	public List<String> findSha1ListBySource(final String source) {
		List<String> sha1List = null;
		if (indexFileHandlerService.checkFileExists()) {
			final Map<String, List<String>> indexMap = indexFileHandlerService.getSha1SourceMapFromFile();
			if (indexMap != null) {
				sha1List = indexMap.get(source);
			}
		}
		return sha1List;
	}

	@Override
	public void addIndexesForSource(final List<String> sha1IdList, final String source) {
		Preconditions.checkNotNull(source, "source can't be null");
		Preconditions.checkNotNull(sha1IdList, "sha1IdList can't be null");
		if (!sha1IdList.isEmpty()) {
			Map<String, List<String>> indexMap = null;
			if (indexFileHandlerService.checkFileExists()) {
				indexMap = indexFileHandlerService.getSha1SourceMapFromFile();
			}
			if (indexMap == null) {
				indexMap = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);
			}
			if (!indexMap.isEmpty()) {
				List<String> existingSha1List = indexMap.get(source);
				if (existingSha1List != null) {
					existingSha1List.addAll(sha1IdList);
				} else {
					existingSha1List = sha1IdList;
				}
				indexMap.put(source, existingSha1List);
			} else {
				indexMap.put(source, sha1IdList);
			}
			indexFileHandlerService.saveMaptoIndexFile(indexMap);
		}

	}

	@Override
	public void deleteIndexesForSource(final List<String> sha1IdList, final String source) {
		Preconditions.checkNotNull(source, "source can't be null");
		Preconditions.checkNotNull(sha1IdList, "sha1IdList can't be null");
		if (!sha1IdList.isEmpty()) {
			Map<String, List<String>> indexMap = null;
			if (indexFileHandlerService.checkFileExists()) {
				indexMap = indexFileHandlerService.getSha1SourceMapFromFile();
			}
			if (indexMap != null && !indexMap.isEmpty()) {
				final List<String> existingSha1List = indexMap.get(source);
				existingSha1List.removeAll(sha1IdList);
				indexMap.put(source, existingSha1List);
				indexFileHandlerService.saveMaptoIndexFile(indexMap);
			}
		}

	}

}
