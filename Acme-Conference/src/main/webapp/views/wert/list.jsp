
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


<security:authorize access="hasRole('ADMIN')">

	<div>
		<button type="button"
			onclick="javascript: relativeRedir('wert/administrator/create.do?conferenceId=${conferenceId}')">
			<spring:message code="wert.create" />
		</button>
	</div>
</security:authorize>

<display:table pagesize="20" class="displaytag" name="werts"
	requestURI="${requestURI}" id="row">

	<jstl:choose>
		<jstl:when test="${row.publicationMoment > mes}">
			<jstl:set var="color" value="green" />
		</jstl:when>
		<jstl:when
			test="${row.publicationMoment < mes && row.publicationMoment > dosMeses}">
			<jstl:set var="color" value="MediumPurple" />
		</jstl:when>
		<jstl:when test="${row.publicationMoment == null}">
			<jstl:set var="color" value="white" />
		</jstl:when>

		<jstl:otherwise>
			<jstl:set var="color" value="blue" />
		</jstl:otherwise>
	</jstl:choose>



	<!-- Action links -->

	<security:authorize access="hasRole('ADMIN')">
		<display:column titleKey="wert.edit"
			style="background-color: ${color}">
			<jstl:if test="${row.administrator == admin }">
				<jstl:if test="${row.draftMode == true }">
					<a href="wert/administrator/edit.do?wertId=${row.id}"> <spring:message
							code="wert.edit" />
					</a>
				</jstl:if>
			</jstl:if>
		</display:column>
	</security:authorize>

	<!-- Attributes -->

	<!-- Ticker -->
	<security:authorize access="hasRole('ADMIN')">
		<display:column property="ticker" titleKey="wert.ticker"
			style="background-color: ${color}" />
	</security:authorize>

	<!-- Publication Moment -->

	<jstl:if test="${languaje == 'es' }">
		<display:column property="publicationMoment"
			style="background-color: ${color}" titleKey="wert.publicationMoment"
			format="{0,date,dd-MM-yy HH:mm}" />
	</jstl:if>

	<jstl:if test="${languaje == 'en' }">
		<display:column property="publicationMoment"
			style="background-color: ${color}" titleKey="wert.publicationMoment"
			format="{0,date,yy/MM/dd  HH:mm}" />
	</jstl:if>
	<!-- Body -->

	<display:column property="body" titleKey="wert.body"
		style="background-color: ${color}" />

	<!-- Attrib1 -->

	<display:column property="atrib1" titleKey="wert.atrib1"
		style="background-color: ${color}" />

	<!-- Attrib2 -->

	<display:column property="atrib2" titleKey="wert.atrib2"
		style="background-color: ${color}" />

	<!-- DraftMode -->
	<security:authorize access="hasRole('ADMIN')">
		<display:column property="draftMode" titleKey="wert.draftMode"
			style="background-color: ${color}" />
	</security:authorize>

	<!-- Administrator -->

	<display:column property="administrator.userAccount.username"
		titleKey="wert.administrator" style="background-color: ${color}" />

	<security:authorize access="hasRole('ADMIN')">
		<display:column titleKey="wert.delete"
			style="background-color: ${color}">
			<jstl:if test="${row.administrator == admin }">
				<jstl:if test="${row.draftMode == true }">
					<a href="wert/administrator/delete.do?wertId=${row.id}"> <spring:message
							code="wert.delete" />
					</a>
				</jstl:if>
			</jstl:if>
		</display:column>
	</security:authorize>


</display:table>
<br />
<input type="button" name="cancel"
	value="<spring:message code="wert.return" />"
	onClick="javascript: window.location.replace('welcome/index.do');" />



