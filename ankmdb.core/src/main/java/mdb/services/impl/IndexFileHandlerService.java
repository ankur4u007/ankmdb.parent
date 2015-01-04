package mdb.services.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import mdb.services.IIndexFileHandlerService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

@Component("sp-indexFileHandlerService")
public class IndexFileHandlerService implements IIndexFileHandlerService {

	@Value("${index.location}")
	private String indexLocation;
	@Value("${index.name}")
	private String indexname;

	@Override
	@Cacheable(value = "index.map", key = "#root.methodName")
	public synchronized Map<String, List<String>> getSha1SourceMapFromFile() {
		Preconditions.checkNotNull(indexLocation, "indexLocation have to be set in property config");
		Preconditions.checkNotNull(indexname, "indexname have to be set in property config");
		final File file = new File(indexLocation + indexname);
		final Map<String, List<String>> indexMap = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);
		if (file.exists()) {
			try (final BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
				final String sources = bf.readLine();
				if (sources != null) {

					for (final String source : sources.split(";")) {
						final List<String> indexList = new ArrayList<String>();
						boolean isLineBreakUp = false;
						while (!isLineBreakUp) {
							final String sha1 = bf.readLine();
							if (sha1 == null || "".equals(sha1.trim())) {
								isLineBreakUp = true;
							} else {
								indexList.add(sha1);
							}
						}
						// Preconditions.checkArgument(!indexList.isEmpty(),
						// "Index file is corrupted, please delete it to continue.");
						indexMap.put(source, indexList);
					}
				}
			} catch (final FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return indexMap;

	}

	@Override
	@CacheEvict(value = "index.map", allEntries = true)
	public synchronized void saveMaptoIndexFile(final Map<String, List<String>> indexMap) {
		if (indexMap.size() > 0) {
			Preconditions.checkNotNull(indexLocation, "indexLocation have to be set in property config");
			Preconditions.checkNotNull(indexname, "indexname have to be set in property config");
			final File file = new File(indexLocation + indexname);
			try (final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
				final StringBuilder sb = new StringBuilder();
				for (final String key : indexMap.keySet()) {
					sb.append(key).append(";");
				}
				sb.deleteCharAt(sb.lastIndexOf(";"));
				sb.append("\r\n");
				bw.write(sb.toString());
				for (final List<String> sha1List : indexMap.values()) {
					for (final String sha1 : sha1List) {
						bw.append(sha1).append("\r\n");
					}
					bw.append("").append("\r\n");
				}
			} catch (final FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	@CacheEvict(value = "index.map", allEntries = true, condition = "#result == false", beforeInvocation = false)
	public boolean checkFileExists() {
		final File file = new File(indexLocation + indexname);
		return file.exists();
	}
}
