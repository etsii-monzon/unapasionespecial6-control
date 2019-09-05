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




<form:form action="${requestURI}" modelAttribute="romp">
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
		<spring:message code="romp.body" />
	</form:label>
	<form:input path="body" />
	<form:errors class="error" path="body" />
	<br>
	<br>


	<%-- Atrib1 --%>
	<form:label path="optionalPicture">
		<spring:message code="romp.optionalPicture" />
	</form:label>
	<form:input path="optionalPicture" />
	<form:errors class="error" path="optionalPicture" />
	<br>
	<br>


	<!-- DraftMode -->
	<form:label path="draftMode">
		<spring:message code="romp.draftMode" />:
	</form:label>
	<form:select path="draftMode">
		<form:option value="True" label="True" />
		<form:option value="False" label="False" />

	</form:select>
	<br>
	<br>

	<%-- Buttons --%>

	<input type="submit" name="save"
		value="<spring:message code="romp.save"/>" />

	<input type="button" name="cancel"
		value="<spring:message code="romp.cancel" />"
		onClick="javascript: window.location.replace('romp/administrator/list.do?conferenceId=${conferenceId}')" />

	<br>
	<br>


</form:form>