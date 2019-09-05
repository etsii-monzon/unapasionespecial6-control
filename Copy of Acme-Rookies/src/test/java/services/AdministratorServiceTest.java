
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Administrator;
import domain.Auditor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AuditService			auditService;

	@Autowired
	private AuditorService			auditorService;


	//11. An actor who is authenticated as an administrator must be able to:
	//	  1. Create user accounts for new administrators.
	//
	@Test
	public void registerAdminDriver() {
		final Object testingData[][] = {
			{
				//An actor who is authenticated  as an administrator must be able to:Create user accounts for new administrators- WORK
				"admin", "username1", "pass123", null
			}, {
				//Create user accounts with empty username- DOESN'T WORK
				"admin", "", "pass123", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)

			this.registerAdminTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}
	protected void registerAdminTemplate(final String user, final String username, final String password, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			this.authenticate(user);
			final Administrator a = this.administratorService.create();

			a.setName("Jose");
			a.setSurname("surname");
			a.setEmail("email@email.com");
			a.setVat(0.21);

			a.getUserAccount().setUsername(username);
			a.getUserAccount().setPassword(password);

			a.getCreditCard().setBrandName("BrandName");
			a.getCreditCard().setCvv(120);
			a.getCreditCard().setExpMonth(10);
			a.getCreditCard().setHolderName("name");
			a.getCreditCard().setExpYear(22);
			a.getCreditCard().setNumber("4024007120853782");

			this.administratorService.save(a);
			this.administratorService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		}
		System.out.println("--------------Register Administrator----------------");
		System.out.println("Se esperaba " + expected + " y es " + caught);
		this.checkExceptions(expected, caught);

	}
	//	//Analysis of sentence coverage: 29/29
	//	//Analysis of data coverage: 2/3
	//
	//	//11. An actor who is authenticated as an administrator must be able to:
	//	//	  1. Create user accounts for new administrators.
	//
	@Test
	public void editProfileAdminDriver() {
		final Object testingData[][] = {
			{
				//User can edit his name/his email/his VAT- WORK
				"admin", "Joselito", "email@email.com", 0.21, null
			}, {
				//Name can't be blank- DOESN'T WORK
				"admin", "", "email@email.com", 0.21, ConstraintViolationException.class
			}, {
				//Valid email- DOESN'T WORK
				"admin", "Joselito", "email_Invalido", 0.21, ConstraintViolationException.class
			}, {
				//Not Blank VAT- DOESN'T WORK
				"admin", "Joselito", "emailI@valido.com", null, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)

			this.editProfileTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], (Class<?>) testingData[i][4]);

	}
	protected void editProfileTemplate(final String user, final String name, final String email, final Double vat, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			this.authenticate(user);
			final Administrator a = this.administratorService.findByPrincipal();
			a.getUserAccount().setPassword("admin");

			a.setName(name);//NotBlank
			a.setEmail(email);//Email
			a.setVat(vat);//NotBlank

			this.administratorService.save(a);
			this.administratorService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		}
		System.out.println("--------------Edit Administrator----------------");
		System.out.println("Se esperaba " + expected + " y es " + caught);
		this.checkExceptions(expected, caught);

	}
	//	//Analysis of sentence coverage: 9/14 
	//	//Analysis of data coverage: 1/3
	//
	//	//4.3 An actor who is authenticated as an administrator must be able to: 
	//	//	Launch a process to compute an audit score for every company. The audit score is computed as the average of the audit scores that the positions offered by a company has got, 
	//	//	but normalised to range 0.00 up to +1.00 using a linear homothetic transformation.
	@Test
	public void computeAuditScoreDriver() {
		final Object testingData[][] = {
			{
				//User authenticated as an admin tries to launch the process to compute an audit score for every company. - WORK
				"admin", null

			}, {//User not authenticated as an admin tries to launch the process to compute an audit score for every company. - DOESN'T WORK
				"company", IllegalArgumentException.class

			}, {//User not authenticated tries to launch the process to compute an audit score for every company. - DOESN'T WORK
				null, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)

			this.computeAuditScoreTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	protected void computeAuditScoreTemplate(final String user, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(user);
			this.auditService.processAuditScores();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		System.out.println("--------------Compute Audit Scores----------------");
		System.out.println("Se esperaba " + expected + " y es " + caught);
		this.checkExceptions(expected, caught);

	}

	//Analysis of sentence coverage: 16/16
	//Analysis of data coverage: 3/3

	//4. An actor who is authenticated as an administrator must be able to:
	//	2. Create user accounts for new auditors.

	@Test
	public void registerAuditorDriver() {
		final Object testingData[][] = {
			{
				//An actor who is authenticated  as an administrator must be able to:Create user accounts for new auditors- WORK
				"admin", "username1", "pass123", null
			}, {
				//Create user accounts with empty username- DOESN'T WORK
				"admin", "", "pass123", IllegalArgumentException.class
			}, {
				//Non authenticated try to create an account- DOESN'T WORK
				null, "username", "pass123", IllegalArgumentException.class
			}, {
				//Hacker try to create an account- DOESN'T WORK
				"hacker", "username", "pass123", IllegalArgumentException.class
			}, {
				//Comapny try to create an account- DOESN'T WORK
				"company", "username", "pass123", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)

			this.registerAuditorTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}
	protected void registerAuditorTemplate(final String user, final String username, final String password, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			this.authenticate(user);
			final Auditor a = this.auditorService.create();

			a.setName("Jose");
			a.setSurname("surname");
			a.setEmail("email@email.com");
			a.setVat(0.21);

			a.getUserAccount().setUsername(username);
			a.getUserAccount().setPassword(password);

			a.getCreditCard().setBrandName("BrandName");
			a.getCreditCard().setCvv(120);
			a.getCreditCard().setExpMonth(10);
			a.getCreditCard().setHolderName("name");
			a.getCreditCard().setExpYear(22);
			a.getCreditCard().setNumber("4024007120853782");

			this.auditorService.save(a);
			//			this.administratorService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		}
		System.out.println("--------------Register Auditor----------------");
		System.out.println("Se esperaba " + expected + " y es " + caught);
		this.checkExceptions(expected, caught);

	}
	//Analysis of sentence coverage: 32/32
	//Analysis of data coverage: 3/3
}
