package portal.repository.jdo;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Repository;

import portal.domain.impl.Tag;
import portal.repository.TagDAO;

@Repository
public class JdoTagDAO extends JdoDAO implements TagDAO {
	@Override
	public Collection<Tag> getPrimaryTags() {
		Collection<Tag> tags = new ArrayList<Tag>();

		String filter = "isPrimary == true && deleted == false";
		String ordering = "position ASC";

		tags = getEntities(Tag.class, filter, ordering);

		return tags;
	}

	@Override
	public Collection<Tag> getBelongingTags(long tagId) {
		Collection<Tag> tags = new ArrayList<Tag>();

		String filter = "parentTag == tag && deleted == false";
		String parameters = "portal.domain.impl.Tag tag";
		String ordering = "position ASC";

		Tag tag = getEntityById(Tag.class, tagId);

		Object[] values = new Object[1];
		values[0] = tag;

		tags = getEntities(Tag.class, filter, parameters, ordering, values);

		return tags;
	}

	@Override
	public Collection<Tag> getAllTags() {
		Collection<Tag> tags = new ArrayList<Tag>();

		tags = getEntities(Tag.class, "deleted == false", "name ASC");

		return tags;
	}
}
