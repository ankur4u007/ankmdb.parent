package mdb.bo;

import java.io.Serializable;

public class EntryBO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected MediaBO mediaBo;
	protected String referenceName;
	protected Double rating;
	protected String referenceUrl;
	protected String imageUrl;
	protected String mediaBoHash;

	/**
	 * @return the mediaBo
	 */
	public MediaBO getMediaBo() {
		return mediaBo;
	}

	/**
	 * @param mediaBo
	 *            the mediaBo to set
	 */
	public void setMediaBo(final MediaBO mediaBo) {
		this.mediaBo = mediaBo;
	}

	/**
	 * @return the rating
	 */
	public Double getRating() {
		return rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(final Double rating) {
		this.rating = rating;
	}

	/**
	 * @return the referenceUrl
	 */
	public String getReferenceUrl() {
		return referenceUrl;
	}

	/**
	 * @param referenceUrl
	 *            the referenceUrl to set
	 */
	public void setReferenceUrl(final String referenceUrl) {
		this.referenceUrl = referenceUrl;
	}

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl
	 *            the imageUrl to set
	 */
	public void setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(final String referenceName) {
		this.referenceName = referenceName;
	}

	public String getMediaBoHash() {
		return mediaBoHash;
	}

	public void setMediaBoHash(final String mediaBoHash) {
		this.mediaBoHash = mediaBoHash;
	}

	@Override
	public String toString() {
		return "EntryBO [" + (mediaBo != null ? "mediaBo=" + mediaBo + ", " : "")
				+ (referenceName != null ? "referenceName=" + referenceName + ", " : "") + (rating != null ? "rating=" + rating + ", " : "")
				+ (referenceUrl != null ? "referenceUrl=" + referenceUrl + ", " : "") + (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "")
				+ (mediaBoHash != null ? "mediaBoHash=" + mediaBoHash : "") + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (mediaBoHash == null ? 0 : mediaBoHash.hashCode());
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
		final EntryBO other = (EntryBO) obj;
		if (mediaBoHash == null) {
			if (other.mediaBoHash != null) {
				return false;
			}
		} else if (!mediaBoHash.equals(other.mediaBoHash)) {
			return false;
		}
		return true;
	}

}
