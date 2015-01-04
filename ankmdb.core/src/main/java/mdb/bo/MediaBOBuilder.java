package mdb.bo;

import java.util.Date;
import java.util.List;

public class MediaBOBuilder {

	private MediaBOBuilder() {
		// Avoid init
	}

	public static final MediaBO buildBoFromDetails(final String name, final Date lastUpdated, final Long size, final String location,
			final List<String> searchableNames, final String format, final String sourceMachineName) {
		return new MediaBO(name, size, lastUpdated, searchableNames, format, format, sourceMachineName);
	}
}
