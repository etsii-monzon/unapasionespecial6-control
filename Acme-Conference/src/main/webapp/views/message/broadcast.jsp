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


<form:form action="message/administrator/broadcast.do"
	modelAttribute="message2">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sender" />
	<form:hidden path="date" />
	<form:hidden path="recipients" />



	<form:label path="subject">
		<spring:message code="message.subject" />
	</form:label>
	<form:input path="subject" />
	<form:errors class="error" path="subject" />
	<br>
	<br>


	<%-- Body --%>
	<form:label path="body">
		<spring:message code="message.body" />
	</form:label>
	<form:textarea path="body" />
	<form:errors class="error" path="body" />
	<br>
	<br>


	<%-- Topic --%>
	<spring:message code="message.topic" />
	<form:input path="topic" />
	<form:errors class="error" path="topic" />
	<br>
	<br>

	<%-- Buttons --%>

	<input type="submit" name="save"
		value="<spring:message code="message.save"/>" />

	<input type="button" name="cancel"
		value="<spring:message code="message.cancel" />"
		onClick="javascript: window.location.replace('message/list.do')" />

	<br>
	<br>


</form:form>