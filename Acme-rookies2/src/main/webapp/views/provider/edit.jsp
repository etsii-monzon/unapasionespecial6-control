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
			return confirm("<spring:message code="company.checkPhoneNumber"/>");
		}
	}
</script>


<security:authorize access="isAnonymous()">
	<jstl:set var="url" value="provider/edit.do" />
</security:authorize>
<security:authorize access="hasRole('COMPANY')">
	<jstl:set var="url" value="provider/provider/edit.do" />
</security:authorize>
<form:form action="${url}" modelAttribute="provider"
	onsubmit="return checkPhoneNumber()">

	<form:hidden path="id" />
	<form:hidden path="version" />


	<form:hidden path="userAccount.authorities" />
	<form:hidden path="userAccount.id" />
	<form:hidden path="userAccount.version" />


	<fieldset>
		<legend>
			<spring:message code="provider.personalData" />
		</legend>

		<acme:textbox code="provider.name" path="name" />
		<acme:textbox code="provider.surname" path="surname" />
		<acme:textbox code="provider.optionalPhoto" path="optionalPhoto" />
		<acme:textbox code="provider.email" path="email" />
		<acme:textbox code="provider.phoneNumber" path="phoneNumber" />
		<acme:textbox code="provider.optionalAddress" path="optionalAddress" />
		<acme:textbox code="provider.vat" path="vat" />
		<acme:textbox code="provider.make" path="make" />

	</fieldset>

	<fieldset>
		<legend>
			<spring:message code="provider.creditCard" />
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
				<spring:message code="provider.userAccount" />
			</legend>
			<acme:textbox code="provider.username" path="userAccount.username" />
			<acme:password code="provider.password" path="userAccount.password" />
			<acme:password code="provider.confirmPassword" path="confirmPassword" />
		</fieldset>
	</security:authorize>

	<br />

	<security:authorize access="isAnonymous()">

		<label><input type="checkbox" name="terms" value="terms"
			required /> <spring:message code="provider.accept" /> <a
			href="help/terms.do"><spring:message code="master.page.terms" /></a></label>

		<br />

		<label><input type="checkbox" name="privacy" value="privacy"
			required /> <spring:message code="provider.privacy" /> </label>

		<br />
	</security:authorize>

	<acme:submit name="save" code="provider.save" />

	<acme:cancel url="welcome/index.do" code="provider.cancel" />

</form:form>