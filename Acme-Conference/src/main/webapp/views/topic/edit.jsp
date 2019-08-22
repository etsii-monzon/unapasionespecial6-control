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


<form:form action="${requestURI}" modelAttribute="topic">
	<%-- Hidden properties from topics--%>
	<form:hidden path="id" />
	<form:hidden path="version" />



	<%-- Topic --%>
	<%-- ENglish Name --%>
	<form:label path="englishName">
		<spring:message code="topic.englishName" />
	</form:label>
	<form:input path="englishName" />
	<form:errors class="error" path="englishName" />
	<br>
	<br>

	<%-- Spanish Name --%>
	<form:label path="spanishName">
		<spring:message code="topic.spanishName" />
	</form:label>
	<form:input path="spanishName" />
	<form:errors class="error" path="spanishName" />
	<br>
	<br>




	<input type="submit" name="save"
		value="<spring:message code="configuration.save" />" />&nbsp; 
	
	<input type="button" name="cancel"
		value="<spring:message code="configuration.cancel" />"
		onclick="javascript:  window.location.replace('configuration/administrator/topic/list.do');" />
	<br>
</form:form>
<br>
<div></div>
