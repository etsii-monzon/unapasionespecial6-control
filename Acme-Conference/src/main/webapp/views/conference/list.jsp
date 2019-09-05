
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
	<security:authorize access="hasRole('ADMIN')">

		<button type="button"
			onclick="javascript: relativeRedir('conference/administrator/list.do')">
			<spring:message code="conference.list" />
		</button>
	</security:authorize>
	<security:authorize access="hasRole('ADMIN')">
		<button type="button"
			onclick="javascript: relativeRedir('conference/administrator/listsub.do')">
			<spring:message code="conference.submissionDeadlineList" />
		</button>

	</security:authorize>
	<security:authorize access="hasRole('ADMIN')">


		<button type="button"
			onclick="javascript: relativeRedir('conference/administrator/listnot.do')">
			<spring:message code="conference.notificationDeadlineList" />
		</button>

	</security:authorize>
	<security:authorize access="hasRole('ADMIN')">

		<button type="button"
			onclick="javascript: relativeRedir('conference/administrator/listcam.do')">
			<spring:message code="conference.cameraDeadlineList" />
		</button>
	</security:authorize>
	<security:authorize access="hasRole('ADMIN')">

		<button type="button"
			onclick="javascript: relativeRedir('conference/administrator/listst.do')">
			<spring:message code="conference.startList" />
		</button>
	</security:authorize>


</div>



<display:table pagesize="5" class="displaytag" name="conferences"
	requestURI="${requestURI}" id="row">



	<!-- Action links -->
	<security:authorize access="hasRole('ADMIN')">
		<display:column titleKey="conference.edit">
			<jstl:if test="${row.draftMode=='true'}">


				<a href="conference/administrator/edit.do?conferenceId=${row.id}">
					<spring:message code="conference.edit" />
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>
	<security:authorize access="hasRole('ADMIN')">
		<display:column titleKey="conference.show">
			<a href="conference/administrator/show.do?conferenceId=${row.id}">
				<spring:message code="conference.show" />
			</a>
		</display:column>
	</security:authorize>

	<!-- Attributes -->


	<display:column property="title" titleKey="conference.title"
		sortable="false" />

	<display:column property="acronym" titleKey="conference.acronym"
		sortable="false" />
	<display:column property="venue" titleKey="conference.venue"
		sortable="false" />
	<display:column property="summary" titleKey="conference.summary"
		sortable="false" />
	<jstl:if test="${languaje == 'en' }">
		<display:column property="category.englishTitle"
			titleKey="conference.category" sortable="true" />
	</jstl:if>
	<jstl:if test="${languaje == 'es' }">
		<display:column property="category.spanishTitle"
			titleKey="conference.category" sortable="true" />
	</jstl:if>
	<display:column property="submissionDeadline"
		titleKey="conference.submissionDeadline" sortable="false"
		format="{0,date,dd/MM/yyyy HH:mm}" />
	<display:column property="notificationDeadline"
		titleKey="conference.notificationDeadline" sortable="false"
		format="{0,date,dd/MM/yyyy HH:mm}" />
	<display:column property="cameraDeadline"
		titleKey="conference.cameraDeadline" sortable="false"
		format="{0,date,dd/MM/yyyy HH:mm}" />
	<display:column property="startDate" titleKey="conference.startDate"
		sortable="false" format="{0,date,dd/MM/yyyy HH:mm}" />
	<display:column property="endDate" titleKey="conference.endDate"
		sortable="false" format="{0,date,dd/MM/yyyy HH:mm}" />
	<display:column property="fee" titleKey="conference.fee"
		sortable="false" />

	<security:authorize access="hasRole('ADMIN')">

		<display:column property="draftMode" titleKey="conference.draftMode"
			sortable="false" />

		<display:column titleKey="conference.activities">
			<a href="activity/administrator/list.do?conferenceId=${row.id}">
				<spring:message code="conference.activities" />
			</a>
		</display:column>
	</security:authorize>

	<security:authorize access="!hasRole('ADMIN')">

		<display:column titleKey="conference.activities">
			<a href="activity/list.do?conferenceId=${row.id}"> <spring:message
					code="conference.activities" />
			</a>


		</display:column>
	</security:authorize>

	<display:column titleKey="conference.comments">

		<a href="comment/conference/list.do?conferenceId=${row.id}"> <spring:message
				code="conference.comments" />
		</a>

	</display:column>

	<display:column titleKey="conference.loots">

		<security:authorize access="!hasRole('ADMIN')">
			<a href="loot/list.do?conferenceId=${row.id}"> <spring:message
					code="conference.loots" />
			</a>
		</security:authorize>

		<security:authorize access="hasRole('ADMIN')">
			<a href="loot/administrator/list.do?conferenceId=${row.id}"> <spring:message
					code="conference.loots" />
			</a>
		</security:authorize>

	</display:column>
</display:table>
<security:authorize access="hasRole('ADMIN')">
	<div>

		<button type="button"
			onclick="javascript: relativeRedir('conference/administrator/create.do')">
			<spring:message code="conference.create" />
		</button>
	</div>
</security:authorize>




<jstl:if
	test="${requestURI=='conference/searchList.do' || requestURI == 'conference/actor/listByCategory.do'}">
	<input type="button" name="cancel"
		value="<spring:message code="conference.return" />"
		onClick="javascript: window.location.replace('conference/search.do');" />
</jstl:if>

<jstl:if
	test="${requestURI!='conference/searchList.do' && requestURI != 'conference/actor/listByCategory.do'&& requestURI!='finder/actor/list.do'}">
	<input type="button" name="cancel"
		value="<spring:message code="conference.return" />"
		onClick="javascript: window.location.replace('welcome/index.do');" />
</jstl:if>

<jstl:if test="${requestURI=='finder/actor/list.do'}">
	<input type="button" name="cancel"
		value="<spring:message code="conference.return" />"
		onClick="javascript: window.location.replace('finder/actor/edit.do');" />
</jstl:if>
