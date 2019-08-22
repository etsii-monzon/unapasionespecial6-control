
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
		onclick="javascript: relativeRedir('message/actor/send.do')">
		<spring:message code="message.create" />
	</button>

</div>

<display:table pagesize="5" class="displaytag" name="messages"
	requestURI="${requestURI}" id="row">



	<!-- Action links -->



	<!-- Attributes -->

	<!-- Email -->
	<display:column titleKey="message.sender" sortable="true">
		<jstl:out value="${row.sender.email}"></jstl:out>
	</display:column>

	<!-- Subject -->
	<display:column property="subject" titleKey="message.subject" />

	<!-- Recipients -->
	<display:column titleKey="message.recipients" sortable="true">
		<jstl:forEach items="${row.recipients }" var="x">
			<jstl:out value="${x.email}"></jstl:out>
		</jstl:forEach>
	</display:column>

	<!-- Topic -->
	<display:column property="topic" titleKey="message.topic"
		sortable="true" />


	<!-- Date -->
	<display:column property="date" titleKey="message.date" sortable="true"
		format="{0,date,dd/MM/yyyy HH:mm}" />

	<display:column titleKey="message.show">
		<a href="message/actor/show.do?messageId=${row.id}"> <spring:message
				code="message.show" />
		</a>
	</display:column>

	<display:column titleKey="message.delete">
		<a href="message/actor/delete.do?messageId=${row.id}"> <spring:message
				code="message.delete" />
		</a>
	</display:column>





</display:table>
<br />
<input type="button" name="cancel"
	value="<spring:message code="message.return" />"
	onClick="javascript: window.location.replace('welcome/index.do');" />



