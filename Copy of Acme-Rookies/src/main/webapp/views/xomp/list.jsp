
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
	name="xomps" requestURI="${requestURI}" id="row">

	<jstl:choose>
		<jstl:when test="${row.publicationMoment > mes}">
			<jstl:set var="color" value="LightYellow" />
		</jstl:when>
		<jstl:when
			test="${row.publicationMoment < mes && row.publicationMoment > dosMeses}">
			<jstl:set var="color" value="MediumPurple" />
		</jstl:when>
		<jstl:when test="${row.publicationMoment == null}">
			<jstl:set var="color" value="white" />
		</jstl:when>

		<jstl:otherwise>
			<jstl:set var="color" value="SkyBlue" />
		</jstl:otherwise>
	</jstl:choose>



	<!-- Action links -->
	<security:authorize access="hasRole('COMPANY')">
		<display:column titleKey="xomp.edit"
			style="background-color: ${color}">
			<jstl:if test="${row.draftMode=='true'}">

				<a href="xomp/company/edit.do?xompId=${row.id}"> <spring:message
						code="xomp.edit" />
				</a>
			</jstl:if>

		</display:column>
	</security:authorize>

	<!-- Attributes -->


	<display:column property="ticker" titleKey="xomp.ticker"
		style="background-color: ${color}" />

	<display:column property="body" titleKey="xomp.body"
		style="background-color: ${color}" />

	<display:column property="optionalPicture"
		titleKey="xomp.optionalPicture" style="background-color: ${color}" />

	<jstl:if test="${english=='false'}">
		<display:column property="publicationMoment"
			titleKey="xomp.publicationMoment" sortable="true"
			format="{0,date,dd-MM-yy HH:mm}" style="background-color: ${color}" />
	</jstl:if>
	<jstl:if test="${english=='true'}">
		<display:column property="publicationMoment"
			titleKey="xomp.publicationMoment" sortable="true"
			format="{0,date,yy/MM/dd HH:mm}" style="background-color: ${color}" />
	</jstl:if>



	<display:column property="draftMode" titleKey="xomp.draftMode"
		sortable="false" style="background-color: ${color}" />

	<security:authorize access="hasRole('COMPANY')">
		<display:column titleKey="xomp.display"
			style="background-color: ${color}">
			<a href="xomp/company/display.do?xompId=${row.id}"> <spring:message
					code="xomp.show" />
			</a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('COMPANY')">

		<display:column titleKey="xomp.delete"
			style="background-color: ${color}">
			<jstl:if test="${row.draftMode=='true'}">

				<a href="xomp/company/delete.do?xompId=${row.id}"> <spring:message
						code="xomp.delete" />
				</a>
			</jstl:if>
		</display:column>

	</security:authorize>
</display:table>

<security:authorize access="hasRole('COMPANY')">
	<div>

		<button type="button"
			onclick="javascript: relativeRedir('xomp/company/create.do?auditId=${auditId}')">
			<spring:message code="xomp.create" />
		</button>
	</div>
</security:authorize>

<security:authorize access="hasRole('AUDITOR')">
	<acme:cancel url="audit/auditor/list.do" code="xomp.return" />
</security:authorize>

<security:authorize access="hasRole('COMPANY')">
	<button type="button"
		onclick="javascript: relativeRedir('audit/listr.do?positionId=${positionId}')">
		<spring:message code="xomp.return" />
	</button>
</security:authorize>
