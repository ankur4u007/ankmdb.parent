package mdb.bo;

import java.util.Date;
import java.util.List;

public class MovieBO extends MediaBO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private List<String> casts;
	private Date releaseDate;

	public MovieBO(final String name, final Long size, final Date lastUpdated, final List<String> searchableNames, final String location,
			final String format, final List<String> casts, final Date releaseDate, final String sourceMachineName) {
		super(name, size, lastUpdated, searchableNames, location, format, sourceMachineName);
		this.casts = casts;
		this.releaseDate = releaseDate;
	}

	public List<String> getCasts() {
		return casts;
	}

	public void setCasts(final List<String> casts) {
		this.casts = casts;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(final Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
	public String toString() {
		return super.toString() + "MovieBO [" + (casts != null ? "casts=" + casts + ", " : "")
				+ (releaseDate != null ? "releaseDate=" + releaseDate : "") + "]";
	}

}
