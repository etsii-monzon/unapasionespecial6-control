


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
	name="auditor" requestURI="${requestURI}" id="row" export="true">

	<display:setProperty name="export.pdf.filename" value="Data.pdf" />
	<display:setProperty name="export.pdf.include_header" value="true" />

	<display:column property="name" titleKey="auditor.name" />
	<display:column property="surname" titleKey="auditor.surname" />
	<display:column property="optionalPhoto"
		titleKey="auditor.optionalPhoto" />
	<display:column property="email" titleKey="auditor.email" />
	<display:column property="phoneNumber"
		titleKey="auditor.phoneNumber" />
	<display:column property="optionalAddress"
		titleKey="auditor.optionalAddress" />
	<display:column property="vat" titleKey="auditor.vat" />
	<display:column property="userAccount.username"
		titleKey="auditor.username" />
	<display:column property="userAccount.password"
		titleKey="auditor.password" />
	<display:column property="userAccount.authorities"
		titleKey="auditor.userAccount.authorities" />

	<display:column property="creditCard.holderName"
		titleKey="creditCard.holderName" />
	<display:column property="creditCard.brandName"
		titleKey="creditCard.brandName" />
	<display:column property="creditCard.number"
		titleKey="auditor.creditCard.number" />
	<display:column property="creditCard.expMonth"
		titleKey="creditCard.expMonth" />
	<display:column property="creditCard.expYear"
		titleKey="creditCard.expYear" />
	<display:column property="creditCard.cvv" titleKey="creditCard.cvv" />
	
	<display:column titleKey="auditor.audits">
		<jstl:forEach items="${audits}" var="item">
			<jstl:out value="${item.text}" />
		</jstl:forEach>
	</display:column>


</display:table>
