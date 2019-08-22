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




<form:form action="${requestURI}" modelAttribute="finder">
	<%-- Hidden properties from category--%>
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="conferences" />


	<%-- Keyword --%>
	<form:label path="keyword">
		<spring:message code="finder.keyword" />
	</form:label>
	<form:input path="keyword" />
	<form:errors class="error" path="keyword" />
	<br>
	<br>

	<%-- Start Date --%>
	<form:label path="startDate">
		<spring:message code="finder.startDate" />
	</form:label>
	<form:input path="startDate" />
	<form:errors class="error" path="startDate" />
	<br>
	<br>

	<%-- End Date --%>
	<form:label path="endDate">
		<spring:message code="finder.endDate" />
	</form:label>
	<form:input path="endDate" />
	<form:errors class="error" path="endDate" />
	<br>
	<br>

	<%-- Category --%>
	<spring:message code="finder.category" />
	<form:select path="category" itemValue="id">
		<jstl:if test="${languaje == 'en' }">
			<form:option value="0">--Select Category--</form:option>

			<form:options items="${categories}" itemLabel="englishTitle"
				itemValue="id" />
		</jstl:if>
		<jstl:if test="${languaje == 'es' }">
			<form:option value="0">--Seleccionar Categoría--</form:option>

			<form:options items="${categories}" itemLabel="spanishTitle"
				itemValue="id" />
		</jstl:if>
	</form:select>
	<form:errors class="error" path="category" />
	<br>
	<br>

	<%-- Max Fee --%>
	<acme:number code="finder.fee" path="fee" min="0" />

	<br>
	<br>

	<%-- Buttons --%>

	<input type="submit" name="save"
		value="<spring:message code="finder.save"/>" />


	<input type="button" name="cancel"
		value="<spring:message code="finder.cancel" />"
		onClick="javascript: window.location.replace('welcome/index.do')" />

	<br>
	<br>


</form:form>