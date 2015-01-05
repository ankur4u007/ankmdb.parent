package mdb.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mdb.bo.EntryBO;
import mdb.bo.MediaBO;
import mdb.processors.IMediaProcessor;
import mdb.services.IIndexHandlerService;
import mdb.services.IIndexService;
import mdb.services.ISerializeDBService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;

@Component("sp-indexService")
public class IndexService implements IIndexService {

	@Autowired
	@Qualifier(value = "sp-indexHandlerService")
	private IIndexHandlerService indexHandlerService;

	@Autowired
	@Qualifier(value = "sp-mediaProcessor")
	private IMediaProcessor mediaProcessor;

	@Autowired
	@Qualifier(value = "sp-serializeDBService")
	private ISerializeDBService serializeDBService;

	@Value("#{'server'.equals('${env.machine}')}")
	private boolean isServerEnv;

	@Value("${server.url}")
	private String serverlUrl;

	@Value("${local.url}")
	private String localUrl;

	@Override
	public List<String> getSha1ListBySource(final String source) {
		return indexHandlerService.findSha1ListBySource(source);
	}

	@Override
	public Set<EntryBO> getAllMediaFromDBFile() {
		Set<EntryBO> entryBOSet = null;
		try {
			if (serializeDBService.checkSerFileExists()) {
				entryBOSet = serializeDBService.getListFromDBFile();
			}
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entryBOSet;
	}

	@Override
	public void performDelete(final List<String> sha1ListToRemove, final String source) {
		if (sha1ListToRemove != null && !sha1ListToRemove.isEmpty()) {
			deleteIndexesForSource(sha1ListToRemove, source);
			deleteMediaFromDbFile(sha1ListToRemove);
		}
	}

	@Override
	public void performSave(final Map<String, MediaBO> mediaAndSha1KeyMap, final String source) {
		// saving consists of two operations
		// updating the indexes and then updating the serialised files
		if (mediaAndSha1KeyMap != null && !mediaAndSha1KeyMap.isEmpty()) {
			final List<String> sha1IdList = new ArrayList<String>();
			sha1IdList.addAll(mediaAndSha1KeyMap.keySet());
			if (!sha1IdList.isEmpty()) {
				addIndexesForSource(sha1IdList, source);
				saveMediaToDbFile(mediaAndSha1KeyMap);
			}

		}
	}

	private void deleteIndexesForSource(final List<String> sha1IdList, final String source) {
		indexHandlerService.deleteIndexesForSource(sha1IdList, source);
	}

	private void addIndexesForSource(final List<String> sha1IdList, final String source) {
		indexHandlerService.addIndexesForSource(sha1IdList, source);
	}

	private void deleteMediaFromDbFile(final List<String> sha1IdListToRemove) {
		if (sha1IdListToRemove != null && !sha1IdListToRemove.isEmpty()) {
			Set<EntryBO> existingEntryBOSet = getAllMediaFromDBFile();
			if (existingEntryBOSet != null && !existingEntryBOSet.isEmpty()) {
				try {
					existingEntryBOSet = new HashSet<EntryBO>(FluentIterable.from(Collections2.filter(existingEntryBOSet, new Predicate<EntryBO>() {
						@Override
						public boolean apply(final EntryBO bo) {
							return !sha1IdListToRemove.contains(bo.getMediaBoHash());
						}
					})).toSet());
					serializeDBService.saveListToDBFile(existingEntryBOSet);
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void saveMediaToDbFile(final Map<String, MediaBO> mediaAndSha1KeyMap) {
		int numberOfProc = Runtime.getRuntime().availableProcessors();
		numberOfProc = numberOfProc <= 1 ? 2 : numberOfProc;
		final ExecutorService executor = Executors.newFixedThreadPool(numberOfProc);
		executor.execute(new Runnable() {
			@Override
			public void run() {
				saveMedia(mediaAndSha1KeyMap);
			}
		});
	}

	private void saveMedia(final Map<String, MediaBO> mediaAndSha1KeyMap) {
		if (mediaAndSha1KeyMap != null) {
			try {
				Set<EntryBO> existingEntryBOSet = null;
				if (serializeDBService.checkSerFileExists()) {
					existingEntryBOSet = getAllMediaFromDBFile();
				}
				if (existingEntryBOSet == null) {
					existingEntryBOSet = new HashSet<EntryBO>();
				}
				final List<EntryBO> newEntryBOList = mediaProcessor.updateMediaDetails(mediaAndSha1KeyMap);
				existingEntryBOSet.addAll(newEntryBOList);
				if (existingEntryBOSet != null && !existingEntryBOSet.isEmpty()) {
					serializeDBService.saveListToDBFile(existingEntryBOSet);
				}
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getPostUrl() {
		String postUrl = null;
		if (isServerEnv) {
			postUrl = serverlUrl;
		} else {
			postUrl = localUrl;
		}
		return postUrl;
	}

}
