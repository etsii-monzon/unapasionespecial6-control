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
	<b><spring:message code="romp.ticker" />: </b>
	<jstl:out value="${romp.ticker }" />
</p>
<p>
	<b><spring:message code="romp.body" />: </b>
	<jstl:out value="${romp.body }" />
</p>
<p>
	<b><spring:message code="romp.optionalPicture" />: </b>
	<jstl:out value="${romp.optionalPicture }" />
</p>
<p>
	<b><spring:message code="romp.draftMode" />: </b>
	<jstl:out value="${romp.draftMode }" />
</p>

<jstl:if test="${languaje == 'en' }">
	<p>
		<b><spring:message code="romp.publicationMoment" />: </b>
		<fmt:formatDate value="${romp.publicationMoment }"
			pattern="yy/MM/dd  HH:mm" />
	</p>
</jstl:if>
<jstl:if test="${languaje == 'es' }">
	<p>
		<b><spring:message code="romp.publicationMoment" />: </b>
		<fmt:formatDate value="${romp.publicationMoment }"
			pattern="dd-MM-yy  HH:mm" />
	</p>
</jstl:if>





<br />


<input type="button" name="cancel"
	value="<spring:message code="romp.return" />"
	onClick="javascript: window.location.replace('romp/administrator/list.do?conferenceId=${conferenceId}')" />


<br />