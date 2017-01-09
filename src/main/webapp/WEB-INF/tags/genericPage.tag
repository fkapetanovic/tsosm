<%@ tag body-content="scriptless" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="pageTitle" required="true" type="java.lang.String" %>
<%@attribute name="customCss" required="false" type="java.lang.String"%>
<%@attribute name="jquery" required="false" type="java.lang.String"%>
<%@attribute name="jqueryUi" required="false" type="java.lang.String"%>
<%@attribute name="customJavaScript" required="false" type="java.lang.String"%>

<!DOCTYPE html>
<html>
	<head>
	    <title>${pageTitle}</title>
	    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>"/>
	    <link rel="stylesheet" type="text/css" href="<c:url value="${customCss}"/>"/>		
	</head>	
	
	<body>		
		<div id="wrap">
			<div id="pageHeader">
		      	<jsp:include page="../jsp/header.jsp" />
			</div>		
			<div id="main">				
			    <jsp:doBody/>
			</div>    
		</div>
		<div id="pageFooter">
	      	<jsp:include page="../jsp/footer.jsp" />
 		</div>  
	</body>
		<c:if test= "${!empty jqueryUi}">
		   <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jquery-ui.css"/>"/>
		</c:if>
		<script type="text/javascript" src="<c:url value="${jquery}"/>"> </script> 
		<script type="text/javascript" src="<c:url value="${customJavaScript}"/>"> </script>
		<script type="text/javascript" src="<c:url value="${jqueryUi}"/>"> </script>
</html>
