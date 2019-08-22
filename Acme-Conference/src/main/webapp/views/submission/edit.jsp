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


<form:form action="submission/author/edit.do"
	modelAttribute="submission">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="status" />
	<form:hidden path="ticker" />
	<form:hidden path="moment" />
	<form:hidden path="reviewers" />

	<jstl:if test="${submission.id==0 }">
	<acme:select code="submission.conference" path="conference"
		items="${conferences}" itemLabel="title" />
	<br />
	</jstl:if>
	<jstl:if test="${submission.id!=0 }">
	<form:hidden path="conference"/>
	</jstl:if>
	<br />

	<fieldset>
		<legend>
			<strong><spring:message code="author.paper" /></strong>
		</legend>

		<acme:textbox code="paper.title" path="paper.title" />
		<%-- Authors --%>
		<spring:message code="paper.authors" />
		<form:select path="paper.authors" multiple="true" itemValue="id">
			<form:options items="${authors}" itemLabel="userAccount.username"
				itemValue="id" />
		</form:select>
		<form:errors class="error" path="paper.authors" />


		<acme:textbox code="paper.summary" path="paper.summary" />
		<acme:textbox code="paper.document" path="paper.document" />

	</fieldset>

	<br />



	<acme:submit name="save" code="submission.save" />

	<%-- <jstl:if test="${submission.id != 0}">
		<acme:submit name="delete" code="submission.delete" />

	</jstl:if> --%>
	<acme:cancel url="submission/author/list.do" code="submission.cancel" />
	<br />



</form:form>