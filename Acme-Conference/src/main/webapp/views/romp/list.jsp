
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
			onclick="javascript: relativeRedir('romp/administrator/create.do?conferenceId=${conferenceId}')">
			<spring:message code="romp.create" />
		</button>
	</div>
</security:authorize>

<display:table pagesize="20" class="displaytag" name="romps"
	requestURI="${requestURI}" id="row">

	<jstl:choose>
		<jstl:when test="${row.publicationMoment > mes}">
			<jstl:set var="color" value="DarkMagenta" />
		</jstl:when>
		<jstl:when
			test="${row.publicationMoment < mes && row.publicationMoment > dosMeses}">
			<jstl:set var="color" value="DarkBlue" />
		</jstl:when>
		<jstl:when test="${row.publicationMoment == null}">
			<jstl:set var="color" value="white" />
		</jstl:when>

		<jstl:otherwise>
			<jstl:set var="color" value="FloralWhite" />
		</jstl:otherwise>
	</jstl:choose>



	<!-- Action links -->
	<security:authorize access="hasRole('ADMIN')">
		<display:column titleKey="romp.show"
			style="background-color: ${color}">
			<a href="romp/administrator/show.do?rompId=${row.id}"> <spring:message
					code="romp.show" />
			</a>

		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
		<display:column titleKey="romp.edit"
			style="background-color: ${color}">
			<jstl:if test="${row.administrator == admin }">
				<jstl:if test="${row.draftMode == true }">
					<a href="romp/administrator/edit.do?rompId=${row.id}"> <spring:message
							code="romp.edit" />
					</a>
				</jstl:if>
			</jstl:if>
		</display:column>
	</security:authorize>

	<!-- Attributes -->

	<!-- Ticker -->
	<security:authorize access="hasRole('ADMIN')">
		<display:column property="ticker" titleKey="romp.ticker"
			style="background-color: ${color}" />
	</security:authorize>

	<!-- Publication Moment -->

	<jstl:if test="${languaje == 'es' }">
		<display:column property="publicationMoment"
			style="background-color: ${color}" titleKey="romp.publicationMoment"
			format="{0,date,dd-MM-yy HH:mm}" />
	</jstl:if>

	<jstl:if test="${languaje == 'en' }">
		<display:column property="publicationMoment"
			style="background-color: ${color}" titleKey="romp.publicationMoment"
			format="{0,date,yy/MM/dd  HH:mm}" />
	</jstl:if>
	<!-- Body -->

	<display:column property="body" titleKey="romp.body"
		style="background-color: ${color}" />

	<!-- Attrib1 -->

	<display:column property="optionalPicture"
		titleKey="romp.optionalPicture" style="background-color: ${color}" />



	<!-- DraftMode -->
	<security:authorize access="hasRole('ADMIN')">
		<display:column property="draftMode" titleKey="romp.draftMode"
			style="background-color: ${color}" />
	</security:authorize>

	<!-- Administrator -->

	<display:column property="administrator.userAccount.username"
		titleKey="romp.administrator" style="background-color: ${color}" />

	<security:authorize access="hasRole('ADMIN')">
		<display:column titleKey="romp.delete"
			style="background-color: ${color}">
			<jstl:if test="${row.administrator == admin }">
				<jstl:if test="${row.draftMode == true }">
					<a href="romp/administrator/delete.do?rompId=${row.id}"> <spring:message
							code="romp.delete" />
					</a>
				</jstl:if>
			</jstl:if>
		</display:column>
	</security:authorize>


</display:table>
<br />
<input type="button" name="cancel"
	value="<spring:message code="romp.return" />"
	onClick="javascript: window.location.replace('welcome/index.do');" />



