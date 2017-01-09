<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags"%>

<z:genericPage pageTitle="${actionTitle} Web Item"
	customCss="/resources/css/form.css"
	jquery="/resources/javascript/jquery-1.7.2.js"
	jqueryUi="/resources/javascript/jquery-ui.js"
	customJavaScript="/resources/javascript/webItemView.js">

	<h1 class="titleLeft">${actionTitle} Web Item</h1>
	<form:form class="insertWebItemForm" method="post" modelAttribute="webItem" title="${actionTitle} Web Item">
		<form:label class="formLabel" path="title">Title</form:label>
		<form:input class="textBox wideTextBox" path="title" />
		<form:errors class="error" path="title" />
		<br />
		<form:label class="formLabel" path="sourceURL">Url</form:label>
		<form:input class="textBox wideTextBox" path="sourceURL" />
		<form:errors class="error" path="sourceURL" />
		<br />
		<form:label class="formLabel" path="sourceName"> Source Name</form:label>
		<form:input class="textBox wideTextBox" path="sourceName" />
		<form:errors class="error" path="sourceName" />
		<br />
		<form:label class="formLabel" path="image.sourceURL"> Image Path</form:label>
		<form:input class="textBox wideTextBox" path="image.sourceURL" />
		<form:errors class="error" path="image.sourceURL" />
		<br />
		<br />
		<c:if test="${!empty imageUrls}">
			Select image to add:<br />
			<c:forEach items="${imageUrls}" var="imageUrl">
				<img class="imageToSelect" src="${imageUrl}" >
			</c:forEach>
			<br />
		</c:if>
		<br /><br />
		<form:label class="formLabel textFieldLabel" path="text">Text</form:label>
		<form:errors class="error" path="text" />
		<form:textarea class="textField" path="text" rows="3" cols="20" />
		<br />
		<br />
		<form:radiobuttons cssClass="radioButton" path="webItemType" items="${allWebItemTypes}"
			itemLabel="name" itemValue="id" />
		<br />
		<form:errors class="error" path="webItemType" />
		<br />
		<form:checkboxes path="featured" items="${featured}" />
		<br />
		<form:errors class="error" path="tags" />
		<br />
		<fieldset>
			<legend>Primary Tags</legend>
			<form:checkboxes class="tags" path="tags" items="${primaryTags}" itemLabel="name"
				itemValue="idText" />
		</fieldset>
		<br />
		<fieldset>
			<legend>Secondary Tags</legend>
			<form:checkboxes class="tags" path="tags" items="${secondaryTags}"
				itemLabel="name" itemValue="idText" />
		</fieldset>
		<br/>
		<input class="button submitWebItemButton" type="submit" value="Save WebItem">
		<form:hidden path="id" />
		<br />
		<br/>
	</form:form>
</z:genericPage>
