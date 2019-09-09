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

<script type="text/javascript">
	function checkPhoneNumber() {
		var pN = $("input#phoneNumber").val();
		//[+],[(],[)] coincide car�cteres de dentro de los corchetes
		//\d cualquier n�mero de 0-9
		//? puede ser 0 o 1,esdecir,puede aparecer el +CC o el +CC (AC)
		//{4,} m�nimo 4 d�gitos
		var regex = /^(([+]\d{1,2})\s([(]\d{1,2}[)]\s)?)?\d{4,}$/;
		if (regex.test(pN)) {
			return true;
		} else {
			return confirm("<spring:message code="auditor.checkPhoneNumber"/>");
		}
	}
</script>

<security:authorize access="hasRole('AUDITOR')">
	<jstl:set var="url" value="auditor/auditor/edit.do" />
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
	<jstl:set var="url" value="auditor/administrator/edit.do" />
</security:authorize>

<form:form action="${url}" modelAttribute="auditor"
	onsubmit="return checkPhoneNumber()">

	<form:hidden path="id" />
	<form:hidden path="version" />



	<form:hidden path="userAccount.authorities" />
	<form:hidden path="userAccount.id" />
	<form:hidden path="userAccount.version" />

	<fieldset>
		<legend>
			<spring:message code="auditor.personalData" />
		</legend>

		<acme:textbox code="auditor.name" path="name" />
		<acme:textbox code="auditor.surname" path="surname" />
		<acme:textbox code="auditor.optionalPhoto" path="optionalPhoto" />
		<acme:textbox code="auditor.email" path="email" />
		<acme:textbox code="auditor.phoneNumber" path="phoneNumber" />
		<acme:textbox code="auditor.optionalAddress" path="optionalAddress" />
		<acme:textbox code="auditor.vat" path="vat" />
	</fieldset>

	<fieldset>
		<legend>
			<spring:message code="auditor.creditCard" />
		</legend>

		<acme:textbox code="creditCard.holderName"
			path="creditCard.holderName" />
		<acme:textbox code="creditCard.brandName" path="creditCard.brandName" />
		<acme:textbox code="administrator.creditCard.number"
			path="creditCard.number" />
		<acme:textbox code="creditCard.expMonth" path="creditCard.expMonth" />
		<acme:textbox code="creditCard.expYear" path="creditCard.expYear" />
		<acme:textbox code="creditCard.cvv" path="creditCard.cvv" />

	</fieldset>

	<security:authorize access="hasRole('ADMIN')">

		<fieldset>
			<legend>
				<spring:message code="auditor.userAccount" />
			</legend>
			<acme:textbox code="auditor.username" path="userAccount.username" />

			<acme:password code="auditor.password" path="userAccount.password" />

			<acme:password code="auditor.confirmPassword" path="confirmPassword" />


		</fieldset>
	</security:authorize>

	<br />
	<acme:submit name="save" code="auditor.save" />

	<acme:cancel url="welcome/index.do" code="auditor.cancel" />
</form:form>