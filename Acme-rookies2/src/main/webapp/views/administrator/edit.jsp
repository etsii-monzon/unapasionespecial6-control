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
			return confirm("<spring:message code="administrator.checkPhoneNumber"/>");
		}
	}
</script>
<form:form action="administrator/administrator/edit.do"
	modelAttribute="administrator" onsubmit="return checkPhoneNumber()">

	<form:hidden path="id" />
	<form:hidden path="version" />



	<form:hidden path="userAccount.authorities" />
	<form:hidden path="userAccount.id" />
	<form:hidden path="userAccount.version" />

	<fieldset>
		<legend>
			<spring:message code="administrator.personalData" />
		</legend>

		<acme:textbox code="administrator.name" path="name" />
		<acme:textbox code="administrator.surname" path="surname" />
		<acme:textbox code="administrator.optionalPhoto" path="optionalPhoto" />
		<acme:textbox code="administrator.email" path="email" />
		<acme:textbox code="administrator.phoneNumber" path="phoneNumber" />
		<acme:textbox code="administrator.optionalAddress"
			path="optionalAddress" />
		<acme:textbox code="administrator.vat" path="vat" />
	</fieldset>

	<fieldset>
		<legend>
			<spring:message code="administrator.creditCard" />
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


	<fieldset>
		<legend>
			<spring:message code="administrator.userAccount" />
		</legend>
		<acme:textbox code="administrator.username"
			path="userAccount.username" />

		<acme:password code="administrator.password"
			path="userAccount.password" />

	</fieldset>
	<br />
	<acme:submit name="save" code="administrator.save" />

	<acme:cancel url="welcome/index.do" code="administrator.cancel" />



</form:form>