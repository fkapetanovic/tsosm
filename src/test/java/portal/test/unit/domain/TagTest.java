package portal.test.unit.domain;

import org.testng.Assert;
import org.testng.annotations.Test;

import portal.domain.impl.Tag;

public class TagTest {
	@Test
	public void tagTest() {
		Tag tag = new Tag();

		Assert.assertEquals(tag.getId(), 0L);
	}
}
