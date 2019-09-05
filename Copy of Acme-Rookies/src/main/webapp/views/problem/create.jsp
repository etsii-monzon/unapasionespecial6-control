<%--
 * create.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="problem.create" />
</p>

<security:authorize access="hasRole('COMPANY')">
	<div>
		<form:form action="problem/company/edit.do" method="POST"
			id="formCreate" name="formCreate" modelAttribute="problem">

			
				

			<input type="submit" name="save"
				value="<spring:message code="problem.save"></spring:message>" />

			<spring:message code="problem.cancel" var="cancelHeader"></spring:message>
			<input type="button" name="cancel" value="${cancelHeader}"
				onclick="javascript:relativeRedir('problem/company/list.do')" />
		</form:form>

	</div>
</security:authorize>