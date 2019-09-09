
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Hacker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class HackerServiceTest extends AbstractTest {

	@Autowired
	private HackerService	hackerService;


	//8. An actor who is authenticated must be able to:
	//	 2. Edit his or her personal data.

	@Test
	public void editProfileDriver() {
		final Object testingData[][] = {
			{
				//User can edit his name/his email/his VAT- WORK
				"rookie", "Joselito", "email@email.com", 0.21, "Marquez", null
			}, {
				//Not autenthicated user can't edit- DOESN'T WORK
				null, "Joselito", "email@email.com", 0.21, "Marquez", IllegalArgumentException.class
			}, {
				//Name can't be blank- DOESN'T WORK
				"rookie", "", "email@email.com", 0.21, "Marquez", ConstraintViolationException.class
			}, {
				//Valid email- DOESN'T WORK
				"rookie", "Joselito", "emailInvalido", 0.21, "Marquez", ConstraintViolationException.class
			}, {
				//Not Blank VAT- DOESN'T WORK
				"rookie", "Joselito", "emailI@valido.com", null, "Marquez", ConstraintViolationException.class
			}, {
				//Not Blank surname- DOESN'T WORK
				"rookie", "Joselito", "emailI@valido.com", 0.21, "", ConstraintViolationException.class
			}, {
				//Not Blank commercialName- DOESN'T WORK
				"rookie", "Joselito", "emailI@valido.com", 0.21, "Marquez", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)

			this.editProfileTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);

	}
	protected void editProfileTemplate(final String user, final String name, final String email, final Double vat, final String surname, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			this.authenticate(user);
			final Hacker hacker = this.hackerService.findByPrincipal();

			hacker.setName(name);//NotBlank
			hacker.setEmail(email);//Email
			hacker.setVat(vat);//NotBlank
			hacker.setSurname(surname);//NotBlank

			this.hackerService.save(hacker);
			this.hackerService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		}
		System.out.println("--------------Edit Hacker----------------");
		System.out.println("Se esperaba " + expected + " y es " + caught);
		this.checkExceptions(expected, caught);

	}
	//Analysis of sentence coverage: 10/16 
	//Analysis of data coverage: 2/3

}
