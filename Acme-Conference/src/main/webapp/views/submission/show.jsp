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
	<b><spring:message code="submission.ticker" />: </b>
	<jstl:out value="${submission.ticker }" />
</p>

<p>
	<b><spring:message code="submission.moment" />: </b>
	<jstl:out value="${submission.moment }" />
</p>




<p>
	<b><spring:message code="submission.status" />: </b>
	<jstl:out value="${submission.status }" />
</p>


<p>
	<b><spring:message code="submission.reviewers" /> :</b>

	<jstl:forEach items="${submission.reviewers }" var="actor">
		<jstl:out value="${actor.name},"></jstl:out>
	</jstl:forEach>
</p>

<br />
<jstl:if test="${submission.cameraReady }">
	<p>
		<spring:message code="submission.ready" />
	</p>
</jstl:if>



<br />

<security:authorize access="hasRole('AUTHOR')">
	<acme:cancel url="submission/author/list.do" code="submission.cancel" />
</security:authorize>
<jstl:if test="${requestURI == 'submission/administrator/show.do' }">
	<security:authorize access="hasRole('ADMIN')">
		<acme:cancel url="submission/administrator/list.do"
			code="submission.return" />
	</security:authorize>
</jstl:if>

<jstl:if
	test="${requestURI == 'activity/administrator/submission/show.do' }">
	<security:authorize access="hasRole('ADMIN')">
		<acme:cancel
			url="activity/administrator/list.do?conferenceId=${conferenceId}"
			code="submission.return" />
	</security:authorize>
</jstl:if>


<br />