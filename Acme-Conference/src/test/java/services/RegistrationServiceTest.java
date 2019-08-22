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

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Conference;
import domain.CreditCard;
import domain.Registration;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RegistrationServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private RegistrationService	registrationService;

	@Autowired
	private AuthorService		authorService;

	@Autowired
	private ConferenceService	conferenceService;


	// Tests ------------------------------------------------------------------

	// The following are fictitious test cases that are intended to check that 
	// JUnit works well in this project.  Just righ-click this class and run 
	// it using JUnit.

	/*
	 * Test comprobación un author lista sus registrations.
	 * Req Funcional: 13.3
	 */
	@Test
	public void testListRegistrations() {
		super.authenticate("author1");

		final Collection<Registration> res = this.authorService.findByPrincipal().getRegistrations();

		Assert.notNull(res);

		super.unauthenticate();

	}
	/*
	 * Test comprobación un author crea una registration.
	 * Req Funcional: 13.3
	 */
	@Test
	public void testCreateRegistration() {
		super.authenticate("author1");

		final Registration res = this.registrationService.create();
		Assert.notNull(res);
		final Conference conference = this.conferenceService.findOne(this.getEntityId("conference1"));
		final CreditCard creditCard = new CreditCard();
		creditCard.setBrandName("VISA");
		creditCard.setCvv(123);
		creditCard.setExpMonth(12);
		creditCard.setExpYear(23);
		creditCard.setHolderName("Juan");
		creditCard.setNumber("4033435225802678");
		res.setConference(conference);
		res.setCreditCard(creditCard);

		final Registration result = this.registrationService.save(res);

		Assert.notNull(result);
		Assert.isTrue(this.registrationService.findAll().contains(result));
		Assert.isTrue(this.authorService.findByPrincipal().getRegistrations().contains(result));

		super.unauthenticate();

	}

}
