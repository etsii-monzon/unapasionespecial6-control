
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

<!-- Hay que a�adir el Search -->


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="audits" requestURI="${requestURI}" id="row">



	<!-- Action links -->
	<security:authorize access="hasRole('AUDITOR')">
		<display:column>
			<jstl:if test="${row.draftMode=='true'}">

				<a href="audit/auditor/edit.do?auditId=${row.id}"> <spring:message
						code="audit.edit" />
				</a>
			</jstl:if>

		</display:column>
	</security:authorize>

	<!-- Attributes -->


	<display:column property="text" titleKey="audit.text" sortable="true" />



	<display:column property="score" titleKey="audit.score"
		sortable="false" />

	<display:column property="moment" titleKey="audit.moment"
		sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />


	<display:column property="draftMode" titleKey="audit.draftMode"
		sortable="false" />
	<display:column property="position.title" titleKey="position.title"
		sortable="false" />


	<security:authorize access="hasRole('AUDITOR')">
		<display:column title="Show">
			<a href="audit/auditor/show.do?auditId=${row.id}"> <spring:message
					code="audit.show" />
			</a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('AUDITOR')">
		<display:column titleKey="audit.quolets">
			<a href="netcashe/auditor/list.do?auditId=${row.id}"> <spring:message
					code="audit.quolets" />
			</a>
		</display:column>
	</security:authorize>


	<security:authorize access="hasRole('COMPANY')">
		<display:column titleKey="audit.quolets">
			<a href="netcashe/company/list.do?auditId=${row.id}"> <spring:message
					code="audit.quolets" />
			</a>
		</display:column>
	</security:authorize>

</display:table>
<security:authorize access="hasRole('AUDITOR')">
	<div>
		<%-- 		<a href="position/company/create.do"> <spring:message
				code="position.create" /></a> --%>
		<button type="button"
			onclick="javascript: relativeRedir('audit/auditor/create.do')">
			<spring:message code="audit.create" />
		</button>
	</div>
</security:authorize>

<security:authorize access="hasRole('COMPANY')">
	<button type="button"
		onclick="javascript: relativeRedir('position/company/list.do')">
		<spring:message code="audit.return" />
	</button>
</security:authorize>