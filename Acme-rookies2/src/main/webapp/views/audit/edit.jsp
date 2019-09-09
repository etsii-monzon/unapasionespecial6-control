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


<form:form action="audit/auditor/edit.do" modelAttribute="audit">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<jstl:if test="${audit.id != 0}">
		<form:hidden path="position" />
	</jstl:if>




	<acme:textbox code="audit.text" path="text" />
	<acme:textbox code="audit.score" path="score" />
	<jstl:if test="${audit.id == 0}">
		<acme:select code="audit.position" path="position"
			items="${positions}" itemLabel="title" />
	</jstl:if>


	<form:label path="draftMode">
		<spring:message code="audit.draftMode" />:
	</form:label>
	<form:select path="draftMode">
		<form:option value="True" label="True" />

		<form:option value="False" label="False" />

	</form:select>
	<br />


	<acme:submit name="save" code="audit.save" />

	<jstl:if test="${audit.id != 0}">
		<acme:submit name="delete" code="audit.delete" />

	</jstl:if>
	<acme:cancel url="audit/auditor/list.do" code="audit.cancel" />
	<br />



</form:form>