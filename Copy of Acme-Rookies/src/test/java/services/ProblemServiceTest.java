
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

import repositories.ProblemRepository;
import utilities.AbstractTest;
import domain.Position;
import domain.Problem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ProblemServiceTest extends AbstractTest {

	//SYSTEM UNDER TEST

	@Autowired
	private ProblemService		problemService;
	@Autowired
	private PositionService		positionService;
	@Autowired
	private ProblemRepository	problemRepository;


	//Requirement 9.1 An actor who is authenticated as a company must be able to delete its positions

	@Test
	public void deleteProblemDriver() {

		final Object testingData[][] = {

			{ //User not authenticated try to delete a problem-- DOESN'T WORK
				null, "problem1", IllegalArgumentException.class
			},

			{//User authenticated as a company  delete a legal record-- WORK

				"company", "problem1", null
			}, {//User authenticated as a member try to delete a company-- DOESN'T WORK9

				"rookie", "problem1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)

			this.deletePositionTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void deletePositionTemplate(final String user, final String problem, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);
			final Problem lr = this.problemService.findOne(super.getEntityId(problem));

			this.problemService.delete(lr);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}
		this.checkExceptions(expected, caught);
	}
	//Analysis of sentence coverage: 8/8
	//Analysis of data coverage: 3/3

	//Requirement 9.1 An actor who is authenticated as a company must be able to create its problems

	@Test
	public void createAndSaveProblemDriver() {

		final Object testingData[][] = {

			{ //User authenticated as a company create a problem-- WORK

				"company", "title123", "statement1", "optionalHint1", "attachments1", false, "position1", null

			}, { //User authenticated as a hacker tries to create a problem-- DOESN'T WORK

				"rookie", "title123", "statement1", "optionalHint1", "attachments1", false, "position1", IllegalArgumentException.class

			}, { //User authenticated as a company tries to create a problem with a blank title--DOESN'T WORK

				"company", "", "statement1", "optionalHint1", "attachments1", false, "position1", ConstraintViolationException.class
			}, { //User authenticated as a company tries to create a problem with a blank statement--DOESN'T WORK

				"company", "title123", "", "optionalHint1", "attachments1", false, "position1", ConstraintViolationException.class
			}, { //User authenticated as a company tries to create a problem with a blank attachments--DOESN'T WORK

				"company", "title123", "statement1", "optionalHint1", "", false, "position1", ConstraintViolationException.class
			}, { //User authenticated as a company tries to create a problem with no position--DOESN'T WORK

				"company", "title123", "statement1", "optionalHint1", "attachments1", false, null, AssertionError.class
			}, { //User not authenticated tries to create a problem-- DOESN'T WORK

				null, "title123", "statement1", "optionalHint1", "attachments1", false, "position1", IllegalArgumentException.class

			}

		};
		for (int i = 0; i < testingData.length; i++)

			this.createAndSaveProblemTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Boolean) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);

	}
	protected void createAndSaveProblemTemplate(final String user, final String title, final String statement, final String optionalHint, final String attachment, final boolean draftMode, final String position, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);

			final Problem lr = this.problemService.create();
			lr.setTitle(title);
			lr.setStatement(statement);
			final Collection<String> attachments = new ArrayList<>();
			attachments.add(attachment);
			lr.setAttachments(attachments);
			lr.setOptionalHint(optionalHint);
			lr.setDraftMode(draftMode);
			final Position pos = this.positionService.findOne(super.getEntityId(position));
			lr.setPosition(pos);

			this.problemService.save(lr);
			System.out.println(lr);

			this.problemRepository.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}
		System.out.println("--------------create----------------");

		System.out.println("Se esperaba " + expected + " y es " + caught);

		this.checkExceptions(expected, caught);

	}

	//Analysis of sentence coverage: 16/17
	//Analysis of data coverage: 3/3

	//Requirement 9.1 An actor who is authenticated as a company must be able to edit its positions

	@Test
	public void editAndSaveProblemRecordDriver() {

		final Object testingData[][] = {

			{ //User authenticated as a brotherhood tries to edit a legal record-- WORK

				"company", "problem1", "new title", null
			}, { //User authenticated as a hacker tries to edit a position--DOESN'T  WORK

				"company", "problem1", "", ConstraintViolationException.class

			}, { //User not authenticated tries to edit a position with a blank description-- DOESN'T WORK

				null, "problem1", "new title", IllegalArgumentException.class

			}

		};
		for (int i = 0; i < testingData.length; i++)

			this.editAndSaveProblemTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}
	protected void editAndSaveProblemTemplate(final String user, final String problem, final String title, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);
			final Problem lr = this.problemService.findOne(super.getEntityId(problem));

			lr.setTitle(title);

			this.problemService.save(lr);

			this.problemRepository.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}
		System.out.println("--------------edit----------------");

		this.checkExceptions(expected, caught);
		System.out.println("Se esperaba" + expected + "y es" + caught);

	}

	//Analysis of sentence coverage: 11/11
	//Analysis of data coverage: 3/3
	//Requirement 9.1 An actor who is authenticated as a company must be able to display its positions

	@Test
	public void displayProblemDriver() {

		final Object testingData[][] = {

			{ //User not authenticated try to display a legal record-- DOESN'T WORK

				null, "problem1", null
			},

			{//User authenticated as a brotherhood display a legal record-- WORK

				"company", "position1", null
			}, {//User authenticated as a member try to display a legal record-- DOESN'T WORK

				"rookie", "position1", null
			}
		};
		for (int i = 0; i < testingData.length; i++)

			this.displayProblemTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void displayProblemTemplate(final String user, final String problem, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);
			@SuppressWarnings("unused")
			final Problem inc = this.problemService.findOne(super.getEntityId(problem));
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}
		System.out.println("--------------display----------------");

		this.checkExceptions(expected, caught);
		System.out.println("Se esperaba " + expected + " y es " + caught);

	}
	//Analysis of sentence coverage: 6/6
	//Analysis of data coverage: 3/3

}
