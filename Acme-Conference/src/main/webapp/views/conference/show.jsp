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
	<b><spring:message code="conference.title" />: </b>
	<jstl:out value="${conference.title }" />
</p>

<p>
	<b><spring:message code="conference.acronym" />: </b>
	<jstl:out value="${conference.acronym }" />
</p>
<p>
	<b><spring:message code="conference.venue" />: </b>
	<jstl:out value="${conference.venue }" />
</p>
<p>
	<b><spring:message code="conference.summary" />: </b>
	<jstl:out value="${conference.summary }" />
</p>
<p>
	<b><spring:message code="conference.submissionDeadline" />: </b>
	<jstl:out value="${conference.submissionDeadline }" />
</p>
<p>
	<b><spring:message code="conference.notificationDeadline" />: </b>
	<jstl:out value="${conference.notificationDeadline }" />
</p>
<p>
	<b><spring:message code="conference.cameraDeadline" />: </b>
	<jstl:out value="${conference.cameraDeadline }" />
</p>
<p>
	<b><spring:message code="conference.startDate" />: </b>
	<jstl:out value="${conference.startDate }" />
</p>
<p>
	<b><spring:message code="conference.endDate" />: </b>
	<jstl:out value="${conference.endDate }" />
</p>
<p>
	<b><spring:message code="conference.fee" />: </b>
	<jstl:out value="${conference.fee }" />
</p>

<p>
	<b><spring:message code="conference.category" />: </b>
	<jstl:if test="${languaje == 'en' }">
		<jstl:out value="${conference.category.englishTitle }" />
	</jstl:if>

	<jstl:if test="${languaje == 'es' }">
		<jstl:out value="${conference.category.spanishTitle }" />
	</jstl:if>
</p>








<br />


<acme:cancel url="conference/administrator/list.do"
	code="conference.cancel" />

<br />