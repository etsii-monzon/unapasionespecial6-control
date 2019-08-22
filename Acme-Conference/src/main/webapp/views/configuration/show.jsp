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

<p>
	<b><spring:message code="configuration.sistemName" />: </b>
	<jstl:out value="${configuration.sistemName}" />
</p>
<p>
	<b><spring:message code="configuration.welcomeSP" />: </b>
	<jstl:out value="${configuration.welcomeSP}" />
</p>
<p>
	<b><spring:message code="configuration.welcomeEN" />: </b>
	<jstl:out value="${configuration.welcomeEN}" />
</p>
<p>
	<b><spring:message code="configuration.bannerURL" />: </b>
	<jstl:out value="${configuration.bannerURL}" />
</p>

<p>
	<b><spring:message code="configuration.countryCode" />: </b>
	<jstl:out value="+${configuration.countryCode}" />
</p>
<!-- TOPICS -->

<p>
	<b><spring:message code="configuration.topic" />: </b>
	<jstl:forEach items="${configuration.topics}" var="actor">
		<!--  Si el idioma es inglés-->
		<jstl:if test="${languaje == 'en' }">
			<jstl:out value="${actor.englishName},"></jstl:out>
		</jstl:if>

		<jstl:if test="${languaje == 'es' }">
			<jstl:out value="${actor.spanishName},"></jstl:out>
		</jstl:if>
	</jstl:forEach>

	<button type="button"
		onclick="javascript: relativeRedir('configuration/administrator/topic/list.do')">
		<spring:message code="configuration.topic.manage" />
	</button>
</p>
<!-- MAKES -->
<p>
	<b><spring:message code="configuration.make" />: </b>
	<jstl:forEach items="${configuration.makes}" var="actor">
		<jstl:out value="${actor},"></jstl:out>
	</jstl:forEach>

	<button type="button"
		onclick="javascript: relativeRedir('configuration/administrator/brandName/list.do')">
		<spring:message code="configuration.brandName.manage" />
	</button>
</p>




<br />
<div>

	<button type="button"
		onclick="javascript: relativeRedir('configuration/administrator/edit.do')">
		<spring:message code="configuration.edit.c" />
	</button>

	<input type="button" name="cancel"
		value="<spring:message code="configuration.return" />"
		onClick="javascript: window.location.replace('welcome/index.do')" />

</div>
<br />