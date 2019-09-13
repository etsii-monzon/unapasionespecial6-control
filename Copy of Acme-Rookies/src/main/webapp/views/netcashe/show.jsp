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

<div>

	<p>
		<b><spring:message code="netcashe.track_id" />: </b>
		<jstl:out value="${netcashe.track_id }" />
	</p>

	<p>
		<b><spring:message code="netcashe.body" />: </b>
		<jstl:out value="${netcashe.body }" />
	</p>
	<p>
		<b><spring:message code="netcashe.status" />: </b>
		<jstl:out value="${netcashe.status }" />
	</p>


	<p>
		<b><spring:message code="netcashe.optionalPicture" />: </b>
		<jstl:if test="${netcashe.optionalPicture!='' }">
			<jstl:out value="${netcashe.optionalPicture }"></jstl:out>
		</jstl:if>
		<jstl:if test="${netcashe.optionalPicture=='' }">
			<spring:message code="netcashe.notPicture" />
		</jstl:if>
	</p>

	

</div>

<br />


<button type="button"
	onclick="javascript: relativeRedir('netcashe/company/list.do?auditId=${netcashe.audit.id}')">
	<spring:message code="netcashe.cancel" />
</button>

<br />