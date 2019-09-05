
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Application;
import domain.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	@Autowired
	private ApplicationService	appService;

	@Autowired
	private PositionService		positionService;


	//An actor who is authenticated as a company must be able to manage the applications to their positions, which includes showing them
	//An actor who is authenticated as a hacker must be able to manage his or her applications, which includes showing them
	@Test
	public void showAppDriver() {
		final Object testingData[][] = {
			{
				//User authenticated as a company tries to show an application- WORK
				"company", "application1", null
			}, {
				//User authenticated as a hacker tries to show an application- WORK
				"rookie", "application2", null
			}, {
				//User not authenticated tries to show an application- DOESN'T WORK
				null, "application1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)

			this.showAppTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	protected void showAppTemplate(final String user, final String application, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);
			@SuppressWarnings("unused")
			final Application inc = this.appService.findOne(super.getEntityId(application));
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}
		System.out.println("--------------display----------------");
		System.out.println("Se esperaba " + expected + " y es " + caught);
		this.checkExceptions(expected, caught);

	}
	//Analysis of sentence coverage: 4/4
	//Analysis of data coverage: 3/3

	//An actor who is authenticated as a company must be able to manage the applications to their positions, which includes showing them
	//An actor who is authenticated as a hacker must be able to manage his or her applications, which includes showing them
	@Test
	public void listAppDriver() {
		final Object testingData[][] = {
			{
				//User authenticated as a company tries to show an application- WORK
				"company", "position1", null
			}, {
				//User authenticated as a hacker tries to show an application- WORK
				"rookie", "position1", null
			}, {
				//User not authenticated tries to show an application- DOESN'T WORK
				null, "position1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)

			this.listAppTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	protected void listAppTemplate(final String user, final String position, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);
			final Position pos = this.positionService.findOne(super.getEntityId(position));
			final Collection<Application> inc = this.appService.findAllGrouped(pos);

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}
		System.out.println("--------------list----------------");
		System.out.println("Se esperaba " + expected + " y es " + caught);
		this.checkExceptions(expected, caught);

	}
	//Analysis of sentence coverage: 24/24
	//Analysis of data coverage: 3/3
	//An actor who is authenticated as a hacker must be able to manage the applications to their positions, which includes creating them.
	//When an application is created, the system assigns an arbitrary problem to it
	@Test
	public void createAndSaveAppDriver() {
		final Object testingData[][] = {
			{
				//User authenticated as a hacker tries to create an application to a position- WORK
				"rookie", "position1", null
			}, {
				//User authenticated as a company tries to create an application- DOESN'T WORK
				"company", "position1", IllegalArgumentException.class
			}, {
				//User not authenticated tries to create an application- DOESN'T WORK
				null, "position1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)

			this.createAndSaveAppTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	protected void createAndSaveAppTemplate(final String user, final String position, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);
			@SuppressWarnings("unused")
			final Application inc = this.appService.create(super.getEntityId(position));
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}
		System.out.println("--------------create----------------");
		System.out.println("Se esperaba " + expected + " y es " + caught);
		this.checkExceptions(expected, caught);

	}
	//Analysis of sentence coverage: 26/37 
	//Analysis of data coverage: 3/3

	//An actor who is authenticated as a hacker must be able to manage the applications to their positions, which includes creating them.
	//When an application is created, the system assigns an arbitrary problem to it
	@Test
	public void editAndSaveAppDriver() {
		final Object testingData[][] = {
			{
				//User authenticated as a hacker tries to update an application with explanations and a link to a position- WORK
				"rookie", "application1", "Explanations for the problem", "https://github.com/franciskou", "", null
			}, {
				//User authenticated as a company tries to update an application by rejecting it- WORK
				"company", "application2", "", "", "REJECTED", null
			}, {
				//User not authenticated tries to update an application by answering the problem- DOESN'T WORK
				null, "application1", "Explanations for the problem", "https://github.com/franciskou", "", IllegalArgumentException.class
			}, {
				//User authenticated as a hacker tries to update an application with explanations and a wrong link to a position- DOESN'T WORK
				"rookie", "application1", "Explanations for the problem", "link1", "", ConstraintViolationException.class
			}, {
				//User authenticated as a company tries to update an application by rejecting it but with a wrong status- DOESN'T WORK
				"company", "application2", "", "", "NO NOS INTERESA", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)

			this.editAndSaveAppTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);

	}

	protected void editAndSaveAppTemplate(final String user, final String application, final String explanations, final String link, final String status, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);
			final int id = super.getEntityId(application);
			final Application app = this.appService.findOne(id);

			if (!explanations.isEmpty()) {
				app.setExplanations(explanations);
				app.setLink(link);
			}
			if (!status.isEmpty())
				app.setStatus(status);

			this.appService.save(app);
			this.appService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}
		System.out.println("-------------edit----------------");
		System.out.println("Se esperaba " + expected + " y es " + caught);
		this.checkExceptions(expected, caught);

	}
	//Analysis of sentence coverage: 18/18
	//Analysis of data coverage: 3/3
}
