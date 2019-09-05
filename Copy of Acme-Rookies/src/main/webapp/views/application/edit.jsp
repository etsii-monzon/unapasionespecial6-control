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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="${requestURI}"
	modelAttribute="application">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="position" />


	<security:authorize access="hasRole('ROOKIE')">
		<jstl:if test="${application.id!=0}">
			<form:hidden path="status" />
			<form:hidden path="problem" />
			
			<p>
				<b><spring:message code="application.problem" />: </b>
				<jstl:out value="${application.problem.title }" />:
				<jstl:out value="${application.problem.statement }" /></br>
				<jstl:out value="${application.problem.optionalHint }" /></br>
			</p>
		
			<acme:textbox code="application.explanations" path="explanations" />
			<acme:textbox code="application.link" path="link"/>

		</jstl:if>
	</security:authorize>
	
	<security:authorize access="hasRole('COMPANY')">
		<jstl:if test="${application.id!=0}">
			<form:hidden path="momentSumbit" />
			<form:hidden path="explanations" />
			<form:hidden path="link" />
			<form:hidden path="problem" />
			
			<p>
				<b><spring:message code="application.explanations" />: </b>
				<jstl:out value="${application.explanations }" />
			</p>
			<p>
				<b><spring:message code="application.link" />: </b>
				<jstl:out value="${application.link }" /></br>
			</p>
			<p>
				<b><spring:message code="application.momentSubmit" />: </b>
				<jstl:out value="${application.momentSumbit }" /></br>
			</p>
		
			<p><spring:message code="application.status" />: </p>
			<form:select path="status">
				<form:option value="-" label="-" selected="selected"/>
				<form:option value="ACCEPTED" label="ACCEPTED" />
				<form:option value="REJECTED" label="REJECTED" />
			</form:select>
			<form:errors cssClass="error" path="status" />
			<spring:message code="application.status.info"/>

		</jstl:if>
	</security:authorize>
	
	
		<acme:submit name="save" code="application.save"/>
 		
 		<security:authorize access="hasRole('COMPANY')">
			<acme:cancel url="application/company/list.do?positionId=${application.position.id }" code="application.cancel"/>
		</security:authorize>
		
		<security:authorize access="hasRole('ROOKIE')">
			<acme:cancel url="application/rookie/list.do?positionId=${application.position.id }" code="application.cancel"/>
		</security:authorize>


</form:form>