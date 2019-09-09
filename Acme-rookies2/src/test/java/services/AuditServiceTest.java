
package services;

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.AuditRepository;
import utilities.AbstractTest;
import domain.Audit;
import domain.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AuditServiceTest extends AbstractTest {

	//SYSTEM UNDER TEST

	@Autowired
	private AuditService	auditService;
	@Autowired
	private AuditRepository	auditRepository;
	@Autowired
	private PositionService	positionService;


	//Requirement 3. An actor who is authenticated as an auditor must be able to:
	//2. Manage his or her audits deleting them.

	//	@Test
	public void deleteAuditDriver() {

		final Object testingData[][] = {

			{ //User not authenticated try to delete a audit-- DOESN'T WORK
				null, "audit1", IllegalArgumentException.class
			}, {//User authenticated as a auditor  delete a audit-- WORK

				"auditor", "audit1", null
			}, {//User authenticated as a rookie try to delete a audit-- DOESN'T WORK9

				"rookie", "audit1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)

			this.deleteAuditTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void deleteAuditTemplate(final String user, final String audit, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);
			final Audit lr = this.auditService.findOne(super.getEntityId(audit));

			this.auditService.delete(lr);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}
		System.out.println("Se esperaba " + expected + " y es " + caught);

		this.checkExceptions(expected, caught);
	}
	//Analysis of sentence coverage: 13/13
	//Analysis of data coverage: 3/3

	//3. An actor who is authenticated as an auditor must be able to:
	//		2. Manage his or her audits, which includes lcreating them

	@Test
	public void createAndSaveAuditDriver() {

		final Object testingData[][] = {

			{ //User authenticated as a AUDITOR create a audit-- WORK

				"auditor", "text1", 7, "position1", null
			}, { //User authenticated as a rookie tries to create a audit-- DOESN'T WORK

				"rookie", "text1", 7, "position1", IllegalArgumentException.class

			}, { //User authenticated as a auditor tries to create a audit with a blank text--DOESN'T WORK

				"auditor", "", 7, "position1", ConstraintViolationException.class
			}, { //User authenticated as a auditor tries to create a audit with a null score--DOESN'T WORK

				"auditor", "text1", null, "position1", ConstraintViolationException.class
			}, { //User authenticated as non authenticated tries to create a audit--DOESN'T WORK

				null, "text1", 7, "position1", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)

			this.createAndSaveAuditTemplate((String) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);

	}
	protected void createAndSaveAuditTemplate(final String user, final String text, final Integer score, final String position, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);

			final Audit lr = this.auditService.create();
			lr.setText(text);
			lr.setScore(score);

			lr.setMoment(new Date());

			lr.setDraftMode(true);
			final Position pos = this.positionService.findOne(super.getEntityId(position));
			lr.setPosition(pos);

			this.auditService.save(lr);

			this.auditRepository.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		System.out.println("Se esperaba " + expected + " y es " + caught);

		this.checkExceptions(expected, caught);

	}

	//	//Analysis of sentence coverage: 19/19
	//	//Analysis of data coverage: 3/3
	//
	//3. An actor who is authenticated as an auditor must be able to:
	//			2. Manage his or her audits, which includes  updating

	@Test
	public void editAndSaveAuditRecordDriver() {

		final Object testingData[][] = {

			{ //User authenticated as a auditor tries to edit a audit-- WORK

				"auditor", "audit1", "new text", null
			}, { //User authenticated as a hacker tries to edit a audit--DOESN'T  WORK

				"rookie", "audit1", "new text", IllegalArgumentException.class
			}, { //User authenticated as a auditor tries to edit a audit with a blank text-- DOESN'T WORK

				"auditor", "audit1", "", ConstraintViolationException.class

			}, { //User not authenticated tries to edit a audit-- DOESN'T WORK

				null, "audit1", "new text", IllegalArgumentException.class

			}

		};
		for (int i = 0; i < testingData.length; i++)

			this.editAndSaveAuditTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}
	protected void editAndSaveAuditTemplate(final String user, final String audit, final String text, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);
			final Audit lr = this.auditService.findOne(super.getEntityId(audit));

			lr.setText(text);

			this.auditService.save(lr);

			this.auditRepository.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}
		System.out.println("---------------edit------------");

		System.out.println("Se esperaba" + expected + "y es" + caught);

		this.checkExceptions(expected, caught);
	}

	//	//Analysis of sentence coverage: 13/13
	//	//Analysis of data coverage: 3/3
	//	3. An actor who is authenticated as an auditor must be able to:
	//	2. Manage his or her audits, which includes showing them

	@Test
	public void displayAuditRecordDriver() {

		final Object testingData[][] = {

			{//User authenticated as a auditor display a audit-- WORK

				"auditor", "audit1", null
			}, {//User authenticated as a company try to display a audit-- WORK

				"company", "audit1", null
			}
		};
		for (int i = 0; i < testingData.length; i++)

			this.displayAuditTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void displayAuditTemplate(final String user, final String audit, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);
			@SuppressWarnings("unused")
			final Audit inc = this.auditService.findOne(super.getEntityId(audit));
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}
		System.out.println("Se esperaba " + expected + " y es " + caught);

		this.checkExceptions(expected, caught);
	}
	//Analysis of sentence coverage: 4/4
	//Analysis of data coverage: 2/3

}
