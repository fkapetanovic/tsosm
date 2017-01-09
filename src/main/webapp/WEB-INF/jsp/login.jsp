<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags"%>

<z:genericPage pageTitle="Log In" 
			   customCss="/resources/css/form.css">

	<h1>Log in to your account</h1>
	
	<div class="form panel">
		<form action="<c:url value='/processLogin'/>" method="post">
			<label class="formLabel" for="username">Username:</label> 
			<input class="textBox" name="username" maxlength="20" type="text" /> <br /> 
			<label class="formLabel" for="password">Password:</label>
			<input class="textBox" name="password" type="password" /> <br />
			<div class="rememberMe">
				<input id="rememberMe" name="rememberMe" type="checkbox" value="true" />
				<label for="rememberMe">Remember Me</label>  
			</div>			
			<div class="buttonDiv">
				<input class="button loginButton" type="submit" value="Log In" /><br />
				<a href="<c:url value='/user/resetPassword'/>">Forgot password?</a>
			</div>
		
			<div class="error loginError">
				<c:if test="${not empty param['error']}">
					<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
				</c:if>
			</div>
		</form>
	</div>

</z:genericPage>

