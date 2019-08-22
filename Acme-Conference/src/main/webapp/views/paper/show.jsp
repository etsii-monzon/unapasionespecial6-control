<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<p>
	<b><spring:message code="paper.title" />: </b>
	<jstl:out value="${paper.title}" />
</p>

<p>
	<b><spring:message code="paper.authors" />: </b>
	<jstl:forEach items="${paper.authors }" var="actor">
		<jstl:out value="${actor.userAccount.username}"></jstl:out>
	</jstl:forEach>
</p>

<p>
	<b><spring:message code="paper.summary" />: </b>
	<jstl:out value="${paper.summary}" />
</p>
<p>
	<b><spring:message code="paper.document" />: </b>
	<jstl:out value="${paper.document}" />
</p>





<br />

<security:authorize access="hasRole('AUTHOR')">

	<acme:cancel url="submission/author/list.do" code="submission.cancel" />
</security:authorize>
<security:authorize access="hasRole('REVIEWER')">

	<acme:cancel url="submission/reviewer/list.do" code="submission.cancel" />
</security:authorize>
<br />