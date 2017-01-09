package portal.test.unit.domain;

import org.testng.Assert;
import org.testng.annotations.Test;

import portal.domain.impl.DataRequestLog;

public class DataRequestLogTest {
	@Test
	public void dataRequestLogTest() {
		DataRequestLog dataRequestLog = new DataRequestLog();

		Assert.assertEquals(dataRequestLog.getRequestedTags().size(), 0);
		Assert.assertEquals(dataRequestLog.getRequestedWebItemTypes().size(), 0);
		Assert.assertEquals(dataRequestLog.getId(), 0L);
	}
}
