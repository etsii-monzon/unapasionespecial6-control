
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Company;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CompanyServiceTest extends AbstractTest {

	@Autowired
	private CompanyService	companyService;


	//8. An actor who is authenticated must be able to:
	//	 2. Edit his or her personal data.

	@Test
	public void editProfileDriver() {
		final Object testingData[][] = {
			{
				//User can edit his name/his email/his VAT- WORK
				"company", "Joselito", "email@email.com", 0.21, "Marquez", "MARCRE", null
			}, {
				//Not autenthicated user can't edit- DOESN'T WORK
				null, "Joselito", "email@email.com", 0.21, "Marquez", "MARCRE", IllegalArgumentException.class
			}, {
				//Name can't be blank- DOESN'T WORK
				"company", "", "email@email.com", 0.21, "Marquez", "MARCRE", ConstraintViolationException.class
			}, {
				//Valid email- DOESN'T WORK
				"company", "Joselito", "emailInvalido", 0.21, "Marquez", "MARCRE", ConstraintViolationException.class
			}, {
				//Not Blank VAT- DOESN'T WORK
				"company", "Joselito", "emailI@valido.com", null, "Marquez", "MARCRE", ConstraintViolationException.class
			}, {
				//Not Blank surname- DOESN'T WORK
				"company", "Joselito", "emailI@valido.com", 0.21, "", "MARCRE", ConstraintViolationException.class
			}, {
				//Not Blank commercialName- DOESN'T WORK
				"company", "Joselito", "emailI@valido.com", 0.21, "Marquez", "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)

			this.editProfileTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);

	}
	protected void editProfileTemplate(final String user, final String name, final String email, final Double vat, final String surname, final String commercialName, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			this.authenticate(user);
			final Company a = this.companyService.findByPrincipal();

			a.setName(name);//NotBlank
			a.setEmail(email);//Email
			a.setVat(vat);//NotBlank
			a.setCommercialName(commercialName);//NotBlank
			a.setSurname(surname);//NotBlank

			this.companyService.save(a);
			this.companyService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		}
		System.out.println("--------------Edit Company----------------");
		System.out.println("Se esperaba " + expected + " y es " + caught);
		this.checkExceptions(expected, caught);

	}
	//Analysis of sentence coverage: 10/16
	//Analysis of data coverage: 2/3

}
