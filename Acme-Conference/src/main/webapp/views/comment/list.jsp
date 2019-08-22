
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
		onclick="javascript: relativeRedir('comment/create.do?commentEntityId=${commentEntityId}')">
		<spring:message code="comment.create" />
	</button>

</div>
<display:table pagesize="5" class="displaytag" name="comments"
	requestURI="${requestURI}" id="row">



	<!-- Action links -->

	<!-- Title -->
	<display:column property="title" titleKey="comment.title" />

	<!-- Moment -->
	<display:column property="moment" titleKey="comment.moment" />

	<!-- Author -->
	<display:column property="author" titleKey="comment.author" />

	<!-- Text -->
	<display:column property="text" titleKey="comment.text" />

	<!-- Attributes -->



</display:table>
<br />

<jstl:if test="${requestURI=='comment/conference/list.do' }">
	<security:authorize access="!hasRole('ADMIN')">
		<jstl:if test="${type == 'PAST' }">
			<input type="button" name="cancel"
				value="<spring:message code="comment.return" />"
				onClick="javascript: window.location.replace('conference/listPast.do')" />
		</jstl:if>
		<jstl:if test="${type == 'FUTURE' }">
			<input type="button" name="cancel"
				value="<spring:message code="comment.return" />"
				onClick="javascript: window.location.replace('conference/listProx.do')" />
		</jstl:if>
		<jstl:if test="${type == 'RUNNING' }">
			<input type="button" name="cancel"
				value="<spring:message code="comment.return" />"
				onClick="javascript: window.location.replace('conference/listEjec.do')" />
		</jstl:if>
	</security:authorize>
	<security:authorize access="hasRole('ADMIN')">
		<input type="button" name="cancel"
			value="<spring:message code="comment.return" />"
			onClick="javascript: window.location.replace('conference/administrator/list.do')" />
	</security:authorize>

</jstl:if>
<jstl:if test="${requestURI!='comment/conference/list.do'  }">
	<security:authorize access="!hasRole('ADMIN')">
		<input type="button" name="cancel"
			value="<spring:message code="comment.return" />"
			onClick="javascript: window.location.replace('activity/list.do?conferenceId=${conferenceId}')" />
	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
		<input type="button" name="cancel"
			value="<spring:message code="comment.return" />"
			onClick="javascript: window.location.replace('activity/administrator/list.do?conferenceId=${conferenceId}')" />
	</security:authorize>
</jstl:if>

