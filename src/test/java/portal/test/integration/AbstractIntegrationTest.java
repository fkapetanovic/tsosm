package portal.test.integration;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

@ContextConfiguration(locations = { "classpath:test-application-context.xml" })
public class AbstractIntegrationTest extends
		AbstractTransactionalTestNGSpringContextTests {
}
