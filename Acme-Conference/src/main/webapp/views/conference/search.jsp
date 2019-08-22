
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


<!-- Search -->

<form:form action="conference/searchList.do" method="GET">

	<label for="keyword"> <spring:message code="conference.keyword" />
	</label>
	<input type="search" name="keyword" id="keyword" size="35"
		placeholder="Search by TITLE, VENUE or SUMMARY" />

	<acme:submit name="search" code="conference.search" />

</form:form>


<!-- BROWSE BY CATEGORY -->
<security:authorize access="isAuthenticated()">
	<form:form action="conference/actor/listByCategory.do" method="GET">

		<label for="category"> <spring:message
				code="conference.category" />
		</label>
		<select name="categoryId" id="categoryId">
			<jstl:if test="${languaje == 'en' }">
				<option value="0">--Select Category--</option>
				<jstl:forEach items="${categories }" var="x">
					<option value="${x.id }">${x.englishTitle }</option>
				</jstl:forEach>
			</jstl:if>
			<jstl:if test="${languaje == 'es' }">
				<option value="0">--Seleccionar Categoría--</option>
				<jstl:forEach items="${categories }" var="x">
					<option value="${x.id }">${x.spanishTitle }</option>
				</jstl:forEach>
			</jstl:if>
		</select>

		<acme:submit name="save" code="conference.search" />

	</form:form>
</security:authorize>
<input type="button" name="cancel"
	value="<spring:message code="conference.return" />"
	onClick="javascript: window.location.replace('welcome/index.do');" />

