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
			return confirm("<spring:message code="hacker.checkPhoneNumber"/>");
		}
	}
</script>

<security:authorize access="isAnonymous()">
	<jstl:set var="url" value="rookie/edit.do" />
</security:authorize>
<security:authorize access="hasRole('COMPANY')">
	<jstl:set var="url" value="rookie/rookie/edit.do" />
</security:authorize>
<form:form action="${url}" modelAttribute="hacker"
	onsubmit="return checkPhoneNumber()">

	<form:hidden path="id" />
	<form:hidden path="version" />


	<form:hidden path="userAccount.authorities" />
	<form:hidden path="userAccount.id" />
	<form:hidden path="userAccount.version" />


	<fieldset>
		<legend>
			<spring:message code="hacker.personalData" />
		</legend>

		<acme:textbox code="hacker.name" path="name" />
		<acme:textbox code="hacker.surname" path="surname" />
		<acme:textbox code="hacker.optionalPhoto" path="optionalPhoto" />
		<acme:textbox code="hacker.email" path="email" />
		<acme:textbox code="hacker.phoneNumber" path="phoneNumber" />
		<acme:textbox code="hacker.optionalAddress" path="optionalAddress" />
		<acme:textbox code="hacker.vat" path="vat" />

	</fieldset>

	<fieldset>
		<legend>
			<spring:message code="company.creditCard" />
		</legend>

		<acme:textbox code="creditCard.holderName"
			path="creditCard.holderName" />
		<acme:textbox code="creditCard.brandName" path="creditCard.brandName" />
		<acme:creditCardNumber code="creditCard.number"
			path="creditCard.number" />
		<acme:textbox code="creditCard.expMonth" path="creditCard.expMonth" />
		<acme:textbox code="creditCard.expYear" path="creditCard.expYear" />
		<acme:textbox code="creditCard.cvv" path="creditCard.cvv" />
	</fieldset>

	<security:authorize access="isAnonymous()">
		<fieldset>
			<legend>
				<spring:message code="hacker.userAccount" />
			</legend>
			<acme:textbox code="hacker.username" path="userAccount.username" />
			<acme:password code="hacker.password" path="userAccount.password" />
			<acme:password code="hacker.confirmPassword" path="confirmPassword" />
		</fieldset>

	</security:authorize>
	<br />

	<security:authorize access="isAnonymous()">

		<label><input type="checkbox" name="terms" value="terms"
			required /> <spring:message code="hacker.accept" /> <a
			href="help/terms.do"><spring:message code="master.page.terms" /></a></label>

		<br />

		<label><input type="checkbox" name="privacy" value="privacy"
			required /> <spring:message code="hacker.privacy" /> </label>

		<br />
	</security:authorize>

	<acme:submit name="save" code="hacker.save" />

	<acme:cancel url="welcome/index.do" code="hacker.cancel" />

</form:form>