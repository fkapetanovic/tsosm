<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags"%>

<z:genericPage pageTitle="Reset Password" 
			   customCss="/resources/css/form.css">

	<h1>Reset password</h1>
	
	<div class="form panel">
		<form:form method="post" commandName="user">
			<label class="formLabel resetPassLabel" for="eMail">Enter your e-mail address:</label> 
			<input class="textBox resetPasswordTextbox" id="eMail" name="eMail" type="text" maxlength="100"/>	
			<div class="error resetPassError">
				<c:out value = "${message}" /> <br />
			</div> 		  
			<div class="buttonDiv">
				<input class="button resetPasswordButton" type="submit" value="Reset Password">
			</div>
		</form:form>
		
	</div>
	
</z:genericPage>
