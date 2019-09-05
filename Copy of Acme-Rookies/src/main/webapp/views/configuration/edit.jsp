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
	function confirmRebrand() {

		return confirm("<spring:message code="configuration.checkRebrand"/>");

	}
</script>

<form:form action="${RequestURI }" modelAttribute="configuration">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="configuration.sistemName" path="sistemName" />
	<br>


	<acme:textbox code="configuration.welcomeSP" path="welcomeSP" />
	<br>

	<acme:textbox code="configuration.welcomeEN" path="welcomeEN" />
	<br>


	<acme:textbox code="configuration.bannerURL" path="bannerURL" />
	<br>



	<acme:textbox code="configuration.countryCode" path="countryCode" />
	<br>

	<jstl:if test="${reBrand== 'false'}">

		<input type="submit" name="notify" onclick="return confirmRebrand()"
			value="<spring:message code="configuration.reBrand" />" />&nbsp; 
		
	</jstl:if>

	<input type="submit" name="save"
		value="<spring:message code="configuration.save" />" />&nbsp; 
	
	<input type="button" name="cancel"
		value="<spring:message code="configuration.cancel" />"
		onclick="javascript:  window.location.replace('welcome/index.do');" />
	<br>
</form:form>