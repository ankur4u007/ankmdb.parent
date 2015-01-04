package mdb.bo;

import java.util.Date;
import java.util.List;

public class MoviesBOBuilder {

	private MoviesBOBuilder() {
		// no init
	}

	public static MovieBO buildMoviesBO(final MediaBO mediaBO, final List<String> casts, final Date releaseDate) {
		return new MovieBO(mediaBO.getName(), mediaBO.getSize(), mediaBO.getLastUpdated(), mediaBO.getSearchableNames(), mediaBO.getLocation(),
				mediaBO.getFormat(), casts, releaseDate, mediaBO.getSourceMachine());
	}
}
