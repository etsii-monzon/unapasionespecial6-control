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


<form:form action="${requestURI }">



	<%-- Topic --%>
	<spring:message code="configuration.topic" />
	<jstl:if test="${topic != '' }">
		<input name="topic" value="${topic }">
	</jstl:if>

	<jstl:if test="${topic == '' }">
		<input name="topic" />
	</jstl:if>


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
