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




<form:form action="${requestURI}" modelAttribute="category">
	<%-- Hidden properties from category--%>
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="children" />



	<%-- EnglishName --%>
	<form:label path="englishTitle">
		<spring:message code="category.englishTitle2" />
	</form:label>
	<form:input path="englishTitle" />
	<form:errors class="error" path="englishTitle" />
	<br>
	<br>


	<%-- Spanish Title --%>
	<form:label path="spanishTitle">
		<spring:message code="category.spanishTitle2" />
	</form:label>
	<form:input path="spanishTitle" />
	<form:errors class="error" path="spanishTitle" />
	<br>
	<br>

	<%-- Parent --%>
	<spring:message code="category.parent" />
	<form:select path="parent" itemValue="id">
		<jstl:if test="${languaje == 'en' }">
			<form:option value="0">--Select Category--</form:option>
			<form:options items="${parents}" itemLabel="englishTitle"
				itemValue="id" />
		</jstl:if>
		<jstl:if test="${languaje == 'es' }">
			<form:option value="0">--Seleccionar Categoría--</form:option>
			<form:options items="${parents}" itemLabel="spanishTitle"
				itemValue="id" />
		</jstl:if>
	</form:select>
	<form:errors class="error" path="parent" />

	<br>
	<br>

	<%-- Buttons --%>

	<input type="submit" name="save"
		value="<spring:message code="category.save"/>" />

	<input type="button" name="cancel"
		value="<spring:message code="category.cancel" />"
		onClick="javascript: window.location.replace('category/administrator/list.do')" />

	<br>
	<br>


</form:form>