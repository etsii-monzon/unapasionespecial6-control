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
		<b><spring:message code="xomp.ticker" />: </b>
		<jstl:out value="${xomp.ticker }" />
	</p>

	<p>
		<b><spring:message code="xomp.body" />: </b>
		<jstl:out value="${xomp.body }" />
	</p>


	<p>
		<b><spring:message code="xomp.optionalPicture" />: </b>
		<jstl:if test="${xomp.optionalPicture!='' }">
			<jstl:out value="${xomp.optionalPicture }"></jstl:out>
		</jstl:if>
		<jstl:if test="${xomp.optionalPicture=='' }">
			<spring:message code="xomp.notPicture" />
		</jstl:if>
	</p>

	<p>
		<b><spring:message code="xomp.publicationMoment" />: </b>
		<jstl:if test="${xomp.draftMode}">

			<spring:message code="xomp.draft" />

		</jstl:if>

		<jstl:if test="${!xomp.draftMode}">
			<jstl:if test="${english=='false'}">

				<fmt:formatDate value="${xomp.publicationMoment}"
					pattern="dd/MM/yyyy HH:mm" />
			</jstl:if>

		</jstl:if>
		<jstl:if test="${!xomp.draftMode}">
			<jstl:if test="${english=='true'}">

				<fmt:formatDate value="${xomp.publicationMoment}"
					pattern="yyyy/MM/dd HH:mm" />
			</jstl:if>

		</jstl:if>
	</p>

</div>

<br />


<button type="button"
	onclick="javascript: relativeRedir('xomp/company/list.do?auditId=${xomp.audit.id}')">
	<spring:message code="xomp.cancel" />
</button>

<br />