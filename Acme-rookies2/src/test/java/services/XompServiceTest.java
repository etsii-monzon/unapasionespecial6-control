
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Xomp;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class XompServiceTest extends AbstractTest {

	//SYSTEM UNDER TEST

	@Autowired
	private XompService	xompService;


	// An actor who is authenticated as an company must be able to:Create Xomps		

	@Test
	public void createAndSaveXompDriver() {

		final Object testingData[][] = {

			{ //User authenticated as a COMPANY create a xomp-- WORK-POSITIVE

				"company1", "TEST", "PENDING", "position1", null
			}, { //User authenticated as a AUDITOR create a xomp--DOESN'T WORK-NEGATIVE

				"auditor1", "TEST", "PENDING", "position1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)

			this.createAndSaveXompTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);

	}
	protected void createAndSaveXompTemplate(final String user, final String body, final String status, final String position, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);

			final Xomp lr = this.xompService.create(super.getEntityId(position));

			lr.setBody(body);
			lr.setDraftMode(true);
			lr.setStatus(status);

			this.xompService.save(lr);

			this.xompService.flush();

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
