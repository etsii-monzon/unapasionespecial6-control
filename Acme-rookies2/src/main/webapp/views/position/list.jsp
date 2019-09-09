
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

<jstl:choose>
	<jstl:when test="${requestURI == 'position/listr.do'}"></jstl:when>
	<jstl:when test="${requestURI == 'position/company/list.do'}"></jstl:when>
	<jstl:otherwise>

		<form:form action="position/search.do" method="GET">

			<label for="keyword"> <spring:message code="position.keyword" />
			</label>
			<input type="text" name="keyword" id="keyword" />
			<acme:submit name="search" code="position.search" />
			<br />


		</form:form>
	</jstl:otherwise>

</jstl:choose>



<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="positions" requestURI="${requestURI}" id="row">



	<!-- Action links -->
	<security:authorize access="hasRole('COMPANY')">
		<display:column>
			<jstl:if test="${row.draftMode=='true'}">
				<a href="position/company/edit.do?positionId=${row.id}"> <spring:message
						code="position.edit" />
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>
	<!-- Attributes -->


	<display:column property="title" titleKey="position.title"
		sortable="true" />



	<display:column property="description" titleKey="position.description"
		sortable="false" />

	<display:column property="deadline" titleKey="position.deadline"
		sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
	<display:column property="profile" titleKey="position.profile"
		sortable="false" />



	<display:column property="skills" titleKey="position.skills"
		sortable="false" />

	<display:column property="technologies"
		titleKey="position.technologies" sortable="false" />
	<display:column property="salary" titleKey="position.salary"
		sortable="false" />

	<display:column property="ticker" titleKey="position.ticker"
		sortable="false" />



	<display:column property="draftMode" titleKey="position.draftMode"
		sortable="false" />


	<display:column title="Company">
		<a href="company/show.do?positionId=${row.id}"> <spring:message
				code="position.company" />
		</a>
	</display:column>

	<display:column title="Problems">
		<a href="problem/list.do?positionId=${row.id}"> <spring:message
				code="position.problem" />
		</a>
	</display:column>
	<display:column title="Audits">
		<a href="audit/listr.do?positionId=${row.id}"> <spring:message
				code="position.audit" />
		</a>
	</display:column>
	<security:authorize access="hasRole('COMPANY')">
		<display:column title="Show">
			<a href="position/company/show.do?positionId=${row.id}"> <spring:message
					code="position.show" />
			</a>
		</display:column>
	</security:authorize>
	<security:authorize access="hasRole('ROOKIE')">
		<spring:message code="position.applic" var="app" />
		<display:column title="${app }">
			<a href="application/rookie/create.do?positionId=${row.id}"> <spring:message
					code="position.apply" />
			</a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('COMPANY')">
		<display:column titleKey="position.quolets">
			<a href="xomp/company/list.do?positionId=${row.id}"> <spring:message
					code="position.quolets" />
			</a>
		</display:column>
	</security:authorize>



</display:table>
<security:authorize access="hasRole('COMPANY')">
	<div>
		<%-- 		<a href="position/company/create.do"> <spring:message
				code="position.create" /></a> --%>
		<button type="button"
			onclick="javascript: relativeRedir('position/company/create.do')">
			<spring:message code="position.create" />
		</button>
	</div>
</security:authorize>