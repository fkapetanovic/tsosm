package portal.repository;

import java.util.Collection;

import portal.domain.impl.Tag;

/**
 * Data Access Object interface providing persistence operations.
 *
 */
public interface TagDAO extends DAO {

	/**
	 * Retrieves all tags marked as "Primary".
	 *
	 * @return a collection of tags or an empty collection if no such entities.
	 */
	public Collection<Tag> getPrimaryTags();

	/**
	 * Retrieves tags that belong to the tag with the given ID
	 *
	 * @param tagId
	 *            tagId.
	 * @return a collection of tags or an empty collection if no such entities.
	 */
	public Collection<Tag> getBelongingTags(long tagId);

	/**
	 * Retrieves all tags
	 *
	 * @return a collection of all tags present in the persistent store.
	 */
	public Collection<Tag> getAllTags();
}
