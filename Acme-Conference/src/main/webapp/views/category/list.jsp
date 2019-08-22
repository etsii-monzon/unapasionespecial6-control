
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

<div>
	<button type="button"
		onclick="javascript: relativeRedir('category/administrator/create.do')">
		<spring:message code="category.create" />
	</button>
</div>

<display:table pagesize="20" class="displaytag" name="categories"
	requestURI="${requestURI}" id="row">



	<!-- Action links -->

	<display:column titleKey="category.edit">
		<!-- Comprobar que no sale para ROOT -->
		<jstl:if test="${row.parent != null }">
			<a href="category/administrator/edit.do?categoryId=${row.id}"> <spring:message
					code="category.edit" />
			</a>
		</jstl:if>
	</display:column>


	<!-- Attributes -->

	<!-- English Name -->
	<jstl:if test="${languaje == 'en' }">
		<display:column property="englishTitle"
			titleKey="category.englishTitle" />
		<!-- Parent -->
		<display:column titleKey="category.parent">
			<jstl:if test="${row.englishTitle == 'CATEGORY' }">
				<jstl:out value="It's the root"></jstl:out>
			</jstl:if>
			<jstl:if test="${row.englishTitle != 'CATEGORY' }">
				<jstl:out value="${row.parent.englishTitle }"></jstl:out>
			</jstl:if>
		</display:column>

		<!-- Children -->
		<display:column titleKey="category.children">
			<jstl:forEach items="${row.children }" var="x">
				<jstl:out value="${x.englishTitle}"></jstl:out>,
			</jstl:forEach>
		</display:column>

	</jstl:if>

	<!-- Spanish Name -->
	<jstl:if test="${languaje == 'es' }">
		<display:column property="spanishTitle"
			titleKey="category.spanishTitle" />
		<!-- Parent -->
		<display:column titleKey="category.parent">
			<jstl:if test="${row.spanishTitle == 'CATEGORÍA' }">
				<jstl:out value="Es la raíz"></jstl:out>
			</jstl:if>
			<jstl:if test="${row.spanishTitle != 'CATEGORÍA' }">
				<jstl:out value="${row.parent.spanishTitle }"></jstl:out>
			</jstl:if>
		</display:column>

		<!-- Children -->
		<display:column titleKey="category.children">
			<jstl:forEach items="${row.children }" var="x">
				<jstl:out value="${x.spanishTitle}"></jstl:out>,
			</jstl:forEach>
		</display:column>

	</jstl:if>

	<display:column titleKey="category.delete">
		<!-- Comprobar que no sale para ROOT -->
		<jstl:if test="${row.parent != null }">
			<a href="category/administrator/delete.do?categoryId=${row.id}">
				<spring:message code="category.delete" />
			</a>
		</jstl:if>
	</display:column>
</display:table>
<br />
<input type="button" name="cancel"
	value="<spring:message code="category.return" />"
	onClick="javascript: window.location.replace('welcome/index.do');" />



