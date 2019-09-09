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
		<b><spring:message code="company.name" />: </b>
		<jstl:out value="${company.name }" />
	</p>
	<p>
		<b><spring:message code="company.surname" />: </b>
		<jstl:out value="${company.surname }" />
	</p>
	<p>
		<b><spring:message code="company.optionalPhoto" />: </b>
		<jstl:out value="${company.optionalPhoto }" />
	</p>
	<p>
		<b><spring:message code="company.email" />: </b>
		<jstl:out value="${company.email }" />
	</p>
	<p>
		<b><spring:message code="company.phoneNumber" />: </b>
		<jstl:out value="${company.phoneNumber }" />
	</p>
	<p>
		<b><spring:message code="company.optionalAddress" />: </b>
		<jstl:out value="${company.optionalAddress }" />
	</p>
	<p>
		<b><spring:message code="company.vat" />: </b>
		<jstl:out value="${company.vat }" />
	</p>
	<p>
		<b><spring:message code="company.commercialName" />: </b>
		<jstl:out value="${company.commercialName }" />
	</p>
	<p>
		<b><spring:message code="company.companyScore" />: </b>
		<jstl:out value="${companyScore}" />
	</p>


	<br />


	<security:authorize access="hasRole('COMPANY')">
		<acme:cancel url="position/company/list.do" code="position.cancel" />
	</security:authorize>
	<security:authorize access="hasRole('ROOKIE')">
		<acme:cancel url="position/list.do" code="position.cancel" />
	</security:authorize>
	<security:authorize access="hasRole('ADMIN')">
		<acme:cancel url="position/list.do" code="position.cancel" />
	</security:authorize>
	<security:authorize access="isAnonymous()">
		<acme:cancel url="position/list.do" code="position.cancel" />
	</security:authorize>





	<br />