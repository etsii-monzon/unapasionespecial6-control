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
import domain.Finder;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FinderServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private ActorService		actorService;


	// Tests ------------------------------------------------------------------

	// The following are fictitious test cases that are intended to check that 
	// JUnit works well in this project.  Just righ-click this class and run 
	// it using JUnit.

	/*
	 * Test comprobación hacer búsqueda como usuario autenticado
	 * Req Funcional: 24.1
	 */
	@Test
	public void testSearch() {
		super.authenticate("admin");

		final Finder res = this.actorService.findByPrincipal().getFinder();

		res.setKeyword("ETSII");

		final Finder result = this.finderService.save(res);

		final Collection<Conference> conferences = this.finderService.find(result).getConferences();

		Assert.notNull(conferences);
		Assert.isTrue(this.conferenceService.findAll().containsAll(conferences));

		super.unauthenticate();

	}
}
