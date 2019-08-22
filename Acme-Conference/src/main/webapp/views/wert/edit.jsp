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




<form:form action="${requestURI}" modelAttribute="wert">
	<%-- Hidden properties from category--%>
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="publicationMoment" />
	<form:hidden path="administrator" />
	<form:hidden path="ticker" />

	<td><input type="hidden" name="conferenceId"
		value="${conferenceId}"></td>



	<%-- Body --%>
	<form:label path="body">
		<spring:message code="wert.body" />
	</form:label>
	<form:input path="body" />
	<form:errors class="error" path="body" />
	<br>
	<br>


	<%-- Atrib1 --%>
	<form:label path="atrib1">
		<spring:message code="wert.atrib1" />
	</form:label>
	<form:input path="atrib1" />
	<form:errors class="error" path="atrib1" />
	<br>
	<br>

	<%-- Atrib2 --%>
	<form:label path="atrib2">
		<spring:message code="wert.atrib2" />
	</form:label>
	<form:input path="atrib2" />
	<form:errors class="error" path="atrib2" />
	<br>
	<br>

	<!-- DraftMode -->
	<form:label path="draftMode">
		<spring:message code="wert.draftMode" />:
	</form:label>
	<form:select path="draftMode">
		<form:option value="True" label="True" />
		<form:option value="False" label="False" />

	</form:select>
	<br>
	<br>

	<%-- Buttons --%>

	<input type="submit" name="save"
		value="<spring:message code="wert.save"/>" />

	<input type="button" name="cancel"
		value="<spring:message code="wert.cancel" />"
		onClick="javascript: window.location.replace('wert/administrator/list.do?conferenceId=${conferenceId}')" />

	<br>
	<br>


</form:form>