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


<form:form action="registration/author/edit.do"
	modelAttribute="registration">

	<form:hidden path="id" />
	<form:hidden path="version" />



	<acme:select code="registration.conference" path="conference"
		items="${conferences}" itemLabel="title" />


	<fieldset>
		<legend>
			<spring:message code="author.creditCard" />
		</legend>

		<acme:textbox code="creditCard.holderName"
			path="creditCard.holderName" />
			
			
		<spring:message code="creditCard.brandName" />
		<form:select path="creditCard.brandName">
			<form:options items="${brandNames}" />
		</form:select>
		<form:errors class="error" path="creditCard.brandName" />
		<br> 
		
		
		<acme:creditCardNumber code="author.creditCard.number"
			path="creditCard.number" />
		<acme:number code="creditCard.expMonth" path="creditCard.expMonth"
			min="1" />
		<acme:number code="creditCard.expYear" path="creditCard.expYear"
			min="0" />
		<acme:number code="creditCard.cvv" path="creditCard.cvv" min="100" />

	</fieldset>

	<br />



	<acme:submit name="save" code="registration.save" />

	<jstl:if test="${registration.id != 0}">
		<acme:submit name="delete" code="registration.delete" />

	</jstl:if>
	<acme:cancel url="registration/author/list.do" code="conference.cancel" />
	<br />



</form:form>