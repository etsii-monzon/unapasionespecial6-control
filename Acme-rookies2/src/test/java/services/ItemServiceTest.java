
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.ItemRepository;
import utilities.AbstractTest;
import domain.Item;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ItemServiceTest extends AbstractTest {

	//SYSTEM UNDER TEST

	@Autowired
	private ItemService		itemService;
	@Autowired
	private ItemRepository	itemRepository;


	//Requirement 9.1 An actor who is authenticated as a provider must be able to delete its items

	@Test
	public void deleteItemDriver() {

		final Object testingData[][] = {

			{ //User not authenticated try to delete a item-- DOESN'T WORK
				null, "item1", IllegalArgumentException.class
			}, {//User authenticated as a provider  delete a item-- WORK

				"provider", "item1", null
			}, {//User authenticated as a hacker try to delete a item-- DOESN'T WORK9

				"rookie", "item1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)

			this.deleteItemTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void deleteItemTemplate(final String user, final String audit, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);
			final Item lr = this.itemService.findOne(super.getEntityId(audit));

			this.itemService.delete(lr);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}
		System.out.println("Se esperaba " + expected + " y es " + caught);

		this.checkExceptions(expected, caught);
	}
	//Analysis of sentence coverage: 10/10
	//Analysis of data coverage: 3/3

	//Requirement 9.1 An actor who is authenticated as a provider must be able to create its items

	@Test
	public void createAndSaveItemDriver() {

		final Object testingData[][] = {

			{ //User authenticated as a provider create a item-- WORK

				"provider", "name1", "description1", "https://hola.com", null
			}, { //User authenticated as a rookie tries to create a item-- DOESN'T WORK

				"rookie", "name1", "description1", "https://hola.com", IllegalArgumentException.class

			}, { //User authenticated as a provider tries to create a item with a blank name--DOESN'T WORK

				"provider", "", "description1", "https://hola.com", ConstraintViolationException.class
			}, { //User authenticated as a provider tries to create a item with a blank description--DOESN'T WORK

				"provider", "name1", "", "https://hola.com", ConstraintViolationException.class
			}, { //User authenticated as a provider tries to create a item with a blank link--DOESN'T WORK

				"provider", "name1", "description1", "", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)

			this.createAndSaveItemTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);

	}
	protected void createAndSaveItemTemplate(final String user, final String name, final String description, final String link, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);

			final Item lr = this.itemService.create();
			lr.setName(name);
			lr.setDescription(description);
			lr.setLink(link);
			final Collection<String> pic = new ArrayList<>();
			lr.setOptionalPictures(pic);

			this.itemService.save(lr);

			this.itemRepository.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		System.out.println("Se esperaba " + expected + " y es " + caught);

		this.checkExceptions(expected, caught);

	}

	//	//Analysis of sentence coverage: 13/13
	//	//Analysis of data coverage: 2/3

	//	//Requirement 9.1 An actor who is authenticated as a provider must be able to edit its items

	@Test
	public void editAndSaveItemRecordDriver() {

		final Object testingData[][] = {

			{ //User authenticated as a provider tries to edit a item-- WORK

				"provider", "item1", "new name", null
			}, { //User authenticated as a hacker tries to edit a item--DOESN'T  WORK

				"rookie", "item1", "new name", IllegalArgumentException.class
			}, { //User authenticated as a provider tries to edit a item with a blank name-- DOESN'T WORK

				"provider", "item1", "", ConstraintViolationException.class

			}, { //User not authenticated tries to edit a item-- DOESN'T WORK

				null, "item1", "new text", IllegalArgumentException.class

			}

		};
		for (int i = 0; i < testingData.length; i++)

			this.editAndSaveItemTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}
	protected void editAndSaveItemTemplate(final String user, final String item, final String name, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);

			final Item lr = this.itemService.findOne(super.getEntityId(item));

			lr.setName(name);

			this.itemService.save(lr);

			this.itemRepository.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}
		System.out.println("---------------edit------------");

		System.out.println("Se esperaba " + expected + " y es " + caught);

		this.checkExceptions(expected, caught);
	}

	//	//Analysis of sentence coverage: 13/13
	//	//Analysis of data coverage: 3/3

	//	//Requirement 9.1 An actor who is authenticated as a provider must be able to display its items

	@Test
	public void displayItemDriver() {

		final Object testingData[][] = {

			{ //User not authenticated try to display a item-- WORK

				null, "item1", null
			},

			{//User authenticated as a provider display a item-- WORK

				"provider", "item1", null
			}, {//User authenticated as a rookie try to display a item-- WORK

				"rookie", "item1", null
			}
		};
		for (int i = 0; i < testingData.length; i++)

			this.displayItemTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void displayItemTemplate(final String user, final String item, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);
			@SuppressWarnings("unused")
			final Item inc = this.itemService.findOne(super.getEntityId(item));
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}
		System.out.println("Se esperaba " + expected + " y es " + caught);

		this.checkExceptions(expected, caught);
	}
	//Analysis of sentence coverage: 4/4
	//Analysis of data coverage: 3/3

}
