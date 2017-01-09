package portal.test.unit.service;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.domain.impl.DataRequestLog;
import portal.domain.impl.Tag;
import portal.domain.impl.User;
import portal.domain.impl.WebItemType;
import portal.repository.DataRequestLogDAO;
import portal.security.context.SecurityContextFacade;
import portal.service.impl.DataRequestLogServiceImpl;
import portal.web.context.RequestContextFacade;

public class DataRequestLogServiceTest {
	@Mock
	private DataRequestLogDAO drLogDao;

	@Mock
	private SecurityContextFacade securityContext;

	@Mock
	RequestContextFacade requestContext;

	@Autowired
	@InjectMocks
	private DataRequestLogServiceImpl drLogService;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void basicInsertLogTest() {
		DataRequestLog drLog = new DataRequestLog();
		drLogService.insertDataRequestLog(drLog);

		verify(drLogDao).insertEntity(drLog);
	}

	@Test
	public void insertLogTest(){
		Collection<Tag> tags = new ArrayList<Tag>();
		Collection<WebItemType> wit =  new ArrayList<WebItemType>();
		String ipAddress = "127.0.0.1";
		User user = new User();

		ArgumentCaptor<DataRequestLog> argument = ArgumentCaptor.forClass(DataRequestLog.class);

		when(securityContext.getLoggedInUser()).thenReturn(user);
		when(requestContext.ipAddress()).thenReturn(ipAddress);

		drLogService.insertDataRequestLog(tags, wit);

		verify(securityContext).getLoggedInUser();
		verify(requestContext).ipAddress();
		verify(drLogDao).insertEntity(argument.capture());

		Assert.assertEquals(ipAddress, argument.getValue().getIpAddress());
		Assert.assertEquals(user, argument.getValue().getCreatedBy());
		Assert.assertEquals(tags, argument.getValue().getRequestedTags());
		Assert.assertEquals(wit, argument.getValue().getRequestedWebItemTypes());
	}

	@Test
	public void getDataRequestLogsTest() {
		drLogService.getDataRequestLogs(10);
		verify(drLogDao).getDataRequestLogs(10);
	}

}
