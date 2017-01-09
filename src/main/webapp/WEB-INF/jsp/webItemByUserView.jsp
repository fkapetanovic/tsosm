<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<z:genericPage pageTitle="All contributions"
  customCss="/resources/css/form.css"
  jquery="/resources/javascript/jquery-1.7.2.js"
	jqueryUi="/resources/javascript/jquery-ui.js"
	customJavaScript="/resources/javascript/webItemByUser.js"
>

<display:table name="webItemList" id="webItem" requestURIcontext="false" requestURI="" pagesize="100" class="displayTable">
 <display:column property="title" paramId="id" >
 </display:column>
   <display:column property="featured" paramId="id" >
  </display:column>
  <display:column property="deleted" paramId="id" >
  </display:column>
  <display:column  title="Action"  >
 <a href="/panel/webItems/${webItem.id}">Edit</a>
 <a href="/panel/webItems/delete/${webItem.id}" class="deleteButton">Delete</a>
 </display:column>
</display:table>
</z:genericPage>
