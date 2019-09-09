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


<form:form action="position/company/edit.do"
	modelAttribute="position">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path ="ticker"/>


	<acme:textbox code="position.title" path="title" />
	<acme:textbox code="position.description" path="description" />
	<acme:moment code="position.deadline" path="deadline" />
	<acme:textbox code="position.profile" path="profile" />
	<acme:textbox code="position.skills" path="skills" />
	<acme:textbox code="position.technologies" path="technologies" />
	<acme:textbox code="position.salary" path="salary" />
	
	
	<form:label path="draftMode">
		<spring:message code="position.draftMode" />:
	</form:label>
	<form:select path="draftMode">
		<form:option value="True" label="True" />
		<jstl:if test="${problemSize>1}">
		
		<form:option value="False" label="False" />
		</jstl:if>
		
	</form:select>
	<br />


	<acme:submit name="save" code="position.save" />

	<jstl:if test="${position.id != 0}">
		<acme:submit name="delete" code="position.delete" />

	</jstl:if>
	<acme:cancel url="position/company/list.do"
		code="position.cancel" />
	<br />



</form:form>