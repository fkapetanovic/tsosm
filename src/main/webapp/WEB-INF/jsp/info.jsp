<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags"%>
<%@taglib  uri="http://www.springframework.org/tags" prefix="spring"%>

<z:genericPage pageTitle="Info" 
			   customCss="/resources/css/form.css">

	<div class="infoMessage panel" >
		<spring:message text="${message}" htmlEscape="false" />
	</div>
	
</z:genericPage>
