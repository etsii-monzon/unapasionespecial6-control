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


<h3>
	<spring:message code="administrator.company" />
</h3>

<spring:message code="administrator.averageComp" />
=
<fmt:formatNumber value="${avgComp}" />

</br>

<spring:message code="administrator.minimumComp" />
=
<fmt:formatNumber value="${minComp}" />

</br>


<spring:message code="administrator.maximumComp" />
=
<fmt:formatNumber value="${maxComp}" />

</br>

<spring:message code="administrator.devtipComp" />
=
<fmt:formatNumber value="${devComp}" />
<br />


<h3>
	<spring:message code="administrator.companies" />
</h3>

<spring:message code="administrator.comps" />
=
<td><jstl:out value="${comps}" />

</br>

<h3>
	<spring:message code="administrator.hacker" />
</h3>

<spring:message code="administrator.averageHck" />
=
<fmt:formatNumber value="${avgHck}" />

</br>

<spring:message code="administrator.minimumHck" />
=
<fmt:formatNumber value="${minHck}" />

</br>


<spring:message code="administrator.maximumHck" />
=
<fmt:formatNumber value="${maxHck}" />

</br>

<spring:message code="administrator.devtipHck" />
=
<fmt:formatNumber value="${devHck}" />
<br />


<h3>
	<spring:message code="administrator.hackers" />
</h3>

<spring:message code="administrator.hckrs" />
=
<td><jstl:out value="${hckrs}" />

</br>


<h3>
	<spring:message code="administrator.position" />
</h3>

<spring:message code="administrator.averagePos"/>
=
<fmt:formatNumber value="${avgPos}" />

</br>

<spring:message code="administrator.minimumPos" />
=
<fmt:formatNumber value="${minPos}" />

</br>


<spring:message code="administrator.maximumPos" />
=
<fmt:formatNumber value="${maxPos}" />

</br>

<spring:message code="administrator.devtipPos" />
=
<fmt:formatNumber value="${devPos}" />
<br />


<h3>
	<spring:message code="administrator.positions" />
</h3>

<spring:message code="administrator.best" />
=
<td><jstl:out value="${best}" />

</br>

<spring:message code="administrator.worst" />
=
<td><jstl:out value="${worst}" />

</br>

<h3>
	<spring:message code="administrator.positionScores" />
</h3>

<spring:message code="administrator.avgScPos"/>
=
<fmt:formatNumber value="${avgScPos}" />

</br>

<spring:message code="administrator.minScPos" />
=
<fmt:formatNumber value="${minScPos}" />

</br>


<spring:message code="administrator.maxScPos" />
=
<fmt:formatNumber value="${maxScPos}" />

</br>

<spring:message code="administrator.stdScPos" />
=
<fmt:formatNumber value="${stdScPos}" />

</br>

<h3>
	<spring:message code="administrator.salaryPos"/>
</h3>

<spring:message code="administrator.salScPos" />
=
<fmt:formatNumber value="${salScPos}" />

</br>

<h3>
	<spring:message code="administrator.companyScores" />
</h3>

<spring:message code="administrator.avgScComp"/>
=
<fmt:formatNumber value="${avgScComp}" />

</br>

<spring:message code="administrator.minScComp" />
=
<fmt:formatNumber value="${minScComp}" />

</br>


<spring:message code="administrator.maxScComp" />
=
<fmt:formatNumber value="${maxScComp}" />

</br>

<spring:message code="administrator.stdScComp" />
=
<fmt:formatNumber value="${stdScComp}" />

<h3>
	<spring:message code="administrator.highestCompanies" />
</h3>

<spring:message code="administrator.highestPos" />
=
<td><jstl:out value="${highestPos}" />

</br>



<h3>
	<spring:message code="administrator.items" />
</h3>

<spring:message code="administrator.avgItemsProv"/>
=
<fmt:formatNumber value="${avgItemsProv}" />

</br>

<spring:message code="administrator.minItemsProv" />
=
<fmt:formatNumber value="${minItemsProv}" />

</br>


<spring:message code="administrator.maxItemsProv" />
=
<fmt:formatNumber value="${maxItemsProv}" />

</br>

<spring:message code="administrator.stdDevItemsProv" />
=
<fmt:formatNumber value="${stdDevItemsProv}" />

<h3>
	<spring:message code="administrator.top" />
</h3>

<spring:message code="administrator.topProv" />
=
<td><jstl:out value="${topProvs}" />

</br>



