package portal.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import portal.domain.impl.Tag;
import portal.repository.TagDAO;
import portal.service.TagService;
import portal.util.Helper;

@Service
@Transactional(rollbackFor = Exception.class)
public class TagServiceImpl implements TagService {
	@Autowired
	private TagDAO tagDao;

	@Override
	public Tag getTagById(long tagId) {

		return tagDao.getEntityById(Tag.class, tagId);
	}

	@Override
	public Collection<Tag> getTags(String tagIds, String delimiter) {
		Collection<Tag> tags = new ArrayList<Tag>();

		Collection<Long> tagIdSet = Helper
				.convertDelimitedStringToLongHashSet(tagIds, delimiter);

		for (Iterator<Long> it = tagIdSet.iterator(); it.hasNext();) {
			Long id = it.next();
			Tag tag = this.getTagById(id);
			if (tag != null && !tag.isDeleted()) {
				tags.add(tag);
			}
		}
		return tags;
	}

	@Override
	public Collection<Tag> getAllTags() {
		return tagDao.getAllTags();
	}

	@Override
	public Collection<Tag> getPrimaryTags() {
		return tagDao.getPrimaryTags();
	}

	@Override
	public Collection<Tag> getBelongingTags(long tagId) {
		return tagDao.getBelongingTags(tagId);
	}

	@Override
	@Secured({"ROLE_SUPER_USER"})
	public Tag insertTag(Tag tag) {
		return tagDao.insertEntity(tag);
	}

	@Override
	@Secured({"ROLE_SUPER_USER"})
	public Tag updateTag(Tag tag) {
		return tagDao.updateEntity(tag);
	}

	@Override
	@Secured({"ROLE_SUPER_USER"})
	public void deleteTag(Tag tag) {
		tagDao.deleteEntity(Tag.class, tag);
	}
}
