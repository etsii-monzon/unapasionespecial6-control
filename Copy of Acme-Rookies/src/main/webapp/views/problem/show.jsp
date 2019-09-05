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
		<b><spring:message code="problem.title" />: </b>
		<jstl:out value="${problem.title }" />
	</p>

	<p>
		<b><spring:message code="problem.statement" />: </b>
		<jstl:out value="${problem.statement }" />
	</p>


	<p>
		<b><spring:message code="problem.optionalHint" />: </b>
		<jstl:out value="${problem.optionalHint }"></jstl:out>
	</p>

<p>
		<b><spring:message code="problem.attachments" />: </b>
		<jstl:out value="${problem.attachments }" />
	</p>
	
	<p>
		<b><spring:message code="problem.position" />: </b>
		<jstl:out value="${problem.position.title }" />
	</p>
	
	
	
	<jstl:if test="${problem.draftMode}">

		<p>
			<b><spring:message code="problem.draft" /> </b>
		</p>
	</jstl:if>

	<jstl:if test="${!problem.draftMode}">

		<p>
			<b><spring:message code="problem.final" /> </b>
		</p>

	</jstl:if>

</div>

<br />

<security:authorize access="hasRole('COMPANY')">
	<acme:cancel url="problem/company/list.do"
		code="problem.cancel" />
</security:authorize>

<security:authorize access="hasRole('ROOKIE')">
	<acme:cancel url="problem//list.do?positionId=${problem.position.id }"
		code="problem.cancel" />
</security:authorize>
<br />