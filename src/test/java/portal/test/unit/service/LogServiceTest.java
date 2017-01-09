package portal.test.unit.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.config.SysEnums.Severity;
import portal.domain.impl.Log;
import portal.domain.impl.User;
import portal.repository.LogDAO;
import portal.security.context.SecurityContextFacade;
import portal.service.impl.LogServiceImpl;
import portal.web.context.RequestContextFacade;

public class LogServiceTest {
	@Mock
	private LogDAO logDao;

	@Mock
	private SecurityContextFacade securityContext;

	@Mock
	private RequestContextFacade requestContext;

	@Mock
	private MessageSource messageSource;

	@Autowired
	@InjectMocks
	private LogServiceImpl logService;

	@BeforeMethod
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void insertLogTest(){
		Log log = new Log();
		logService.insertLog(log);

		verify(logDao).insertEntity(log);
	}

	@Test
	public void insertWebLogTest(){
		String messageCode = "mesage code";
		String message = "message";
		Severity severity = Severity.DEBUG;
		String ipAddress = "127.0.0.1";
		User user = new User();

		ArgumentCaptor<Log> argument = ArgumentCaptor.forClass(Log.class);

		when(securityContext.getLoggedInUser()).thenReturn(user);
		when(requestContext.ipAddress()).thenReturn(ipAddress);
		when(messageSource.getMessage(messageCode, null, null)).thenReturn(message);

		logService.insertWebLog(messageCode, severity);

		verify(securityContext).getLoggedInUser();
		verify(requestContext).ipAddress();
		verify(messageSource).getMessage(messageCode, null, null);
		verify(logDao).insertEntity(argument.capture());

		Assert.assertEquals(argument.getValue().getIpAddress(), ipAddress);
		Assert.assertEquals(argument.getValue().getCreatedBy(), user);
		Assert.assertEquals(argument.getValue().getText(), message);
		Assert.assertEquals(argument.getValue().getSeverity(), severity.getValue());
	}

	@Test
	public void getLogsTest(){
		logService.getLogs(10);

		verify(logDao).getLogs(10);
	}
}
