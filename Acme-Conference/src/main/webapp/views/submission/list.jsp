
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

<%-- <jsp:useBean id="now" class="java.util.Date" />
 --%>
<!-- Listing grid -->

<script type="text/javascript">
	function confirmNotify() {

		return confirm("<spring:message code="submission.confirm"/>");

	}

	function confirmAssign() {

		res = confirm("<spring:message code="submission.assign.conf"/>");
		if (res == true) {
			javascript: relativeRedir('submission/administrator/assignReviewers.do')
		}

	}
</script>


<display:table pagesize="5" class="displaytag" name="submissions"
	requestURI="${requestURI}" id="row">



	<!-- Action links -->
	<security:authorize access="hasRole('AUTHOR')">
		<display:column titleKey="submission.show">
			<a href="submission/author/show.do?submissionId=${row.id}"> <spring:message
					code="submission.show" />
			</a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
		<display:column titleKey="submission.show">
			<a href="submission/administrator/show.do?submissionId=${row.id}">
				<spring:message code="submission.show" />
			</a>
		</display:column>
	</security:authorize>
	<security:authorize access="hasRole('AUTHOR')">
		<display:column titleKey="submission.edit">

			<jstl:if test="${row.status=='ACCEPTED'}">
				<jstl:if test="${row.conference.cameraDeadline > fecha }">
					<jstl:if test="${!row.cameraReady }">
						<a href="submission/author/upload.do?submissionId=${row.id}">
							<spring:message code="submission.upload" />
						</a>
					</jstl:if>
				</jstl:if>
			</jstl:if>
			<jstl:if test="${row.cameraReady}">
				<spring:message code="submission.uploaded" />
			</jstl:if>
		</display:column>
	</security:authorize>

	<!-- Attributes -->


	<display:column property="conference.title"
		titleKey="submission.conference" sortable="false" />
	<security:authorize access="hasRole('REVIEWER')">
		<display:column property="conference.summary"
			titleKey="submission.conference.summary" sortable="false" />
	</security:authorize>
	<display:column property="ticker" titleKey="submission.ticker"
		sortable="false" />
	<display:column property="moment" titleKey="submission.moment"
		sortable="false" format="{0,date,dd/MM/yyyy HH:mm}" />
	<display:column property="status" titleKey="submission.status"
		sortable="true" />


	<security:authorize access="hasRole('ADMIN')">
		<display:column titleKey="submission.reports">
			<a href="report/administrator/list.do?submissionId=${row.id}"> <spring:message
					code="reports.show" />
			</a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('AUTHOR')">
		<display:column titleKey="submission.reports">
			<jstl:if test="${row.status != 'UNDER-REVIEW' }">
				<a href="report/author/list.do?submissionId=${row.id}"> <spring:message
						code="reports.show" />
				</a>
			</jstl:if>

		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
		<display:column titleKey="submission.decission">
			<jstl:if
				test="${row.status == 'UNDER-REVIEW' && row.conference.submissionDeadline < now && row.conference.notificationDeadline > now}">
				<a onclick="return confirmNotify()"
					href="submission/administrator/decisionMaking.do?submissionId=${row.id}">
					<spring:message code="submission.decision" />
				</a>

			</jstl:if>

		</display:column>
	</security:authorize>
	</br>

	<security:authorize access="hasRole('AUTHOR')">
		<display:column titleKey="submission.paper">
			<a href="paper/author/showp.do?submissionId=${row.id}"> <spring:message
					code="paper.show" />
			</a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('REVIEWER')">
		<display:column titleKey="submission.paper">
			<a href="paper/reviewer/showp.do?submissionId=${row.id}"> <spring:message
					code="paper.show" />
			</a>
		</display:column>
	</security:authorize>
	</br>

	<security:authorize access="hasRole('REVIEWER')">

		<display:column titleKey="report.create">
			<a href="report/reviewer/create.do?subId=${row.id}"> <spring:message
					code="report.create" />
			</a>
		</display:column>


	</security:authorize>

	</br>

	<security:authorize access="hasRole('ADMIN')">
		<display:column titleKey="submission.assign">
			<jstl:if
				test="${row.status == 'UNDER-REVIEW' && row.conference.submissionDeadline < now && row.conference.notificationDeadline > now}">
				<jstl:if test="${row.reviewers.isEmpty()}">
					<a href="submission/administrator/assign.do?submissionId=${row.id}">
						<spring:message code="submission.assign" />
					</a>
				</jstl:if>
			</jstl:if>
		</display:column>
	</security:authorize>


</display:table>

<jstl:if test="${not empty submissions }">
	<security:authorize access="hasRole('ADMIN')">
		<div>

			<button type="button" onclick="return confirmAssign();">
				<spring:message code="submission.assignAuto" />
			</button>
		</div>
	</security:authorize>
</jstl:if>
<security:authorize access="hasRole('AUTHOR')">
	<div>

		<button type="button"
			onclick="javascript: relativeRedir('submission/author/create.do')">
			<spring:message code="submission.create" />
		</button>
	</div>
</security:authorize>
<br />
<input type="button" name="cancel"
	value="<spring:message code="submission.return" />"
	onClick="javascript: window.location.replace('welcome/index.do')" />