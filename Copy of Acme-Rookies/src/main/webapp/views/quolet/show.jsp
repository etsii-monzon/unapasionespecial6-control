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
		<b><spring:message code="quolet.name" />: </b>
		<jstl:out value="${quolet.name }" />
	</p>

	<p>
		<b><spring:message code="quolet.body" />: </b>
		<jstl:out value="${quolet.body }" />
	</p>
	<p>
		<b><spring:message code="quolet.status" />: </b>
		<jstl:out value="${quolet.status }" />
	</p>


	<p>
		<b><spring:message code="quolet.optionalPicture" />: </b>
		<jstl:if test="${quolet.optionalPicture!='' }">
			<jstl:out value="${quolet.optionalPicture }"></jstl:out>
		</jstl:if>
		<jstl:if test="${quolet.optionalPicture=='' }">
			<spring:message code="quolet.notPicture" />
		</jstl:if>
	</p>

	

</div>

<br />


<button type="button"
	onclick="javascript: relativeRedir('quolet/company/list.do?auditId=${quolet.audit.id}')">
	<spring:message code="quolet.cancel" />
</button>

<br />