/**
 *
 */
package mdb.processors.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mdb.bo.EntryBO;
import mdb.bo.EntryBOBuilder;
import mdb.bo.MediaBO;
import mdb.bo.MediaBOBuilder;
import mdb.bo.MoviesBOBuilder;
import mdb.processors.IMediaProcessor;
import mdb.searchNameGenerator.ISerachNameGenerator;
import mdb.updater.IMediaDetailsUpdater;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

/**
 * @author CHANDRAYAN
 *
 */
@Component("sp-mediaProcessor")
public class MediaProcessor implements IMediaProcessor {

	@Value("#{'${text.to.filter}'.split(';')}")
	private List<String> filterTextList;

	@Autowired
	@Qualifier(value = "sp-imdbUpdater")
	private IMediaDetailsUpdater mediaDetailsUpdater;

	@Autowired
	@Qualifier(value = "sp-searchNameGenerator")
	private ISerachNameGenerator serachNameGenerator;

	private static Logger log = LoggerFactory.getLogger(MediaProcessor.class);

	@Override
	public List<MediaBO> findMedia(final List<String> mediaLocations, final String mediaSource, final List<String> mediaFormatLists)
			throws IOException {
		Preconditions.checkNotNull(mediaLocations, "Media Location cant be null");
		Preconditions.checkArgument(!mediaLocations.isEmpty(), "Media Location should not be empty");
		final List<MediaBO> mediaBOList = new ArrayList<MediaBO>();
		for (final String stringpath : mediaLocations) {
			final File file = new File(stringpath);
			Preconditions.checkNotNull(!file.isDirectory(), "Location:" + stringpath + " should be a directory");
			final Path path = Paths.get(stringpath);
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
					final String fileName = file.getFileName().toString();
					String format = null;
					String mediaName = null;
					final String[] namesArray = fileName.split("\\.");
					if (namesArray != null) {
						format = namesArray[namesArray.length - 1];
						format = format != null ? format.trim().toLowerCase() : null;
						final StringBuilder sb = new StringBuilder();
						for (int i = 0; i < namesArray.length - 1; i++) {
							sb.append(namesArray[i]).append(" ");
						}
						mediaName = sb.toString().trim();
					}
					mediaBOList.add(MediaBOBuilder.buildBoFromDetails(mediaName, new Date(attrs.lastModifiedTime().toMillis()), attrs.size(), file
							.getParent().toAbsolutePath().toString(),
							serachNameGenerator.getSearchableNames(mediaName, format, mediaFormatLists, filterTextList), format, mediaSource));
					return super.visitFile(file, attrs);
				}
			});

		}
		return mediaBOList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<EntryBO> updateMediaDetails(final Map<String, MediaBO> mediaAndSha1KeyMap) {
		List<EntryBO> entryBOList = null;
		if (mediaAndSha1KeyMap != null && !mediaAndSha1KeyMap.isEmpty()) {
			entryBOList = new ArrayList<EntryBO>();
			for (final String mediaHash : mediaAndSha1KeyMap.keySet()) {
				final MediaBO mediaBO = mediaAndSha1KeyMap.get(mediaHash);
				Map<String, Object> responseMap = null;
				for (final String searchableName : mediaBO.getSearchableNames()) {
					if (searchableName != null) {
						responseMap = iterateSearchableNameFromEndToStart(searchableName);
						if (responseMap != null) {
							break;
						}
					}
				}
				if (responseMap != null) {
					final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date releaseDate = null;
					final String releaseDateString = responseMap.get(IMediaDetailsUpdater.releaseDate) == null ? null : (String) responseMap
							.get(IMediaDetailsUpdater.releaseDate);
					try {
						if (releaseDateString != null) {
							releaseDate = format.parse(releaseDateString);
						}
					} catch (final ParseException pe) {
						releaseDate = null;
					}
					entryBOList.add(EntryBOBuilder.buildBoFromList(
							MoviesBOBuilder.buildMoviesBO(mediaBO, responseMap.get(IMediaDetailsUpdater.cast) == null ? null
									: (List<String>) responseMap.get(IMediaDetailsUpdater.cast), releaseDate),
									responseMap.get(IMediaDetailsUpdater.rating) == null ? null : new Double((String) responseMap
											.get(IMediaDetailsUpdater.rating)),
											responseMap.get(IMediaDetailsUpdater.referenceUrl) == null ? null : (String) responseMap
													.get(IMediaDetailsUpdater.referenceUrl),
													responseMap.get(IMediaDetailsUpdater.imageUrl) == null ? null : (String) responseMap.get(IMediaDetailsUpdater.imageUrl),
															responseMap.get(IMediaDetailsUpdater.referenceName) == null ? null : (String) responseMap
																	.get(IMediaDetailsUpdater.referenceName), mediaHash));
				}
			}
		}
		return entryBOList;
	}

	private Map<String, Object> iterateSearchableNameFromEndToStart(final String searchableName) {
		Map<String, Object> responseMap = null;
		final String[] brokenSearchableName = searchableName.split(" ");
		for (int i = brokenSearchableName.length; i > 0; i--) {
			final StringBuilder sb = new StringBuilder();
			for (int j = 0; j < i; j++) {
				sb.append(brokenSearchableName[j]).append(" ");
			}
			responseMap = mediaDetailsUpdater.updateMedia(sb.toString().trim());
			if (responseMap != null) {
				break;
			}
		}
		return responseMap;
	}

	@Override
	public String getSHA1ofMediaBO(final MediaBO mediaBO) throws IOException {
		String key = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			final ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(mediaBO);
			oos.close();
			key = DigestUtils.sha1Hex(baos.toByteArray());
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			baos.close();
		}
		return key;
	}
}
