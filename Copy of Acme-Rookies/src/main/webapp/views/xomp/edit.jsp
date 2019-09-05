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


<form:form action="xomp/company/edit.do" modelAttribute="xomp">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="publicationMoment" />
	<form:hidden path="ticker" />
	<form:hidden path="audit" />




	<acme:textarea code="xomp.body" path="body" />
	<acme:textbox code="xomp.optionalPicture" path="optionalPicture" />


	<form:label path="draftMode">
		<spring:message code="xomp.draftMode" />:
	</form:label>
	<form:select path="draftMode">
		<form:option value="True" label="True" />

		<form:option value="False" label="False" />

	</form:select>
	<br />

	<acme:submit name="save" code="xomp.save" />
	<acme:cancel url="xomp/company/list.do?auditId=${auditId }"
		code="xomp.cancel" />
	<br />



</form:form>