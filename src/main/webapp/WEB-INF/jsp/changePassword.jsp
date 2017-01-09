<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags"%>

<z:genericPage pageTitle="New Password" 
			   customCss="/resources/css/form.css">

	<h1>Enter new password</h1>
	
	<div class="form panel">
		<form:form  method="post" action="/user/changePassword/${userName}/${passwordChangeCode}">
			<label class="formLabel resetPassLabel" for="password">New password:</label> 
			<input class="textBox newPasswordTextBox" id="password" name="password" type="password" />
			<div class="error errorNewPassword"><c:out value = "${error1}" />   <br /> </div>
			<label class="formLabel resetPassLabel" for="retypePassword">Retype new password:</label> 
			<input class="textBox newPasswordTextBox" id="retypePassword" name="retypePassword" type="password" />
			<div class="error errorNewPassword"><c:out value = "${error2}" />  <br /> </div>   
			<div class="buttonDiv">
				<input class="button resetPasswordButton" type="submit" value="Submit Password">
			</div>
		</form:form>
	</div>
	
</z:genericPage>




