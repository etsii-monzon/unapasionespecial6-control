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
import domain.Author;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AuthorServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private AuthorService	authorService;


	// Tests ------------------------------------------------------------------

	// The following are fictitious test cases that are intended to check that 
	// JUnit works well in this project.  Just righ-click this class and run 
	// it using JUnit.

	/*
	 * Test comprobación registrar usuario no autenticado como Author
	 * Req Funcional: 11.5
	 */
	@Test
	public void testRegisterAndSaveAuthor() {
		super.authenticate(null);
		final Author res = this.authorService.create();

		res.getUserAccount().setUsername("usuario123");
		res.getUserAccount().setPassword("passs123");

		res.setName("Luis");
		res.setSurname("López Aguado");
		res.setEmail("raulfth@gmail.com");
		res.setAddress("MOnzón");
		res.setPhoneNumber("659 86 53 42");

		final Author result = this.authorService.save(res);
		Assert.isTrue(this.authorService.findAll().contains(result));

		super.unauthenticate();

	}

	/*
	 * Test comprobación edición datos personales usuario autenticado como Author
	 * Req Funcional: 12.2
	 */
	@Test
	public void testEditAndSaveAuthor() {
		super.authenticate("author1");
		final Author res = this.authorService.findByPrincipal();

		res.setAddress("Lorca");
		res.setPhoneNumber("");

		final Author result = this.authorService.save(res);
		Assert.isTrue(this.authorService.findAll().contains(result));
		Assert.isTrue(result.equals(res));

		super.unauthenticate();

	}
}
