package portal.security;
import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import portal.domain.impl.WebItem;
import portal.service.WebItemService;
@Component
public class WebItemPermissionEvaluator implements PermissionEvaluator{
	@Autowired
	WebItemService webItemService;

	@Override
	public boolean hasPermission(Authentication authentication, Object target, Object permission) {
		if (target instanceof WebItem)
		{
			WebItem webItem=(WebItem) target;
			calculatePermission(authentication, permission, webItem);
		}
		return false;
	}

	private boolean calculatePermission(Authentication authentication,
			Object permission, WebItem webItem) {
		if ("delete".equals(permission) || "edit".equals(permission))
		{
			return webItem.getCreatedBy().getUsername().equals(authentication.getName()) ;
		}
		return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable id,
			String targetType, Object permission) {
		WebItem webItem=webItemService.getWebItemById((Long) id);
		return calculatePermission(authentication,permission,webItem);

	}
}
