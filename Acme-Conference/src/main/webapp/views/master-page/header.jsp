<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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
	<a href="#"><img src="${bannerURL}" alt="Acme Conference Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="configuration/administrator/show.do"><spring:message
								code="master.page.configuration.edit" /></a></li>
					<li><a href="conference/administrator/list.do"><spring:message
								code="master.page.conference.list" /></a></li>
					<li><a href="submission/administrator/list.do"><spring:message
								code="master.page.submissionad.list" /></a></li>
					<li><a href="category/administrator/list.do"><spring:message
								code="master.page.categories" /></a></li>
					<li><a href="administrator/administrator/list.do"><spring:message
								code="master.page.dashboard" /></a></li>

					<%-- 	<li><a href="conference/administrator/listst.do"><spring:message
								code="master.page.conferencest.list" /></a></li>
					<li><a href="conference/administrator/listnot.do"><spring:message
								code="master.page.conferencestnot.list" /></a></li>
					<li><a href="conference/administrator/listcam.do"><spring:message
								code="master.page.conferencestcam.list" /></a></li>
								<li><a href="conference/administrator/listsub.do"><spring:message
								code="master.page.conferencestcam.list" /></a></li> --%>


				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('AUTHOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.author" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="registration/author/list.do"><spring:message
								code="master.page.registration.list" /></a></li>
					<li><a href="submission/author/list.do"><spring:message
								code="master.page.submission.list" /></a></li>
				</ul></li>

		</security:authorize>

		<security:authorize access="hasRole('REVIEWER')">
			<li><a class="fNiv"><spring:message
						code="master.page.reviewer" /></a>
				<ul>
					<li class="arrow"></li>

					<li><a href="submission/reviewer/list.do"><spring:message
								code="master.page.submissionre.list" /></a></li>
				</ul></li>

		</security:authorize>
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
		</security:authorize>

		<security:authorize access="permitAll">
			<li><a class="fNiv"><spring:message
						code="master.page.conferences" /></a>
				<ul>
					<li class="arrow"></li>

					<li><a href="conference/search.do"><spring:message
								code="master.page.conference.search" /></a></li>

					<li><a href="conference/listProx.do"><spring:message
								code="master.page.conference.prox" /></a></li>

					<li><a href="conference/listPast.do"><spring:message
								code="master.page.conference.past" /></a></li>

					<li><a href="conference/listEjec.do"><spring:message
								code="master.page.conference.ejec" /></a></li>
				</ul></li>

		</security:authorize>


		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"><spring:message
						code="master.page.message" /></a>
				<ul>
					<li class="arrow"></li>

					<li><a href="message/actor/list.do"><spring:message
								code="master.page.message.list" /></a></li>
					<security:authorize access="hasRole('ADMIN')">
						<li><a href="message/administrator/broadcast.do"><spring:message
									code="master.page.message.allActors" /></a></li>
						<li><a href="message/administrator/messageAuthors.do"><spring:message
									code="master.page.message.allAuthors" /></a></li>
						<li><a href="message/administrator/messageAuthorsSub.do"><spring:message
									code="master.page.message.allAuthorsSub" /></a></li>
						<li><a
							href="message/administrator/messageAuthorsRegistration.do"><spring:message
									code="master.page.message.allAuthorsRegister" /></a></li>
					</security:authorize>
				</ul></li>
		</security:authorize>
		<security:authorize access="isAnonymous()">

			<li><a class="fNiv"><spring:message
						code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>

					<!-- Registrarse como author -->

					<li><a href="author/create.do"><spring:message
								code="master.page.author.register" /></a></li>

					<!-- Registrarse como reviewer -->

					<li><a href="reviewer/create.do"><spring:message
								code="master.page.reviewer.register" /></a></li>



				</ul></li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="finder/actor/edit.do"><spring:message
						code="master.page.finder" /></a></li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('AUTHOR')">
						<li><a href="author/author/edit.do"><spring:message
									code="master.page.author.edit" /></a></li>
					</security:authorize>

					<security:authorize access="hasRole('ADMIN')">
						<li><a href="administrator/administrator/edit.do"><spring:message
									code="master.page.admin.edit" /></a></li>
					</security:authorize>

					<security:authorize access="hasRole('REVIEWER')">
						<li><a href="reviewer/reviewer/edit.do"><spring:message
									code="master.page.reviewer.edit" /></a></li>
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

