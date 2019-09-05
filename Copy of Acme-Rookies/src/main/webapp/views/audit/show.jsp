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

<div>

	<p>
		<b><spring:message code="audit.text" />: </b>
		<jstl:out value="${audit.text }" />
	</p>

	<p>
		<b><spring:message code="audit.score" />: </b>
		<jstl:out value="${audit.score }" />
	</p>


	<p>
		<b><spring:message code="audit.moment" />: </b>
		<jstl:out value="${audit.moment }"></jstl:out>
	</p>


	<jstl:if test="${audit.draftMode}">

		<p>
			<b><spring:message code="audit.draft" /> </b>
		</p>
	</jstl:if>

	<jstl:if test="${!audit.draftMode}">

		<p>
			<b><spring:message code="audit.final" /> </b>
		</p>

	</jstl:if>
	
	
</div>

<br />


<acme:cancel url="audit/auditor/list.do"
	code="audit.cancel" />

<br />