/*
 * SampleTest.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Administrator;
import domain.Author;
import domain.Message;
import domain.Submission;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private SubmissionService		submissionService;

	@Autowired
	private AuthorService			authorService;

	@Autowired
	private RegistrationService		regService;

	@Autowired
	private ConferenceService		confService;


	// Tests ------------------------------------------------------------------

	// The following are fictitious test cases that are intended to check that 
	// JUnit works well in this project.  Just righ-click this class and run 
	// it using JUnit.

	/*
	 * Test comprobación edición datos personales usuario autenticado como Admin
	 * Req Funcional: 12.2
	 */
	@Test
	public void testEditAndSaveAdministrator() {
		super.authenticate("admin");
		final Administrator res = this.administratorService.findByPrincipal();

		res.setName("Rodrigo");

		final Administrator result = this.administratorService.save(res);
		Assert.isTrue(this.administratorService.findAll().contains(result));
		Assert.isTrue(result.equals(res));

		super.unauthenticate();

	}

	/*
	 * Test comprobación notificación recibida por author de decisión de su submission
	 * Req Funcional: 14.5
	 */
	@Test
	public void testNotificationProcedure() {
		super.authenticate("admin");

		final Submission s = this.submissionService.findOne(super.getEntityId("submission1"));
		this.messageService.notificationDecision(s);

		final Author recipient = this.authorService.findAuthorBySubmissionId(s.getId());

		for (final Message m : recipient.getMessages())
			if (m.getTopic() == "RESOLUTION") {
				Assert.isTrue(recipient.getMessages().contains(m));
				break;
			}

		super.unauthenticate();

	}

	/*
	 * Test comprobación un admin muestra su dashboard.
	 * Req Funcional: 14.8
	 */
	@Test
	public void testDashboard() {
		super.authenticate("admin");

		Assert.notNull(this.submissionService.avgSubmissionsPerConference());
		Assert.notNull(this.submissionService.minSubmissionsPerConference());
		Assert.notNull(this.submissionService.maxSubmissionsPerConference());
		Assert.notNull(this.submissionService.stdDevSubmissionsPerConference());
		Assert.notNull(this.regService.avgRegistrationsPerConference());
		Assert.notNull(this.regService.maxRegistrationsPerConference());
		Assert.notNull(this.regService.minRegistrationsPerConference());
		Assert.notNull(this.regService.stdDevRegistrationsPerConference());
		Assert.notNull(this.confService.avgConferenceFees());
		Assert.notNull(this.confService.maxConferenceFees());
		Assert.notNull(this.confService.minConferenceFees());
		Assert.notNull(this.confService.stdConferenceFees());
		Assert.notNull(this.confService.avgDaysPerConference());
		Assert.notNull(this.confService.maxDaysPerConference());
		Assert.notNull(this.confService.minDaysPerConference());
		Assert.notNull(this.confService.stdDevDaysPerConference());
		super.unauthenticate();

	}
}
