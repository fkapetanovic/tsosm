package portal.test.unit.domain;

import org.testng.Assert;
import org.testng.annotations.Test;

import portal.domain.impl.WebItem;

public class WebItemTest {
	@Test
	public void webItemTest() {
		WebItem webItem = new WebItem();

		Assert.assertEquals(webItem.getTags().size(), 0);
		Assert.assertEquals(webItem.getId(), 0L);
	}
}
