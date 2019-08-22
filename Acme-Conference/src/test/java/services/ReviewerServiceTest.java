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

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Reviewer;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ReviewerServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ReviewerService	reviewerService;


	// Tests ------------------------------------------------------------------

	// The following are fictitious test cases that are intended to check that 
	// JUnit works well in this project.  Just righ-click this class and run 
	// it using JUnit.

	/*
	 * Test comprobación registrar usuario no autenticado como Reviewer
	 * Req Funcional: 11.6
	 */
	@Test
	public void testRegisterAndSaveReviewer() {
		super.authenticate(null);
		final Reviewer res = this.reviewerService.create();

		res.getUserAccount().setUsername("usuarioR");
		res.getUserAccount().setPassword("passs123");

		res.setName("Jose");
		res.setSurname("Román Aguado");
		res.setEmail("jose145@gmail.com");
		res.setAddress("Paseo de la Conferencia");
		res.setPhoneNumber("659865342");
		final Collection<String> keywords = new ArrayList<>();
		keywords.add("Hacking");
		keywords.add("Java");
		res.setKeywords(keywords);

		final Reviewer result = this.reviewerService.save(res);
		Assert.isTrue(this.reviewerService.findAll().contains(result));

		super.unauthenticate();

	}

	/*
	 * Test comprobación edición datos personales usuario autenticado como Reviewer
	 * Req Funcional: 12.2
	 */
	@Test
	public void testEditAndSaveReviewer() {
		super.authenticate("reviewer1");
		final Reviewer res = this.reviewerService.findByPrincipal();

		res.setAddress("Avd.Reina Mercedes");
		res.setPhoneNumber("");

		final Reviewer result = this.reviewerService.save(res);
		Assert.isTrue(this.reviewerService.findAll().contains(result));
		Assert.isTrue(result.equals(res));

		super.unauthenticate();

	}
}
