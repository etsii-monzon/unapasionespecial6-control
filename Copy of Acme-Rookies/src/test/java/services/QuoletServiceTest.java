
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Quolet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class QuoletServiceTest extends AbstractTest {

	//SYSTEM UNDER TEST

	@Autowired
	private QuoletService	quoletService;


	// An actor who is authenticated as an company must be able to create Quolets

	@Test
	public void createAndSaveQuoletDriver() {

		final Object testingData[][] = {

			{ //User authenticated as a COMPANY create a quolet-- WORK

				"company1", "TEST", "PENDING", "audit10", null
			}, { //User authenticated as a AUDITOR create a quolet--DOESN'T WORK

				"auditor1", "TEST", "PENDING", "audit10", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)

			this.createAndSaveQuoletTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);

	}
	protected void createAndSaveQuoletTemplate(final String user, final String body, final String status, final String audit, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);

			final Quolet lr = this.quoletService.create(super.getEntityId(audit));

			lr.setBody(body);
			lr.setDraftMode(true);
			lr.setStatus(status);

			this.quoletService.save(lr);

			this.quoletService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		System.out.println("--------------Create Quolet----------------");
		System.out.println("Se esperaba " + expected + " y es " + caught);
		this.checkExceptions(expected, caught);

	}
	//	//Analysis of sentence coverage: 19/19
	//	//Analysis of data coverage: 3/3

}
