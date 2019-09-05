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


<form:form action="conference/administrator/edit.do"
	modelAttribute="conference">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="comments" />
	<form:hidden path="romps" />




	<acme:textbox code="conference.title" path="title" />
	<acme:textbox code="conference.acronym" path="acronym" />
	<acme:textbox code="conference.venue" path="venue" />
	<acme:textbox code="conference.summary" path="summary" />


	<acme:moment code="conference.submissionDeadlineED"
		path="submissionDeadline" />
	<acme:moment code="conference.notificationDeadlineED"
		path="notificationDeadline" />
	<acme:moment code="conference.cameraDeadlineED" path="cameraDeadline" />
	<acme:moment code="conference.startDateED" path="startDate" />
	<acme:moment code="conference.endDateED" path="endDate" />



	<acme:number code="conference.fee" path="fee" min="0" max="9999" />
	<%-- Category --%>
	<spring:message code="conference.category" />
	<form:select path="category" itemValue="id">
		<jstl:if test="${languaje == 'en' }">
			<form:options items="${categories}" itemLabel="englishTitle"
				itemValue="id" />
		</jstl:if>
		<jstl:if test="${languaje == 'es' }">
			<form:options items="${categories}" itemLabel="spanishTitle"
				itemValue="id" />
		</jstl:if>
	</form:select>
	<form:errors class="error" path="category" />
	<br />


	<form:label path="draftMode">
		<spring:message code="conference.draftMode" />:
	</form:label>
	<form:select path="draftMode">
		<form:option value="True" label="True" />


		<form:option value="False" label="False" />

	</form:select>
	<br />



	<acme:submit name="save" code="conference.save" />

	<jstl:if test="${conference.id != 0}">
		<acme:submit name="delete" code="conference.delete" />

	</jstl:if>
	<acme:cancel url="conference/administrator/list.do"
		code="conference.cancel" />
	<br />



</form:form>