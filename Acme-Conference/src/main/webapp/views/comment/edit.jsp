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




<form:form action="comment/edit.do" modelAttribute="comment">
	<%-- Hidden properties from category--%>
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />

	<td><input type="hidden" name="commentEntityId"
		value="${commentEntityId}"></td>






	<%-- Title --%>
	<form:label path="title">
		<spring:message code="comment.title" />
	</form:label>
	<form:input path="title" />
	<form:errors class="error" path="title" />
	<br>
	<br>

	<%-- Author --%>
	<form:label path="author">
		<spring:message code="comment.author" />
	</form:label>
	<form:input path="author" />
	<form:errors class="error" path="author" />
	<br>
	<br>

	<%-- Text --%>
	<form:label path="text">
		<spring:message code="comment.text" />
	</form:label>
	<form:textarea path="text" />
	<form:errors class="error" path="text" />
	<br>
	<br>



	<%-- Buttons --%>

	<input type="submit" name="save"
		value="<spring:message code="comment.save"/>" />

	<jstl:if test="${requestURI=='comment/conference/edit.do' }">
		<input type="button" name="cancel"
			value="<spring:message code="comment.cancel" />"
			onClick="javascript: window.location.replace('comment/conference/list.do?conferenceId=${commentEntityId}')" />

	</jstl:if>
	<jstl:if test="${requestURI=='comment/tutorial/edit.do' }">
		<input type="button" name="cancel"
			value="<spring:message code="comment.cancel" />"
			onClick="javascript: window.location.replace('comment/tutorial/list.do?tutorialId=${commentEntityId}')" />

	</jstl:if>
	<jstl:if test="${requestURI=='comment/presentation/edit.do' }">
		<input type="button" name="cancel"
			value="<spring:message code="comment.cancel" />"
			onClick="javascript: window.location.replace('comment/presentation/list.do?presentationId=${commentEntityId}')" />

	</jstl:if>
	<jstl:if test="${requestURI=='comment/panel/edit.do' }">
		<input type="button" name="cancel"
			value="<spring:message code="comment.cancel" />"
			onClick="javascript: window.location.replace('comment/panel/list.do?panelId=${commentEntityId}')" />

	</jstl:if>

	<div></div>
	<br>
	<br>

</form:form>
