<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags"%>

<z:genericPage pageTitle="Tsosm - The Bright Side of the Web" 
			   customCss="/resources/css/home.css" 
			   jquery="/resources/javascript/jquery-1.7.2.js" 
			   customJavaScript="/resources/javascript/home.js">
	<meta name="description" content="New insightful, inspiring, cutting edge, funny and 
	relaxing content from around the Web every day." />
	<div class="navigation">

		<div class="wiTypes panel">
			<c:forEach var="webItemType" items="${webItemTypes}">
				<a class="wiType" oid="${webItemType.id}">${webItemType.name}</a>
			</c:forEach>
		</div>

		<div class="selectedTags panel">
			<div id="tagSlot0" class="selectedTag" index="0"></div>
			<div id="tagLink0" class="tagLinks" index="0">
				<img class="tagLink"
					src="<c:url value="/resources/pics/home/link.png" />" />
			</div>
			<div id="tagSlot1" class="selectedTag" index="1"></div>
			<div id="tagLink1" class="tagLinks" index="1">
				<img class="tagLink"
					src="<c:url value="/resources/pics/home/link.png" />" />
			</div>
			<div id="tagSlot2" class="selectedTag" index="2"></div>
			<div id="tagLink2" class="tagLinks" index="2">
				<img class="tagLink"
					src="<c:url value="/resources/pics/home/link.png" />" />
			</div>
			<div id="tagSlot3" class="selectedTag" index="3"></div>
			<div id="tagLink3" class="tagLinks" index="3">
				<img class="tagLink"
					src="<c:url value="/resources/pics/home/link.png" />" />
			</div>
			<div id="tagSlot4" class="selectedTag" index="4"></div>
		</div>

		<div class="tags panel">
			<c:forEach var="primaryTag" items="${primaryTags}">
				<div>
					<img oid="${primaryTag.id}" title="${primaryTag.name}"
						src="<c:url value="/resources/${primaryTag.iconPath}" />" />
				</div>
			</c:forEach>
		</div>

		<div id="sub" class="subTags panel"></div>

		<div id="search" class="searchButtons">
			<button type="button" id="searchButton">Find</button>
			<button type="button" id="resetButton">Clear</button>
		</div>
	</div>

	<div id="feed" class="feed"></div>
</z:genericPage>