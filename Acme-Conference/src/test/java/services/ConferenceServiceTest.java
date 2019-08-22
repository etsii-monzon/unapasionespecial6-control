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
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;
import domain.Conference;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConferenceServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ConferenceService		conferenceService;
	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private CategoryService			categoryService;


	// Tests ------------------------------------------------------------------

	// The following are fictitious test cases that are intended to check that 
	// JUnit works well in this project.  Just righ-click this class and run 
	// it using JUnit.

	/*
	 * Test comprobación búsqueda de conferencia introduciendo una palabra clave
	 * Req Funcional: 11.4
	 */
	@Test
	public void testSearchConferenceByKeyword() {
		super.authenticate(null);

		final String keyword = "ETSII";

		final Collection<Conference> res = this.conferenceService.searchConferenceByKeyword(keyword);

		Assert.notNull(res);
		Assert.isTrue(this.conferenceService.findAll().containsAll(res));

		super.unauthenticate();

	}
	/*
	 * Test comprobación crear una conference.
	 * Req Funcional: 14.2
	 */
	@Test
	public void testCreateConference() {
		super.authenticate("admin");

		final Conference res = this.conferenceService.create();
		Assert.notNull(res);

		res.setSubmissionDeadline(new Date());
		res.setNotificationDeadline(new Date());
		res.setCameraDeadline(new Date());
		res.setStartDate(new Date());
		res.setEndDate(new Date());
		res.setAcronym("aeiou");
		res.setDraftMode(false);
		res.setFee(125.);
		res.setSummary("sum");
		res.setTitle("hola");
		res.setVenue("hola");
		res.setCategory(this.categoryService.findOne(super.getEntityId("category1")));

		final Conference result = this.conferenceService.save(res);

		Assert.notNull(result);
		Assert.isTrue(this.conferenceService.findAll().contains(result));
		Assert.isTrue(this.adminService.findByPrincipal().getConferences().contains(result));

		super.unauthenticate();

	}

	/*
	 * Test comprobación un admin lista sus conferences.
	 * Req Funcional: 14.1
	 */
	@Test
	public void testListConferences() {
		super.authenticate("admin");

		final Collection<Conference> res = this.adminService.findByPrincipal().getConferences();

		Assert.notNull(res);

		super.unauthenticate();

	}

	/*
	 * Test comprobación listar conferences por categoría por usuario autenticado
	 * Req Funcional: 23.1
	 */
	@Test
	public void testListConferenceByCategory() {
		super.authenticate("admin");

		final Category c = this.categoryService.findOne(super.getEntityId("category2"));

		final Collection<Conference> conferences = this.conferenceService.getConferencesByCategory(c);
		Assert.notNull(conferences);

		super.unauthenticate();

	}

}
