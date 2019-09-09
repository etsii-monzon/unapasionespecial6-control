<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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

<!-- Listing grid -->

<!-- Hay que añadir el Search -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="applications" requestURI="${requestURI}" id="row">

	<!-- Attributes -->


	<display:column property="moment" titleKey="application.moment"
		sortable="false" format="{0,date,dd/MM/yyyy HH:mm}" />

	<display:column property="status" titleKey="application.status"
		sortable="true" />

	<display:column property="explanations" titleKey="application.explanations"
		sortable="false" />

	<display:column property="link" titleKey="application.link"
		sortable="false" />
	<spring:message code="application.problem"  var = "prob"/>
	<display:column title="${prob}">
	<jstl:if test="${row.status!='PENDING' }">
		<a href="problem/company/show.do?problemId=${row.problem.id}"> <spring:message
			code="application.problem" />
		</a>
	</jstl:if>
	</display:column>
	<spring:message code="application.show"  var = "show"/>
	<display:column title="${show }">
		<security:authorize access="hasRole('COMPANY')">
			<a href="application/company/show.do?applicationId=${row.id}"> <spring:message
				code="application.show" />
			</a>
		
		</security:authorize>
		<security:authorize access="hasRole('ROOKIE')">
			<a href="application/rookie/show.do?applicationId=${row.id}"> <spring:message
				code="application.show" />
			</a> 
		</security:authorize>
	</display:column>
	

</display:table>

<acme:cancel url="position/list.do" code="application.cancel" />