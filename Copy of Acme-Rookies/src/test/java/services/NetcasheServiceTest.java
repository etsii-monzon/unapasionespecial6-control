
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Netcashe;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class NetcasheServiceTest extends AbstractTest {

	//SYSTEM UNDER TEST

	@Autowired
	private NetcasheService	netcasheService;


	// An actor who is authenticated as an company must be able to: Create Netcashes		

	@Test
	public void createAndSaveNetcasheDriver() {

		final Object testingData[][] = {

			{ //User authenticated as a COMPANY create a netcashe-- WORK-POSITIVE

				"company1", "TEST", "TROME", "audit10", null
			}, { //User authenticated as a AUDITOR create a netcashe--DOESN'T WORK-NEGATIVE

				"auditor1", "TEST", "TROME", "audit10", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)

			this.createAndSaveXompTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);

	}
	protected void createAndSaveXompTemplate(final String user, final String body, final String status, final String audit, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);

			final Netcashe lr = this.netcasheService.create(super.getEntityId(audit));

			lr.setBody(body);
			lr.setDraftMode(true);
			lr.setStatus(status);

			this.netcasheService.save(lr);

			this.netcasheService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		System.out.println("--------------Create Xomp----------------");
		System.out.println("Se esperaba " + expected + " y es " + caught);
		this.checkExceptions(expected, caught);

	}
	//	//Analysis of sentence coverage: 19/19
	//	//Analysis of data coverage: 3/3

}
