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


<form:form action="submission/administrator/assign.do" modelAttribute="submission">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker" />
	<form:hidden path="moment" />
	<form:hidden path="cameraReady"/>
	<form:hidden path="status"/>
	<form:hidden path="conference"/>
	<form:hidden path="paper"/>
	
	<security:authorize access="hasRole('ADMIN')">
	
	<form:label path="reviewers">
			<spring:message code="submission.reviewers" />
		</form:label>	
		<form:select multiple="true" id="reviewers" path="reviewers">	
			<form:options items="${reviewers}" itemValue="id" itemLabel="name" />
		</form:select>
		<form:errors path="reviewers" cssClass="error" />

	
	<br />
	
	<p><spring:message code="submission.revAviso" /></p>
	
	</security:authorize>


	<button type="submit" name="save" class="btn btn-secondary">
		<spring:message code="submission.save" />
	</button>

	<button type="button" onclick="javascript: relativeRedir('submission/administrator/list.do')" >
		<spring:message code="submission.cancel" />
	</button>



</form:form>