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
import domain.Category;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CategoryServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private CategoryService	categoryService;


	// Tests ------------------------------------------------------------------

	// The following are fictitious test cases that are intended to check that 
	// JUnit works well in this project.  Just righ-click this class and run 
	// it using JUnit.

	/*
	 * Test comprobación crear categories como Admin
	 * Req Funcional: 25.1
	 */
	@Test
	public void testCreateAndSaveCategory() {
		super.authenticate("admin");

		final Category res = this.categoryService.create();

		res.setEnglishTitle("TEST");
		res.setSpanishTitle("PRUEBA");
		res.setParent(this.categoryService.findOne(super.getEntityId("category1")));

		final Category result = this.categoryService.save(res);
		Assert.notNull(result);
		Assert.isTrue(this.categoryService.findAll().contains(result));

		super.unauthenticate();

	}

	/*
	 * Test comprobación editar categories como Admin
	 * Req Funcional: 25.1
	 */
	@Test
	public void testEditAndSaveCategory() {
		super.authenticate("admin");

		final Category res = this.categoryService.findOne(super.getEntityId("category2"));

		res.setEnglishTitle("TEST");
		res.setSpanishTitle("PRUEBA");

		final Category result = this.categoryService.save(res);
		Assert.notNull(result);
		Assert.isTrue(this.categoryService.findAll().contains(result));

		super.unauthenticate();

	}

	/*
	 * Test comprobación eliminar categories como Admin
	 * Req Funcional: 25.1
	 */
	@Test
	public void testDeleteCategory() {
		super.authenticate("admin");

		final Category res = this.categoryService.findOne(super.getEntityId("category2"));
		Assert.isTrue(this.categoryService.findAll().contains(res));

		this.categoryService.delete(res);
		Assert.isTrue(!this.categoryService.findAll().contains(res));

		super.unauthenticate();

	}

	/*
	 * Test comprobación listar categories como Admin
	 * Req Funcional: 25.1
	 */
	@Test
	public void testListCategory() {
		super.authenticate("admin");

		final Collection<Category> categories = this.categoryService.findAll();
		Assert.notNull(categories);

		super.unauthenticate();

	}

}
