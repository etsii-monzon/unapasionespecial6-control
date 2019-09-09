
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
	name="items" requestURI="${requestURI}" id="row">



	<!-- Action links -->
	<security:authorize access="hasRole('PROVIDER')">
		<display:column>

				<a href="item/provider/edit.do?itemId=${row.id}"> <spring:message
						code="item.edit" />
				</a>

		</display:column>
	</security:authorize>

	<!-- Attributes -->


	<display:column property="name" titleKey="item.name" sortable="true" />



	<display:column property="description" titleKey="item.description"
		sortable="false" />


	<display:column property="link" titleKey="item.link"
		sortable="false" />
		
		<display:column property="optionalPictures" titleKey="item.optionalPictures"
		sortable="false" />
		
		<security:authorize access="isAnonymous()">
		<display:column title="Show">
			<a href="provider/show.do?itemId=${row.id}"> <spring:message
					code="provider.show" />
			</a>
		</display:column>
	</security:authorize>


	<security:authorize access="hasRole('PROVIDER')">
		<display:column title="Show">
			<a href="item/provider/show.do?itemId=${row.id}"> <spring:message
					code="item.show" />
			</a>
		</display:column>
	</security:authorize>

</display:table>
<security:authorize access="hasRole('PROVIDER')">
	<div>
		<%-- 		<a href="position/company/create.do"> <spring:message
				code="position.create" /></a> --%>
		<button type="button"
			onclick="javascript: relativeRedir('item/provider/create.do')">
			<spring:message code="item.create" />
		</button>
	</div>
</security:authorize>