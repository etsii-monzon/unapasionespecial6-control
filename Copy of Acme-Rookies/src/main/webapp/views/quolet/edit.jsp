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


<form:form action="quolet/company/edit.do" modelAttribute="quolet">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<%-- 	<form:hidden path="publicationMoment" />
 --%>
	<form:hidden path="name" />
	<form:hidden path="audit" />




	<acme:textarea code="quolet.body" path="body" />
	<acme:textbox code="quolet.optionalPicture" path="optionalPicture" />

	<form:label path="status">
		<spring:message code="quolet.status" />:
	</form:label>
	<form:select path="status">
		<form:option value="TROME" label="TROME" />
		<form:option value="GORK" label="GORK" />
		<form:option value="STIM" label="STIM" />
		<form:option value="ZURK" label="ZURK" />


	</form:select>
	<br />

	<form:label path="draftMode">
		<spring:message code="quolet.draftMode" />:
	</form:label>
	<form:select path="draftMode">
		<form:option value="True" label="True" />

		<form:option value="False" label="False" />

	</form:select>
	<br />

	<acme:submit name="save" code="quolet.save" />
	<acme:cancel url="quolet/company/list.do?auditId=${auditId }"
		code="quolet.cancel" />
	<br />



</form:form>