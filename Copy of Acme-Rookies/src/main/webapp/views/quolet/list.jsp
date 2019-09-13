
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

<!-- Hay que a�adir el Search -->


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="quolets" requestURI="${requestURI}" id="row">

	<jstl:choose>
		<jstl:when test="${row.status == 'TROME'}">
			<jstl:set var="color" value="DarkOliveGreen" />
		</jstl:when>
		<jstl:when test="${row.status == 'GORK'}">
			<jstl:set var="color" value="Moccasin" />
		</jstl:when>
		<jstl:when test="${row.status == null}">
			<jstl:set var="color" value="white" />
		</jstl:when>

		<jstl:otherwise>
			<jstl:set var="color" value="Gray" />
		</jstl:otherwise>
	</jstl:choose>



	<!-- Action links -->
	<security:authorize access="hasRole('COMPANY')">
		<display:column titleKey="quolet.edit"
			style="background-color: ${color}">
			<jstl:if test="${row.draftMode=='true'}">

				<a href="quolet/company/edit.do?quoletId=${row.id}"> <spring:message
						code="quolet.edit" />
				</a>
			</jstl:if>

		</display:column>
	</security:authorize>

	<!-- Attributes -->


	<display:column property="name" titleKey="quolet.name"
		style="background-color: ${color}" />

	<display:column property="body" titleKey="quolet.body"
		style="background-color: ${color}" />

	<display:column property="optionalPicture"
		titleKey="quolet.optionalPicture" style="background-color: ${color}" />


	<display:column property="status" titleKey="quolet.status"
		sortable="false" style="background-color: ${color}" />


	<display:column property="draftMode" titleKey="quolet.draftMode"
		sortable="false" style="background-color: ${color}" />

	<security:authorize access="hasRole('COMPANY')">
		<display:column titleKey="quolet.display"
			style="background-color: ${color}">
			<a href="quolet/company/display.do?quoletId=${row.id}"> <spring:message
					code="quolet.show" />
			</a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('COMPANY')">

		<display:column titleKey="quolet.delete"
			style="background-color: ${color}">
			<jstl:if test="${row.draftMode=='true'}">

				<a href="quolet/company/delete.do?quoletId=${row.id}"> <spring:message
						code="quolet.delete" />
				</a>
			</jstl:if>
		</display:column>

	</security:authorize>
</display:table>

<security:authorize access="hasRole('COMPANY')">
	<div>

		<button type="button"
			onclick="javascript: relativeRedir('quolet/company/create.do?auditId=${auditId}')">
			<spring:message code="quolet.create" />
		</button>
	</div>
</security:authorize>

<security:authorize access="hasRole('AUDITOR')">
	<acme:cancel url="audit/auditor/list.do" code="quolet.return" />
</security:authorize>

<security:authorize access="hasRole('COMPANY')">
	<button type="button"
		onclick="javascript: relativeRedir('audit/listr.do?positionId=${positionId}')">
		<spring:message code="quolet.return" />
	</button>
</security:authorize>
