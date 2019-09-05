
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

<!-- Listing grid -->

<!-- Hay que añadir el Search -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="problems" requestURI="${requestURI}" id="row">



	<!-- Action links -->
	<security:authorize access="hasRole('COMPANY')">
		<display:column>
			<jstl:if test="${row.draftMode=='true'}">

				<a href="problem/company/edit.do?problemId=${row.id}"> <spring:message
						code="problem.edit" />
				</a>

			</jstl:if>

		</display:column>
	</security:authorize>
	<!-- Attributes -->


	<display:column property="title" titleKey="problem.title"
		sortable="true" />



	<display:column property="statement" titleKey="problem.statement"
		sortable="false" />

	<display:column property="optionalHint" titleKey="problem.optionalHint"
		sortable="false" />

	<display:column property="attachments" titleKey="problem.attachments"
		sortable="false" />

	<display:column property="position.title" titleKey="problem.position"
		sortable="false" />



	<display:column property="draftMode" titleKey="problem.draftMode"
		sortable="false" />

	<security:authorize access="hasRole('COMPANY')">
		<display:column title="Show">
			<a href="problem/company/show.do?problemId=${row.id}"> <spring:message
					code="problem.show" />
			</a>
		</display:column>
		
		<display:column title="Delete">
				<jstl:if test="${row.draftMode=='true'}">
		
			<a href="problem/company/delete.do?problemId=${row.id}"> <spring:message
					code="problem.delete" />
			</a>
			</jstl:if>
		</display:column>
	</security:authorize>
	
	

</display:table>
<security:authorize access="hasRole('COMPANY')">
	<div>
		<a href="problem/company/create.do"> <spring:message
				code="problem.create" /></a>
	</div>
</security:authorize>