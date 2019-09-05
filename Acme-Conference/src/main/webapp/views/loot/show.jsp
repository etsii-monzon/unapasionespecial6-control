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
	<b><spring:message code="loot.publicationMoment" />: </b>
	<jstl:out value="${loot.publicationMoment }" />
</p>

<p>
	<b><spring:message code="loot.body" />: </b>
	<jstl:out value="${loot.body }" />
</p>
<p>
	<b><spring:message code="loot.optionalPicture" />: </b>
	<jstl:out value="${loot.optionalPicture }" />
</p>
<p>
	<b><spring:message code="loot.ticker" />: </b>
		<jstl:out value="${loot.ticker }" />
	
</p>


<br />


<input type="button" name="cancel"
	value="<spring:message code="loot.return" />"
	onClick="javascript: window.location.replace('welcome/index.do');" />