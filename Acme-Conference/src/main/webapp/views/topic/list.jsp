
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

<display:table pagesize="5" class="displaytag" name="topics"
	requestURI="${requestURI}" id="row">
	<!-- Action links -->


	<display:column titleKey="configuration.edit">
		<a href="configuration/administrator/topic/edit.do?topicId=${row.id}">
			<spring:message code="configuration.edit" />
		</a>
	</display:column>
	<!-- Attributes -->

	<!-- ENGLISH NAME -->
	<display:column property="englishName" titleKey="topic.en"
		sortable="true" />
	<!-- SPANISH NAME -->
	<display:column property="spanishName" titleKey="topic.sp"
		sortable="true" />



	<display:column titleKey="configuration.delete">
		<a
			href="configuration/administrator/topic/delete.do?topicId=${row.id}">
			<spring:message code="configuration.delete" />
		</a>
	</display:column>

</display:table>

<div>
	<button type="button"
		onclick="javascript: relativeRedir('configuration/administrator/show.do')">
		<spring:message code="configuration.return" />
	</button>
</div>

