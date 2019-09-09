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
		<b><spring:message code="application.moment" />: </b>
		<jstl:out value="${application.moment }" />
	</p>

	<p>
		<b><spring:message code="application.status" />: </b>
		<jstl:out value="${application.status }" />
	</p>
	
	<p>
		<b><spring:message code="application.problem" />: </b>
		<jstl:out value="${application.problem.title }" />
		<jstl:out value="${application.problem.statement }" />
		<jstl:out value="${application.problem.optionalHint }" />
	</p>

	<jstl:if test="${application.status!='PENDING' }">
		<p>
			<b><spring:message code="application.momentSubmit" />: </b>
			<jstl:out value="${application.momentSumbit }"></jstl:out>
		</p>
	
		<p>
			<b><spring:message code="application.explanations" />: </b>
			<jstl:out value="${application.explanations }" />
		</p>
	
		<p>
			<b><spring:message code="application.link" />: </b>
			<jstl:out value="${application.link }" />
		</p>
	</jstl:if>
	
	<p>
		<b><spring:message code="application.position" />: </b>
		<jstl:out value="${application.position.title }" />
		<jstl:out value="${application.position.description }" />
	</p>
	


</div>

<br />


<security:authorize access="hasRole('ROOKIE')">

	<acme:cancel url="application/rookie/list.do?positionId=${application.position.id }"
		
		code="application.cancel" />
		<jstl:if test="${application.status=='PENDING'}">
			<button type="button" onclick="javascript: relativeRedir('application/rookie/edit.do?applicationId=${application.id}')" >
				<spring:message code="application.edit" />
			</button>
		</jstl:if>
</security:authorize>

<security:authorize access="hasRole('COMPANY')">

	<acme:cancel url="application/company/list.do?positionId=${application.position.id }"
		
		code="application.cancel" />
		<jstl:if test="${application.status!='REJECTED'}">
		<jstl:if test="${application.status!='ACCEPTED'}">
			<%-- <a href="application/company/edit.do?applicationId=${application.id}"> <spring:message
				code="application.edit" />
			</a> --%>
			<jstl:if test="${application.status=='SUBMITTED'}">
			<button type="button" onclick="javascript: relativeRedir('application/company/edit.do?applicationId=${application.id}')" >
				<spring:message code="application.edit" />
			</button>
			</jstl:if>
			
		</jstl:if>
		</jstl:if>
		
		
</security:authorize>

<br />