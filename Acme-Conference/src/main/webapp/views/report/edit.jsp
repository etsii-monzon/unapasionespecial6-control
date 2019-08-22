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


<form:form action="report/reviewer/edit.do" modelAttribute="report">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="submission" />



		<acme:number code="report.originality" path="originality" min="0"
		max="10" />
		<acme:number code="report.quality" path="quality" min="0"
		max="10" />
		<acme:number code="report.readability" path="readability" min="0"
		max="10" />
		<acme:textbox code="report.comment" path="comment" />
	<form:label path="decision">
		<spring:message code="report.decision" />:
	</form:label>
	<form:select path="decision">
		<form:option value="BORDER-LINE" label="BORDER-LINE" />


		<form:option value="ACCEPT" label="ACCEPT" />
		<form:option value="REJECT" label="REJECT" />

	</form:select>		
	
	<br />

	<acme:submit name="save" code="report.save" />

	<jstl:if test="${report.id != 0}">
		<acme:submit name="delete" code="report.delete" />

	</jstl:if>
	<acme:cancel url="submission/reviewer/list.do" code="report.cancel" />
	<br />



</form:form>