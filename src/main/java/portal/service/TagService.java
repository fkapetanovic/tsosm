package portal.service;

import java.util.Collection;

import portal.domain.impl.Tag;

/**
 * "Service" interface for managing tags.
 *
 */
public interface TagService {

	/**
	 * Retrieves a tag by its ID.
	 *
	 * @param tagId
	 * @return the tag with the given ID or throws an exception if no such entity.
	 */
	public Tag getTagById(long tagId);

	/**
	 * Retrieves tags by the list of tag IDs.
	 *
	 * @param tagIds
	 *            the list of tag IDs delimited by a delimiter character.
	 * @param delimiter
	 *            delimiter.
	 * @return a collection of tags or empty collection if no such entities.
	 */
	public Collection<Tag> getTags(String tagIds, String delimiter);

	/**
	 * Retrieves all tags
	 *
	 * @return a collection of all tags present in the persistent store.
	 */
	public Collection<Tag> getAllTags();

	/**
	 * Retrieves all tags marked as "Primary".
	 *
	 * @return a collection of primary tags or an empty collection if no such entities.
	 */
	public Collection<Tag> getPrimaryTags();

	/**
	 * Retrieves tags that belong to the tag with the given ID
	 *
	 * @param tagId
	 * @return a collection of tags or empty collection if no such entities.
	 */
	public Collection<Tag> getBelongingTags(long tagId);

	/**
	 * Inserts a new tag into the persistent store.
	 *
	 * @param tag
	 * @return the inserted tag.
	 */
	public Tag insertTag(Tag tag);

	/**
	 * Saves a modified tag into the persistent store.
	 *
	 * @param tag
	 * @return the updated tag.
	 */
	public Tag updateTag(Tag tag);

	/**
	 * Deletes a tag from the persistent store.
	 *
	 * @param tag
	 */
	public void deleteTag(Tag tag);
}
