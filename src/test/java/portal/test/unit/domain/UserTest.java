package portal.test.unit.domain;

import org.testng.Assert;
import org.testng.annotations.Test;

import portal.domain.impl.User;

public class UserTest {
	@Test
	public void userTest() {
		User user = new User();

		Assert.assertEquals(user.getAuthorities().size(), 0);
		Assert.assertEquals(user.getId(), 0L);
	}
}
