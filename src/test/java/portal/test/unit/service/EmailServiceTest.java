package portal.test.unit.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Properties;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.config.AppPropKeys;
import portal.config.MessageKeys;
import portal.domain.impl.User;
import portal.service.impl.EmailServiceImpl;
import portal.util.Helper;

public class EmailServiceTest {
	@Mock
	private MailSender mailSender;

	@Mock
	private MessageSource messageSource;

	@Autowired
	@InjectMocks
	private EmailServiceImpl eMailService;

	private final Properties APP_PROPERTIES = Helper.getAppProperties();
	private final String FROM = APP_PROPERTIES.getProperty(AppPropKeys.MAIL_FROM);

	private final String LINK_PREFIX_ACTIVATE_ACCOUNT = APP_PROPERTIES.getProperty
			(AppPropKeys.MAIL_LINK_PREFIX_ACTIVATE_ACCOUNT);

	private final String LINK_PREFIX_CHANGE_PASSWORD = APP_PROPERTIES.getProperty
			(AppPropKeys.MAIL_LINK_PREFIX_CHANGE_PASSWORD);

	@BeforeMethod
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void sendActivationEmailTest(){
		String subject = "Activate your account";
		String bodyIntro = "To activate your account click the following link:";
		String bodyGreeting = "Greetings.";
		String from = FROM;
		String linkPrefix = LINK_PREFIX_ACTIVATE_ACCOUNT;
		User user = new User();

		user.setActivationCode("5a1d1d2bcac94e06bb5b31a952583129");
		user.setUsername("username");
		user.seteMailAddress("osamdeset@gmx.net");

		String body = bodyIntro + linkPrefix + user.getUsername() + "/" + user.getActivationCode() + bodyGreeting;
		ArgumentCaptor<SimpleMailMessage> argument = ArgumentCaptor.forClass(SimpleMailMessage.class);

		when(messageSource.getMessage(MessageKeys.MAIL_SUBJECT_ACTIVATE_ACCOUNT,
				null, null)).thenReturn(subject);
		when(messageSource.getMessage(MessageKeys.MAIL_BODY_ACTIVATE_ACCOUNT,
				null, null)).thenReturn(bodyIntro);
		when(messageSource.getMessage(MessageKeys.MAIL_BODY_END_GREETING_ACC_CREATED,
				null, null)).thenReturn(bodyGreeting);

		eMailService.sendActivationEmail(user);

		verify(messageSource).getMessage(MessageKeys.MAIL_SUBJECT_ACTIVATE_ACCOUNT, null, null);
		verify(messageSource).getMessage(MessageKeys.MAIL_BODY_ACTIVATE_ACCOUNT,null, null);
		verify(mailSender).send(argument.capture());

		Assert.assertEquals(argument.getValue().getSubject(), subject);
		Assert.assertEquals(argument.getValue().getText(), body);
		Assert.assertEquals(argument.getValue().getFrom(), from);
		Assert.assertEquals(argument.getValue().getTo()[0], user.geteMailAddress());
	}

	@Test
	public void sendChangePasswordEmailTest(){
		String subject = "Change password";
		String bodyIntro = "To change password click the following link: ";
		String from = FROM;
		String bodyGreeting = "Greetings.";
		String linkPrefix = LINK_PREFIX_CHANGE_PASSWORD;
		User user = new User();

		user.setPasswordChangeCode("5a1d1d2bcac94e06bb5b31a952583129");
		user.setUsername("username");
		user.seteMailAddress("osamdeset@gmx.net");

		String body = bodyIntro + linkPrefix + user.getUsername() + "/" + user.getPasswordChangeCode() + bodyGreeting;
		ArgumentCaptor<SimpleMailMessage> argument = ArgumentCaptor.forClass(SimpleMailMessage.class);

		when(messageSource.getMessage(MessageKeys.MAIL_SUBJECT_CHANGE_PASSWORD,
				null, null)).thenReturn(subject);
		when(messageSource.getMessage(MessageKeys.MAIL_BODY_CHANGE_PASSWORD,
				null, null)).thenReturn(bodyIntro);
		when(messageSource.getMessage(MessageKeys.MAIL_BODY_END_GREETING_DEFAULT,
				null, null)).thenReturn(bodyGreeting);

		eMailService.sendChangePasswordEmail(user);

		verify(messageSource).getMessage(MessageKeys.MAIL_SUBJECT_CHANGE_PASSWORD,
				null, null);
		verify(messageSource).getMessage(MessageKeys.MAIL_BODY_CHANGE_PASSWORD,
				null, null);
		verify(mailSender).send(argument.capture());
		
		Assert.assertEquals(argument.getValue().getSubject(), subject);
		Assert.assertEquals(argument.getValue().getText(), body);
		Assert.assertEquals(argument.getValue().getFrom(), from);
		Assert.assertEquals(argument.getValue().getTo()[0], user.geteMailAddress());
	}
}
