package portal.test.integration.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.domain.impl.Log;
import portal.domain.impl.User;
import portal.service.LogService;
import portal.service.UserService;
import portal.test.integration.AbstractIntegrationTest;
import portal.test.integration.TestEntities;

public class LogServiceIntegrationTest extends AbstractIntegrationTest {
	@Autowired
	private LogService logService;

	@Autowired
	private UserService userService;
	private User user;

	@BeforeMethod
	public void setUp() {
		user = userService.loadUserByUsername(TestEntities.TEST_USER_1);
	}

	@Test
	public void insertLogTest() {
		Log log = TestEntities.createLog1();
		log.setCreatedBy(user);
		Log insertedLog = logService.insertLog(log);

		Assert.assertEquals(insertedLog.getAdditionalInfo(), log.getAdditionalInfo());
		Assert.assertEquals(insertedLog.getIpAddress(), log.getIpAddress());
		Assert.assertEquals(insertedLog.getOperation(), log.getOperation());
		Assert.assertEquals(insertedLog.getText(), log.getText());
		Assert.assertEquals(insertedLog.getCreatedBy(), log.getCreatedBy());
	}

	@Test
	public void getLogsTest() {
		Log log1 = TestEntities.createLog1();
		Log log2 = TestEntities.createLog2();

		log1.setCreatedBy(user);
		log2.setCreatedBy(user);

		logService.insertLog(log1);
		logService.insertLog(log2);

		Collection<Log> logs = logService.getLogs(5);

		Assert.assertEquals(logs.size(), 2);
	}
}
