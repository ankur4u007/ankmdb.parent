/**
 *
 */
package mdb.services;

import java.util.List;

/**
 * @author CHANDRAYAN
 *
 */
public interface IIndexHandlerService {

	List<String> findSha1ListBySource(String source);

	void addIndexesForSource(List<String> sha1IdList, String source);

	void deleteIndexesForSource(final List<String> sha1IdList, final String source);
}
