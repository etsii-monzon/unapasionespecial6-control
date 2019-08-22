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
		<b><spring:message code="report.originality" />: </b>
		<jstl:out value="${report.originality }" />
	</p>
	
	<p>
		<b><spring:message code="report.quality" />: </b>
		<jstl:out value="${report.quality }" />
	</p>
	
	<p>
		<b><spring:message code="report.readability" />: </b>
		<jstl:out value="${report.readability }" />
	</p>
	
	<p>
		<b><spring:message code="report.decision" />: </b>
		<jstl:out value="${report.decision }" />
	</p>
	<p>
		<b><spring:message code="report.submissioncon" />: </b>
		<jstl:out value="${report.submission.conference.title }" />
	</p>
	
	
	


	

<br />


 <acme:cancel url="submission/reviewer/list.do"
	code="report.cancel" /> 

<br />