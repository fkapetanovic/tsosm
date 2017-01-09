package portal.service.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import portal.config.AppPropKeys;
import portal.config.MessageKeys;
import portal.domain.impl.User;
import portal.service.EmailService;
import portal.util.Helper;

@Component
public class EmailServiceImpl implements EmailService{
	@Autowired
	private MailSender mailSender;

	@Autowired
	private MessageSource messageSource;

	private final Properties APP_PROPERTIES = Helper.getAppProperties();
	private final String FROM = APP_PROPERTIES.getProperty(AppPropKeys.MAIL_FROM);

	@Override
	public void sendActivationEmail(User user) {
		final String LINK_PREFIX_ACTIVATE_ACCOUNT = APP_PROPERTIES.getProperty
				(AppPropKeys.MAIL_LINK_PREFIX_ACTIVATE_ACCOUNT);

		SimpleMailMessage message = new SimpleMailMessage();
		String subject = messageSource.getMessage(MessageKeys.MAIL_SUBJECT_ACTIVATE_ACCOUNT, null, null);
		String body = messageSource.getMessage(MessageKeys.MAIL_BODY_ACTIVATE_ACCOUNT,
				null, null) + LINK_PREFIX_ACTIVATE_ACCOUNT +
				user.getUsername() + "/" + user.getActivationCode() +
				messageSource.getMessage(MessageKeys.MAIL_BODY_END_GREETING_ACC_CREATED,
				null, null);

		message.setFrom(FROM);
		message.setTo(user.geteMailAddress());
		message.setSubject(subject);
		message.setText(body);

		mailSender.send(message);
	}

	@Override
	public void sendChangePasswordEmail(User user) {
		final String FROM = APP_PROPERTIES.getProperty(AppPropKeys.MAIL_FROM);
		final String LINK_PREFIX_CHANGE_PASSWORD = APP_PROPERTIES.getProperty
				(AppPropKeys.MAIL_LINK_PREFIX_CHANGE_PASSWORD);

		SimpleMailMessage message = new SimpleMailMessage();

		String subject = messageSource.getMessage(MessageKeys.MAIL_SUBJECT_CHANGE_PASSWORD, null, null);
		String body = messageSource.getMessage(MessageKeys.MAIL_BODY_CHANGE_PASSWORD,
				null, null) + LINK_PREFIX_CHANGE_PASSWORD +
				user.getUsername() + "/" + user.getPasswordChangeCode()+
				messageSource.getMessage(MessageKeys.MAIL_BODY_END_GREETING_DEFAULT,
				null, null);

		message.setFrom(FROM);
		message.setTo(user.geteMailAddress());
		message.setSubject(subject);
		message.setText(body);

		mailSender.send(message);
	}
}
