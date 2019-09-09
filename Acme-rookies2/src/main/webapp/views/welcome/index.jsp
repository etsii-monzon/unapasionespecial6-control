<%--
 * index.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<script type="text/javascript">
	function alertName() {
		alert('<spring:message code="welcome.notify" />');
	}
</script>
<p>
	<spring:message code="welcome.greeting.prefix" />
	<security:authorize access="isAuthenticated()">
		${name}
	</security:authorize>
	<spring:message code="welcome.greeting.suffix" />
</p>
<p>${mensaje}</p>
<p>
	<spring:message code="welcome.greeting.current.time" />
	${moment}
</p>
<security:authorize access="isAuthenticated()">
	<jstl:if test="${reBrand== 'true' && isNotify == 'false'}">
		<script type="text/javascript">
			window.onload = alertName;
		</script>

	</jstl:if>


</security:authorize>

<security:authorize access="isAuthenticated()">
	<p>
		<b><spring:message code="welcome.breach" /></b>
	</p>
	<p style="border: ridge #000000 1px; color: grey">
		<spring:message code="welcome.security" />
	</p>
</security:authorize>

