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
	<b><spring:message code="message.sender" />: </b>
	<jstl:out value="${m.sender.email}" />
</p>

<p>
	<b><spring:message code="message.recipients" />: </b>
	<jstl:forEach items="${m.recipients }" var="actor">
		<jstl:out value="${actor.email},"></jstl:out>
	</jstl:forEach>
</p>
<p>
	<b><spring:message code="message.subject" />: </b>
	<jstl:out value="${m.subject }" />

</p>
<p>
	<b><spring:message code="message.body" />: </b>
	<jstl:out value="${m.body }" />
</p>
<p>
	<b><spring:message code="message.date" />: </b>
	<jstl:out value="${m.date }" />
</p>
<p>
	<jstl:if test="${languaje == 'en' }">
		<b><spring:message code="message.topic" />: </b>
		<jstl:out value="${m.topic}" />
	</jstl:if>
	<jstl:if test="${languaje == 'es' }">
		<b><spring:message code="message.topic" />: </b>
		<jstl:out value="${m.topic}" />
	</jstl:if>
</p>





<br />


<input type="button" name="cancel"
	value="<spring:message code="message.return" />"
	onClick="javascript: window.location.replace('message/actor/list.do')" />


<br />