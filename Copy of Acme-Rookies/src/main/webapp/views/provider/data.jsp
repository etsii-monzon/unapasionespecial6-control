


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
	name="provider" requestURI="${requestURI}" id="row" export="true">

	<display:setProperty name="export.pdf.filename" value="Data.pdf" />
	<display:setProperty name="export.pdf.include_header" value="true" />

	<display:column property="name" titleKey="provider.name" />
	<display:column property="surname" titleKey="provider.surname" />
	<display:column property="optionalPhoto"
		titleKey="provider.optionalPhoto" />
	<display:column property="email" titleKey="provider.email" />
	<display:column property="phoneNumber" titleKey="provider.phoneNumber" />
	<display:column property="optionalAddress"
		titleKey="provider.optionalAddress" />
	<display:column property="vat" titleKey="provider.vat" />
	<display:column property="make" titleKey="provider.make" />

	<display:column property="userAccount.username"
		titleKey="provider.username" />
	<display:column property="userAccount.password"
		titleKey="provider.password" />
	<display:column property="userAccount.authorities"
		titleKey="provider.userAccount.authorities" />

	<display:column property="creditCard.holderName"
		titleKey="creditCard.holderName" />
	<display:column property="creditCard.brandName"
		titleKey="creditCard.brandName" />
	<display:column property="creditCard.number"
		titleKey="provider.creditCard.number" />
	<display:column property="creditCard.expMonth"
		titleKey="creditCard.expMonth" />
	<display:column property="creditCard.expYear"
		titleKey="creditCard.expYear" />
	<display:column property="creditCard.cvv" titleKey="creditCard.cvv" />

	<display:column titleKey="provider.items">
		<jstl:forEach items="${items}" var="item">
			<jstl:out value="${item.name}" />
		</jstl:forEach>
	</display:column>


</display:table>
