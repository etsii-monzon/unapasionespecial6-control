
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

<h2>
	<spring:message code="activity.listPanel" />
</h2>

<display:table pagesize="5" class="displaytag" name="panels"
	requestURI="${requestURI}" id="row1">



	<!-- Action links -->


	<security:authorize access="hasRole('ADMIN')">
		<display:column titleKey="panel.show">
			<a href="activity/administrator/panel/edit.do?panelId=${row1.id}">
				<spring:message code="panel.show" />
			</a>
		</display:column>
	</security:authorize>

	<!-- Attributes -->


	<display:column property="title" titleKey="activity.title"
		sortable="false" />
	<display:column property="speakers" titleKey="activity.speakers"
		sortable="false">
		<jstl:forEach items="${row1.speakers}" var="y">
			<jstl:out value="${y}"></jstl:out>
		</jstl:forEach>
	</display:column>
	<display:column property="startMoment" titleKey="activity.startMoment"
		sortable="false" />
	<display:column property="endMoment" titleKey="activity.endMoment"
		sortable="false" />
	<display:column property="room" titleKey="activity.room"
		sortable="false" />
	<display:column property="summary" titleKey="activity.summary"
		sortable="false" />
	<display:column property="conference.title"
		titleKey="activity.conference" sortable="false" />
	<display:column property="optionalAttachments"
		titleKey="activity.optionalAttachments" sortable="false">
		<jstl:forEach items="${row1.optionalAttachments}" var="x">
			<jstl:out value="${x}"></jstl:out>
		</jstl:forEach>
	</display:column>
	<display:column titleKey="panel.comments">

		<a href="comment/panel/list.do?panelId=${row1.id}"> <spring:message
				code="panel.comments" />
		</a>

	</display:column>
	<security:authorize access="hasRole('ADMIN')">

		<display:column titleKey="activity.delete">
			<a href="activity/administrator/panel/delete.do?panelId=${row1.id}">
				<spring:message code="panel.delete" />
			</a>
		</display:column>
	</security:authorize>


</display:table>

<!--CREAR PANEL  -->
<security:authorize access="hasRole('ADMIN')">
	<div>
		<jstl:if test="${conference.startDate > fecha }">
			<button type="button"
				onclick="javascript: relativeRedir('activity/administrator/panel/create.do?conferenceId=${conferenceId}')">
				<spring:message code="panel.create" />
			</button>
		</jstl:if>
	</div>
</security:authorize>

<h2>
	<spring:message code="activity.listTutorial" />
</h2>

<display:table pagesize="5" class="displaytag" name="tutorials"
	requestURI="${requestURI}" id="row2">


	<!-- Action links -->

	<security:authorize access="hasRole('ADMIN')">
		<display:column titleKey="tutorial.show">
			<a
				href="activity/administrator/tutorial/edit.do?tutorialId=${row2.id}">
				<spring:message code="tutorial.show" />
			</a>
		</display:column>
	</security:authorize>

	<!-- Attributes -->


	<display:column property="title" titleKey="activity.title"
		sortable="false" />
	<display:column property="speakers" titleKey="activity.speakers"
		sortable="false">
		<jstl:forEach items="${row2.speakers}" var="y">
			<jstl:out value="${y}"></jstl:out>
		</jstl:forEach>
	</display:column>
	<display:column property="startMoment" titleKey="activity.startMoment"
		sortable="false" />
	<display:column property="endMoment" titleKey="activity.endMoment"
		sortable="false" />
	<display:column property="room" titleKey="activity.room"
		sortable="false" />
	<display:column property="summary" titleKey="activity.summary"
		sortable="false" />
	<display:column property="conference.title"
		titleKey="activity.conference" sortable="false" />
	<display:column property="optionalAttachments"
		titleKey="activity.optionalAttachments" sortable="false">
		<jstl:forEach items="${row2.optionalAttachments}" var="x">
			<jstl:out value="${x}"></jstl:out>
		</jstl:forEach>
	</display:column>

	<display:column titleKey="tutorial.sections">
		<security:authorize access="!hasRole('ADMIN')">

			<a href="section/list.do?tutorialId=${row2.id}"> <spring:message
					code="tutorial.sections" />
			</a>
		</security:authorize>
		<security:authorize access="hasRole('ADMIN')">

			<a href="section/administrator/list.do?tutorialId=${row2.id}"> <spring:message
					code="tutorial.sections" />
			</a>
		</security:authorize>
	</display:column>

	<display:column titleKey="panel.comments">

		<a href="comment/tutorial/list.do?tutorialId=${row2.id}"> <spring:message
				code="panel.comments" />
		</a>

	</display:column>
	<security:authorize access="hasRole('ADMIN')">
		<display:column titleKey="activity.delete">
			<a
				href="activity/administrator/tutorial/delete.do?tutorialId=${row2.id}">
				<spring:message code="tutorial.delete" />
			</a>
		</display:column>
	</security:authorize>
</display:table>

<!--CREAR TUTORIAL  -->
<security:authorize access="hasRole('ADMIN')">
	<div>
		<jstl:if test="${conference.startDate > fecha }">
			<button type="button"
				onclick="javascript: relativeRedir('activity/administrator/tutorial/create.do?conferenceId=${conferenceId}')">
				<spring:message code="tutorial.create" />
			</button>
		</jstl:if>
	</div>
</security:authorize>

<h2>
	<spring:message code="activity.listPresentation" />
</h2>


<display:table pagesize="5" class="displaytag" name="presentations"
	requestURI="${requestURI}" id="row3">



	<!-- Action links -->


	<security:authorize access="hasRole('ADMIN')">
		<display:column titleKey="presentation.show">
			<a
				href="activity/administrator/presentation/edit.do?presentationId=${row3.id}">
				<spring:message code="presentation.show" />
			</a>
		</display:column>
	</security:authorize>

	<!-- Attributes -->


	<display:column property="title" titleKey="activity.title"
		sortable="false" />
	<display:column property="speakers" titleKey="activity.speakers"
		sortable="false">
		<jstl:forEach items="${row3.speakers}" var="y">
			<jstl:out value="${y}"></jstl:out>
		</jstl:forEach>
	</display:column>
	<display:column property="startMoment" titleKey="activity.startMoment"
		sortable="false" />
	<display:column property="endMoment" titleKey="activity.endMoment"
		sortable="false" />
	<display:column property="room" titleKey="activity.room"
		sortable="false" />
	<display:column property="summary" titleKey="activity.summary"
		sortable="false" />
	<display:column property="conference.title"
		titleKey="activity.conference" sortable="false" />
	<display:column property="optionalAttachments"
		titleKey="activity.optionalAttachments" sortable="false">
		<jstl:forEach items="${row3.optionalAttachments}" var="x">
			<jstl:out value="${x}"></jstl:out>
		</jstl:forEach>
	</display:column>
	<display:column titleKey="panel.comments">

		<a href="comment/presentation/list.do?presentationId=${row3.id}">
			<spring:message code="panel.comments" />
		</a>

	</display:column>

	<security:authorize access="hasRole('ADMIN')">


		<display:column titleKey="presentation.submission">
			<a
				href="activity/administrator/submission/show.do?submissionId=${row3.submission.id}">
				<spring:message code="presentation.subm" />
			</a>
		</display:column>

		<display:column titleKey="activity.delete">
			<a
				href="activity/administrator/presentation/delete.do?presentationId=${row3.id}">
				<spring:message code="presentation.delete" />
			</a>
		</display:column>
	</security:authorize>
</display:table>

<!--CREAR PRESENTATION  -->
<jstl:if test="${allowed }">
	<security:authorize access="hasRole('ADMIN')">
		<div>
			<jstl:if test="${conference.startDate > fecha }">
				<button type="button"
					onclick="javascript: relativeRedir('activity/administrator/presentation/create.do?conferenceId=${conferenceId}')">
					<spring:message code="presentation.create" />
				</button>
			</jstl:if>
		</div>
	</security:authorize>
</jstl:if>

<br />
<br />
<security:authorize access="hasRole('ADMIN')">
	<div>

		<button type="button"
			onclick="javascript: relativeRedir('conference/administrator/list.do')">
			<spring:message code="activity.return" />
		</button>
	</div>
</security:authorize>

<security:authorize access="!hasRole('ADMIN')">


	<div>

		<jstl:if test="${type=='PAST' }">
			<button type="button"
				onclick="javascript: relativeRedir('conference/listPast.do')">
				<spring:message code="activity.return" />
			</button>
		</jstl:if>
		<jstl:if test="${type=='RUNNING' }">
			<button type="button"
				onclick="javascript: relativeRedir('conference/listEjec.do')">
				<spring:message code="activity.return" />
			</button>
		</jstl:if>
		<jstl:if test="${type=='FUTURE' }">
			<button type="button"
				onclick="javascript: relativeRedir('conference/listProx.do')">
				<spring:message code="activity.return" />
			</button>
		</jstl:if>
	</div>

</security:authorize>


