<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="${bannerURL}"
		alt="Acme-Rookie Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<!-- Crear nuevos administardores  -->
					<li><a href="administrator/administrator/create.do"><spring:message
								code="master.page.administrator.register" /></a></li>
					<li><a href="auditor/administrator/create.do"><spring:message
								code="master.page.auditor.register" /></a></li>
					<li><a href="configuration/administrator/edit.do"><spring:message
								code="master.page.configuration.edit" /></a></li>
					<li><a href="administrator/administrator/list.do"><spring:message
								code="master.page.administrator.dashboard" /></a></li>
					<li><a href="administrator/administrator/compute.do"><spring:message
								code="master.page.administrator.score" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('COMPANY')">
			<li><a class="fNiv"><spring:message
						code="master.page.company" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="position/company/list.do"><spring:message
								code="master.page.company.position" /></a></li>
					<li><a href="problem/company/list.do"><spring:message
								code="master.page.company.problem" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('AUDITOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.auditor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="audit/auditor/list.do"><spring:message
								code="master.page.company.audit" /></a></li>

				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('PROVIDER')">
			<li><a class="fNiv"><spring:message
						code="master.page.provider" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="item/provider/list.do"><spring:message
								code="master.page.provider.item" /></a></li>

				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('ROOKIE')">
			<li><a class="fNiv" href="application/rookie/list/all.do"><spring:message
						code="master.page.applications" /></a>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="item/list.do"><spring:message
						code="master.page.item" /></a></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="provider/list.do"><spring:message
						code="master.page.provider" /></a></li>
		</security:authorize>

		<security:authorize access="permitAll">
			<li><a class="fNiv" href="company/list.do"><spring:message
						code="master.page.company.list" /></a></li>

			<li><a class="fNiv" href="position/list.do"><spring:message
						code="master.page.position.list" /></a></li>

		</security:authorize>
		<security:authorize access="isAnonymous()">

			<li><a class="fNiv"><spring:message
						code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>

					<!-- Registrarse como company -->

					<li><a href="company/create.do"><spring:message
								code="master.page.company.register" /></a></li>

					<!-- Registrarse como Hacker -->

					<li><a href="rookie/create.do"><spring:message
								code="master.page.hacker.register" /></a></li>

					<li><a href="provider/create.do"><spring:message
								code="master.page.provider.register" /></a></li>
				</ul></li>
		</security:authorize>


		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('ADMIN')">
						<li><a href="administrator/administrator/edit.do"><spring:message
									code="master.page.administrator.edit" /></a></li>
						<li><a href="administrator/administrator/data.do"><spring:message
									code="master.page.administrator.data" /></a></li>
						<li><a onclick="return confirm('Are you sure?')"
							href="administrator/administrator/deleteAccount.do"><spring:message
									code="master.page.administrator.delete" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('COMPANY')">
						<li><a href="company/company/edit.do"><spring:message
									code="master.page.company.edit" /></a></li>
						<li><a href="company/company/data.do"><spring:message
									code="master.page.company.data" /></a></li>
						<li><a onclick="return confirm('Are you sure?')"
							href="company/company/deleteAccount.do"><spring:message
									code="master.page.company.delete" /></a></li>
					</security:authorize>

					<security:authorize access="hasRole('ROOKIE')">
						<li><a href="rookie/rookie/edit.do"><spring:message
									code="master.page.hacker.edit" /></a></li>
						<li><a href="rookie/rookie/data.do"><spring:message
									code="master.page.hacker.data" /></a></li>
						<li><a onclick="return confirm('Are you sure?')"
							href="rookie/rookie/deleteAccount.do"><spring:message
									code="master.page.hacker.delete" /></a></li>
					</security:authorize>
					
					<security:authorize access="hasRole('AUDITOR')">
						<li><a href="auditor/auditor/edit.do"><spring:message
									code="master.page.auditor.edit" /></a></li>
						<li><a href="auditor/auditor/data.do"><spring:message
									code="master.page.auditor.data" /></a></li>
						<li><a onclick="return confirm('Are you sure?')"
							href="auditor/auditor/deleteAccount.do"><spring:message
									code="master.page.auditor.delete" /></a></li>
					</security:authorize>
					
					<security:authorize access="hasRole('PROVIDER')">
						<li><a href="provider/provider/edit.do"><spring:message
									code="master.page.provider.edit" /></a></li>
						<li><a href="provider/provider/data.do"><spring:message
									code="master.page.provider.data" /></a></li>
						<li><a onclick="return confirm('Are you sure?')"
							href="provider/provider/deleteAccount.do"><spring:message
									code="master.page.provider.delete" /></a></li>
					</security:authorize>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>


