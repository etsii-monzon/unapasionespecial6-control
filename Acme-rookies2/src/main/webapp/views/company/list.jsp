
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<!-- Listing grid -->

<!-- Hay que añadir el Search -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="companies" requestURI="${requestURI}" id="row">



	<!-- Action links -->
	
	<!-- Attributes -->


	<display:column property="name" titleKey="company.name"
		sortable="true" />
		<display:column property="surname" titleKey="company.surname"
		sortable="true" />
		<display:column property="optionalPhoto" titleKey="company.optionalPhoto"
		sortable="true" />
		<display:column property="email" titleKey="company.email"
		sortable="true" />
		<display:column property="phoneNumber" titleKey="company.phoneNumber"
		sortable="true" />
		<display:column property="optionalAddress" titleKey="company.optionalAddress"
		sortable="true" />
		<display:column property="vat" titleKey="company.vat"
		sortable="true" />
		<display:column property="commercialName" titleKey="company.commercialName"
		sortable="true" />


	
	
	<display:column>
		<a href="position/listr.do?companyId=${row.id}"> <spring:message
				code="position.companies" />
		</a>
	</display:column>

</display:table>
