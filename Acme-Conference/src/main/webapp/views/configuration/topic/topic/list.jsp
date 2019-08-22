
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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


<!-- Listing grid -->

<div>
	<button type="button"
		onclick="javascript: relativeRedir('configuration/administrator/topic/create.do')">
		<spring:message code="configuration.create.topic" />
	</button>
</div>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="topics" requestURI="${requestURI}" id="row">
	<!-- Action links -->



	<!-- Attributes -->

	<display:column titleKey="configuration.topic" sortable="true">
		<jstl:out value="${row }"></jstl:out>
	</display:column>



	<display:column titleKey="configuration.delete">
		<a href="configuration/administrator/topic/delete.do?topic=${row}">
			<spring:message code="configuration.delete" />
		</a>
	</display:column>

</display:table>

<div>
	<button type="button"
		onclick="javascript: relativeRedir('configuration/administrator/edit.do')">
		<spring:message code="configuration.return" />
	</button>
</div>

