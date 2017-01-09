<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="menu">
<div class="logo"><a href="/"><img src="<c:url value="/resources/pics/home/logobeta.png" />"/></a></div>
	<div class="menuItems">
		<ul>
			<li>
				<a class="menuLink" href="/">Home</a>
			</li>
			<sec:authorize access="hasAnyRole('ROLE_MODERATOR')">
				<li>
					<a class="menuLink"  href="/panel/webItems/all">All Items</a>
				</li>
			</sec:authorize>
			<sec:authorize access="hasAnyRole( 'ROLE_EDITOR','ROLE_MODERATOR')">
				<li>
					<a class="menuLink"  href="/panel/webItems/my/<%= new java.text.SimpleDateFormat("yyyy").format(new java.util.Date()) %>">My Items</a>
				</li>
			</sec:authorize>
			<li>
				<a class="menuLink" href="/user/help">Help</a>
			</li>
			<sec:authorize access="hasAnyRole('ROLE_USER', 'ROLE_EDITOR','ROLE_MODERATOR')">
				<li>
					<a class="menuLink"  href="/user/logout">Log Out</a>
				</li>
			</sec:authorize>
		</ul>
	</div>
</div>
