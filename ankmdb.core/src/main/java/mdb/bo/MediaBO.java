package mdb.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MediaBO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected String name;
	protected Long size;
	protected Date lastUpdated;
	protected List<String> searchableNames;
	protected String location;
	protected String format;
	protected String sourceMachine;

	public MediaBO(final String name, final Long size, final Date lastUpdated, final List<String> searchableNames, final String location,
			final String format, final String sourceMachine) {
		super();
		this.name = name;
		this.size = size;
		this.lastUpdated = lastUpdated;
		this.searchableNames = searchableNames;
		this.location = location;
		this.format = format;
		this.sourceMachine = sourceMachine;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the size
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(final Long size) {
		this.size = size;
	}

	/**
	 * @return the lastUpdated
	 */
	public Date getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * @param lastUpdated
	 *            the lastUpdated to set
	 */
	public void setLastUpdated(final Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	/**
	 * @return the searchableNames
	 */
	public List<String> getSearchableNames() {
		return searchableNames;
	}

	/**
	 * @param searchableNames
	 *            the searchableNames to set
	 */
	public void setSearchableNames(final List<String> searchableNames) {
		this.searchableNames = searchableNames;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(final String location) {
		this.location = location;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format
	 *            the format to set
	 */
	public void setFormat(final String format) {
		this.format = format;
	}

	public String getSourceMachine() {
		return sourceMachine;
	}

	public void setSourceMachine(final String sourceMachine) {
		this.sourceMachine = sourceMachine;
	}

	@Override
	public String toString() {
		return "MediaBO [" + (name != null ? "name=" + name + ", " : "") + (size != null ? "size=" + size + ", " : "")
				+ (lastUpdated != null ? "lastUpdated=" + lastUpdated + ", " : "")
				+ (searchableNames != null ? "searchableNames=" + searchableNames + ", " : "")
				+ (location != null ? "location=" + location + ", " : "") + (format != null ? "format=" + format + ", " : "")
				+ (sourceMachine != null ? "sourceMachine=" + sourceMachine : "") + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (format == null ? 0 : format.hashCode());
		result = prime * result + (lastUpdated == null ? 0 : lastUpdated.hashCode());
		result = prime * result + (location == null ? 0 : location.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (searchableNames == null ? 0 : searchableNames.hashCode());
		result = prime * result + (size == null ? 0 : size.hashCode());
		result = prime * result + (sourceMachine == null ? 0 : sourceMachine.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MediaBO other = (MediaBO) obj;
		if (format == null) {
			if (other.format != null) {
				return false;
			}
		} else if (!format.equals(other.format)) {
			return false;
		}
		if (lastUpdated == null) {
			if (other.lastUpdated != null) {
				return false;
			}
		} else if (!lastUpdated.equals(other.lastUpdated)) {
			return false;
		}
		if (location == null) {
			if (other.location != null) {
				return false;
			}
		} else if (!location.equals(other.location)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (searchableNames == null) {
			if (other.searchableNames != null) {
				return false;
			}
		} else if (!searchableNames.equals(other.searchableNames)) {
			return false;
		}
		if (size == null) {
			if (other.size != null) {
				return false;
			}
		} else if (!size.equals(other.size)) {
			return false;
		}
		if (sourceMachine == null) {
			if (other.sourceMachine != null) {
				return false;
			}
		} else if (!sourceMachine.equals(other.sourceMachine)) {
			return false;
		}
		return true;
	}

}
