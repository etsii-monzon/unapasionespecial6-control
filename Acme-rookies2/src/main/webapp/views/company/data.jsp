


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

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="company" requestURI="${requestURI}" id="row" export="true">

	<display:setProperty name="export.pdf.filename" value="Data.pdf" />
	<display:setProperty name="export.pdf.include_header" value="true" />

	<display:column property="name" titleKey="company.name" />
	<display:column property="surname" titleKey="company.surname" />
	<display:column property="optionalPhoto"
		titleKey="company.optionalPhoto" />
	<display:column property="email" titleKey="company.email" />
	<display:column property="phoneNumber" titleKey="company.phoneNumber" />
	<display:column property="optionalAddress"
		titleKey="company.optionalAddress" />
	<display:column property="vat" titleKey="company.vat" />
	<display:column property="commercialName"
		titleKey="company.commercialName" />
	<display:column property="userAccount.username"
		titleKey="company.username" />
	<display:column property="userAccount.password"
		titleKey="company.password" />
	<display:column property="userAccount.authorities"
		titleKey="company.userAccount.authorities" />

	<display:column property="creditCard.holderName"
		titleKey="creditCard.holderName" />
	<display:column property="creditCard.brandName"
		titleKey="creditCard.brandName" />
	<display:column property="creditCard.number"
		titleKey="creditCard.number" />
	<display:column property="creditCard.expMonth"
		titleKey="creditCard.expMonth" />
	<display:column property="creditCard.expYear"
		titleKey="creditCard.expYear" />
	<display:column property="creditCard.cvv" titleKey="creditCard.cvv" />

	<display:column titleKey="company.problems">
		<jstl:forEach items="${problems}" var="item">
			<jstl:out value="${item.title}" />
		</jstl:forEach>
	</display:column>
	
	<display:column titleKey="company.positions">
		<jstl:forEach items="${positions}" var="item">
			<jstl:out value="${item.title}" />
		</jstl:forEach>
	</display:column>



</display:table>

