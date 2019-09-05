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
		<b><spring:message code="position.title" />: </b>
		<jstl:out value="${position.title }" />
	</p>

	<p>
		<b><spring:message code="position.description" />: </b>
		<jstl:out value="${position.description }" />
	</p>


	<p>
		<b><spring:message code="position.deadline" />: </b>
		<jstl:out value="${position.deadline }"></jstl:out>
	</p>

<p>
		<b><spring:message code="position.profile" />: </b>
		<jstl:out value="${position.profile }" />
	</p>
	
	<p>
		<b><spring:message code="position.skills" />: </b>
		<jstl:out value="${position.skills }" />
	</p>
	
	<p>
		<b><spring:message code="position.technologies" />: </b>
		<jstl:out value="${position.technologies }" />
	</p>
	
	<p>
		<b><spring:message code="position.salary" />: </b>
		<jstl:out value="${position.salary }" />
	</p>
	
	<p>
		<b><spring:message code="position.ticker" />: </b>
		<jstl:out value="${position.ticker }" />
	</p>
	
	<jstl:if test="${position.draftMode}">

		<p>
			<b><spring:message code="position.draft" /> </b>
		</p>
	</jstl:if>

	<jstl:if test="${!position.draftMode}">

		<p>
			<b><spring:message code="position.final" /> </b>
		</p>

	</jstl:if>
	<security:authorize access="hasRole('ROOKIE')">
		<a href="application/rookie/create.do?positionId=${position.id}"> <spring:message
			code="position.apply" />
		</a>
	</security:authorize>
	
	<security:authorize access="hasRole('COMPANY')">
		<a href="application/company/list.do?positionId=${position.id }"> <spring:message
			code="position.applications" />
		</a>
	
	</security:authorize>

</div>

<br />


<acme:cancel url="position/company/list.do"
	code="position.cancel" />

<br />