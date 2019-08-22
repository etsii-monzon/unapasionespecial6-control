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
	<jstl:out value="${registration.conference.title }" />
</p>

<p>
	<b><spring:message code="conference.acronym" />: </b>
	<jstl:out value="${registration.conference.acronym }" />
</p>
<p>
	<b><spring:message code="conference.venue" />: </b>
	<jstl:out value="${registration.conference.venue }" />
</p>
<p>
	<b><spring:message code="conference.submissionDeadline" />: </b>
	<jstl:out value="${registration.conference.submissionDeadline }" />
</p>
<p>
	<b><spring:message code="conference.notificationDeadline" />: </b>
	<jstl:out value="${registration.conference.notificationDeadline }" />
</p>
<p>
	<b><spring:message code="conference.cameraDeadline" />: </b>
	<jstl:out value="${registration.conference.cameraDeadline }" />
</p>
<p>
	<b><spring:message code="conference.startDate" />: </b>
	<jstl:out value="${registration.conference.startDate }" />
</p>
<p>
	<b><spring:message code="conference.endDate" />: </b>
	<jstl:out value="${registration.conference.endDate }" />
</p>
<p>
	<b><spring:message code="conference.fee" />: </b>
	<jstl:out value="${registration.conference.fee }" />
</p>


<p>
	<b><spring:message code="registration.creditCard" />: </b>
	<jstl:out value="**** **** **** ${creditCard }" />
</p>








<br />


<acme:cancel url="registration/author/list.do"
	code="registration.cancel" />

<br />