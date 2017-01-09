<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags"%>

<z:genericPage pageTitle="Create Account" 
			   customCss="/resources/css/form.css">
			   
	<h1>Join using e-mail</h1>
	
	<div class="form panel">
		<form:form  method="post" commandName="user">
			<form:label class="formLabel" path="username">User name:</form:label>
			<form:input class="textBox" path="username" maxlength="20"/><br />
			<div class="error"><form:errors path="username"/></div> 
			<form:label class="formLabel" path="password">Password:</form:label>
			<form:password class="textBox" path="password"/>
			<div class="error"><form:errors path="password"/></div>
			<form:label class="formLabel" path="retypePassword">Retype password:</form:label>
			<form:password class="textBox" path="retypePassword"/><br />
			<div class="error"><form:errors path="retypePassword"/></div>
			<form:label class="formLabel" path="eMailAddress">E-mail address:</form:label>
			<form:input class="textBox" path="eMailAddress" maxlength="100"/><br />
			<div class="error"><form:errors path="eMailAddress"/></div>
			<div class="buttonDiv"><input class="button submitButton" type="submit" value="Join"></div>
		</form:form>
	</div>
	
</z:genericPage>
