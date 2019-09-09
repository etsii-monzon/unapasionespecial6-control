
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.PositionRepository;
import utilities.AbstractTest;
import domain.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PositionServiceTest extends AbstractTest {

	//SYSTEM UNDER TEST

	@Autowired
	private PositionService		positionService;
	@Autowired
	private PositionRepository	positionRepository;


	//Requirement 9.1 An actor who is authenticated as a company must be able to delete its positions

	@Test
	public void deletePositionDriver() {

		final Object testingData[][] = {

			{ //User not authenticated try to delete a company-- DOESN'T WORK
				null, "position1", IllegalArgumentException.class
			}, {//User authenticated as a company  delete a legal record-- WORK

				"company2", "position2", null
			}, {//User authenticated as a member try to delete a company-- DOESN'T WORK9

				"rookie", "position2", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)

			this.deletePositionTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void deletePositionTemplate(final String user, final String position, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);
			final Position lr = this.positionService.findOne(super.getEntityId(position));

			this.positionService.delete(lr);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}
		System.out.println("Se esperaba " + expected + " y es " + caught);

		this.checkExceptions(expected, caught);
	}
	//Analysis of sentence coverage: 13/13
	//Analysis of data coverage: 3/3

	//Requirement 9.1 An actor who is authenticated as a company must be able to create its positions

	@Test
	public void createAndSavePositionDriver() {

		final Object testingData[][] = {

			{ //User authenticated as a company create a position-- WORK

				"company", "title2", "description123", "informatico", "java", "java", 1500.0, null

			}, { //User authenticated as a hacker tries to create a position-- DOESN'T WORK

				"rookie", "title123", "description1", "informatico", "java", "java", 1250.0, IllegalArgumentException.class

			}, { //User authenticated as a company tries to create a position with a blank title--DOESN'T WORK

				"company2", "", "description1", "informatico", "java", "java", 1250.0, ConstraintViolationException.class
			}, { //User authenticated as a company tries to create a position with a blank description--DOESN'T WORK

				"company2", "title123", "", "informatico", "java", "java", 1250.0, ConstraintViolationException.class
			}, { //User authenticated as a company tries to create a position with a blank deadline--DOESN'T WORK

				"company2", "title123", "description1", "", "java", "java", 1250.0, ConstraintViolationException.class
			}, { //User authenticated as a company tries to create a position with no profile--DOESN'T WORK

				"company2", "title123", "description1", "", "java", "java", 1250.0, ConstraintViolationException.class
			}, {//User authenticated as a company tries to create a position with no skills--DOESN'T WORK

				"company2", "title123", "description1", "informatico", "", "java", 1250.0, ConstraintViolationException.class

			}, {//User authenticated as a company tries to create a position with no technologies--DOESN'T WORK

				"company2", "title123", "description1", "informatico", "java", "", 1250.0, ConstraintViolationException.class

			}, { //User not authenticated tries to create a position-- DOESN'T WORK

				null, "title123", "description1", "informatico", "java", "java", 1250.0, IllegalArgumentException.class

			}

		};
		for (int i = 0; i < testingData.length; i++)

			this.createAndSaveLegalTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6],
				(Class<?>) testingData[i][7]);

	}
	protected void createAndSaveLegalTemplate(final String user, final String title, final String description, final String profile, final String skill, final String technology, final Double salary, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);

			final Position lr = this.positionService.create();
			lr.setTitle(title);
			lr.setDescription(description);
			//			final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:ii");
			//			final String stringFechaConHora = fecha;
			//			final Date fechaConHora = sdf.parse(stringFechaConHora);

			lr.setDeadline(new Date());
			lr.setProfile(profile);
			final Collection<String> skills = new ArrayList<>();
			skills.add(skill);
			lr.setSkills(skills);
			final Collection<String> technologies = new ArrayList<>();
			technologies.add(technology);
			lr.setTechnologies(technologies);
			lr.setSalary(salary);
			lr.setDraftMode(true);

			this.positionService.save(lr);

			this.positionRepository.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		System.out.println("Se esperaba " + expected + " y es " + caught);

		this.checkExceptions(expected, caught);

	}

	//	//Analysis of sentence coverage: 27/28
	//	//Analysis of data coverage: 3/3
	//
	//	//Requirement 9.1 An actor who is authenticated as a company must be able to edit its positions

	@Test
	public void editAndSavePositionRecordDriver() {

		final Object testingData[][] = {

			{ //User authenticated as a brotherhood tries to edit a legal record-- WORK

				"company", "position1", "new description", null
			}, { //User authenticated as a hacker tries to edit a position--DOESN'T  WORK

				"rookie", "position1", "new description", IllegalArgumentException.class
			}, { //User authenticated as a company tries to edit a position with a blank description-- DOESN'T WORK

				"company", "position1", "", ConstraintViolationException.class

			}, { //User not authenticated tries to edit a position with a blank description-- DOESN'T WORK

				null, "position1", "new description", IllegalArgumentException.class

			}

		};
		for (int i = 0; i < testingData.length; i++)

			this.editAndSaveLegalTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}
	protected void editAndSaveLegalTemplate(final String user, final String position, final String description, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);
			final Position lr = this.positionService.findOne(super.getEntityId(position));

			lr.setDescription(description);

			this.positionService.save(lr);

			this.positionRepository.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}
		System.out.println("---------------edit------------");

		System.out.println("Se esperaba" + expected + "y es" + caught);

		this.checkExceptions(expected, caught);
	}

	//	//Analysis of sentence coverage: 11/11
	//	//Analysis of data coverage: 3/3
	//	//Requirement 9.1 An actor who is authenticated as a company must be able to display its positions

	@Test
	public void displayPositionRecordDriver() {

		final Object testingData[][] = {

			{ //User not authenticated try to display a legal record-- WORK

				null, "position1", null
			},

			{//User authenticated as a brotherhood display a legal record-- WORK

				"company", "position1", null
			}, {//User authenticated as a member try to display a legal record-- WORK

				"rookie", "position1", null
			}
		};
		for (int i = 0; i < testingData.length; i++)

			this.displayPositionTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void displayPositionTemplate(final String user, final String position, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);
			@SuppressWarnings("unused")
			final Position inc = this.positionService.findOne(super.getEntityId(position));
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}
		System.out.println("Se esperaba " + expected + " y es " + caught);

		this.checkExceptions(expected, caught);
	}
	//Analysis of sentence coverage: 6/6
	//Analysis of data coverage: 3/3

}
