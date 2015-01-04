package mdb.updater;

import java.util.Map;

public interface IMediaDetailsUpdater {

	public final String cast = "CASTS";
	public final String rating = "RATING";
	public final String releaseDate = "RELEASEDATE";
	public final String imageUrl = "IMAGEURL";
	public final String referenceUrl = "REFERENCEURL";
	public final String referenceName = "REFERENCENAME";

	Map<String, Object> updateMedia(String mediaName);
}
