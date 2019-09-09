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


<form:form action="problem/company/edit.do" modelAttribute="problem">

	<form:hidden path="id" />
	<form:hidden path="version" />


	<acme:textbox code="problem.title" path="title" />
	<acme:textbox code="problem.statement" path="statement" />
	
	<acme:textbox code="problem.optionalHint" path="optionalHint" />
	<acme:textbox code="problem.attachments" path="attachments" />

	<form:label path="draftMode">
		<spring:message code="problem.draftMode" />:
	</form:label>
	<form:select path="draftMode">
		<form:option value="True" label="True" />
		<form:option value="False" label="False" />

	</form:select>

	<acme:select code="company.position" path="position" items="${positions}" itemLabel="title"/>
	

	



	<acme:submit name="save" code="problem.save" />
	<acme:cancel url="problem/company/list.do" code="problem.cancel" />
	<br />



</form:form>