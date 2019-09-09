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


<form:form action="item/provider/edit.do"
	modelAttribute="item">

	<form:hidden path="id" />
	<form:hidden path="version" />	


	<acme:textbox code="item.name" path="name" />
	<acme:textbox code="item.description" path="description" />	
	<acme:textbox code="item.link" path="link" />	
	<acme:textbox code="item.optionalPictures" path="optionalPictures" />	
	
	

	<acme:submit name="save" code="item.save" />

	<jstl:if test="${item.id != 0}">
		<acme:submit name="delete" code="item.delete" />

	</jstl:if>
	<acme:cancel url="item/provider/list.do"
		code="item.cancel" />
	<br />



</form:form>