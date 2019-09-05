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

	<p>
		<b><spring:message code="provider.name" />: </b>
		<jstl:out value="${provider.name }" />
	</p>
	<p>
		<b><spring:message code="provider.surname" />: </b>
		<jstl:out value="${provider.surname }" />
	</p>
	<p>
		<b><spring:message code="provider.optionalPhoto" />: </b>
		<jstl:out value="${provider.optionalPhoto }" />
	</p>
	<p>
		<b><spring:message code="provider.email" />: </b>
		<jstl:out value="${provider.email }" />
	</p>
	<p>
		<b><spring:message code="provider.phoneNumber" />: </b>
		<jstl:out value="${provider.phoneNumber }" />
	</p>
	<p>
		<b><spring:message code="provider.optionalAddress" />: </b>
		<jstl:out value="${provider.optionalAddress }" />
	</p>
	<p>
		<b><spring:message code="provider.vat" />: </b>
		<jstl:out value="${provider.vat }" />
	</p>
	
	<p>
		<b><spring:message code="provider.make" />: </b>
		<jstl:out value="${provider.make }" />
	</p>


	<br />


	
	<security:authorize access="isAnonymous()">
		<acme:cancel url="item/list.do" code="item.cancel" />
	</security:authorize>





	<br />