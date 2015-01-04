/**
 *
 */
package mdb.bo;

/**
 * @author CHANDRAYAN
 *
 */
public class EntryBOBuilder {

	private EntryBOBuilder() {
		// avoid initialisation
	}

	public static EntryBO buildBoFromList(final MediaBO mediaBO, final Double rating, final String referenceUrl, final String imageUrl,
			final String referenceName, final String mediaHash) {
		final EntryBO bo = new EntryBO();
		bo.setMediaBo(mediaBO);
		bo.setRating(rating);
		bo.setReferenceUrl(referenceUrl);
		bo.setImageUrl(imageUrl);
		bo.setReferenceName(referenceName);
		bo.setMediaBoHash(mediaHash);
		return bo;
	}
}
