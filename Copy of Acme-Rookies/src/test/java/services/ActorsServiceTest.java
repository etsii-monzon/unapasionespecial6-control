
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Company;
import domain.Hacker;
import domain.Position;
import domain.Provider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ActorsServiceTest extends AbstractTest {

	@Autowired
	private CompanyService	companyService;

	@Autowired
	private HackerService	hackerService;

	@Autowired
	private PositionService	positionService;

	@Autowired
	private ProviderService	providerService;

	@Autowired
	private ActorService	actorService;


	//7. An actor who is not authenticated must be able to:Register to the system as a company.

	@Test
	public void registerCompanyDriver() {
		final Object testingData[][] = {
			{
				//An actor who is not authenticated must be able to:Register to the system as a company- WORK
				"username1", "pass123", null
			}, {
				//An actor who is not authenticated must be able to:Register to the system as a company with a valid username- DOESN'T WORK
				"", "pass123", IllegalArgumentException.class
			}, {
				//An actor who is not authenticated must be able to:Register to the system as a company with a valid password- DOESN'T WORK
				"username1", "", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)

			this.registerCompanyTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void registerCompanyTemplate(final String username, final String password, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			this.authenticate(null);
			final Company c = this.companyService.create();

			c.setName("name");
			c.setSurname("surname");
			c.setEmail("email@gmail.com");
			c.setVat(0.21);
			c.setCommercialName("commercialName");

			c.getUserAccount().setUsername(username);
			c.getUserAccount().setPassword(password);

			c.getCreditCard().setBrandName("BrandName");
			c.getCreditCard().setCvv(120);
			c.getCreditCard().setExpMonth(10);
			c.getCreditCard().setHolderName("name");
			c.getCreditCard().setExpYear(22);
			c.getCreditCard().setNumber("4024007120853782");

			this.companyService.save(c);
			//
			//			this.companyRepository.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		}
		System.out.println("--------------Register Company----------------");
		System.out.println("Se esperaba " + expected + " y es " + caught);
		this.checkExceptions(expected, caught);

	}
	//	//Analysis of sentence coverage: 33/33 
	//	//Analysis of data coverage: 1/3
	//
	//	//7. An actor who is not authenticated must be able to:Register to the system as a hacker.
	//
	@Test
	public void registerHackerDriver() {
		final Object testingData[][] = {
			{
				//An actor who is not authenticated must be able to:Register to the system as a company- WORK
				"username1", "pass123", null
			}, {
				//An actor who is not authenticated must be able to:Register to the system as a hacker with a valid username- DOESN'T WORK
				"", "pass123", IllegalArgumentException.class
			}, {
				//An actor who is not authenticated must be able to:Register to the system as a hacker with a valid password- DOESN'T WORK
				"username1", "", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)

			this.registerHackerTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void registerHackerTemplate(final String username, final String password, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			this.authenticate(null);
			final Hacker h = this.hackerService.create();
			h.setName("name");
			h.setSurname("surname");
			h.setEmail("email@gmail.com");
			h.setVat(0.21);

			h.getUserAccount().setUsername(username);
			h.getUserAccount().setPassword(password);

			h.getCreditCard().setBrandName("BrandName");
			h.getCreditCard().setCvv(120);
			h.getCreditCard().setExpMonth(10);
			h.getCreditCard().setHolderName("name");
			h.getCreditCard().setExpYear(22);
			h.getCreditCard().setNumber("4024007120853782");

			this.hackerService.save(h);
			//
			//			this.companyRepository.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		}
		System.out.println("--------------Register Hacker----------------");
		System.out.println("Se esperaba " + expected + " y es " + caught);
		this.checkExceptions(expected, caught);

	}
	//	//Analysis of sentence coverage: 31/31
	//	//Analysis of data coverage: 1/3
	//
	//	//7. An actor who is not authenticated must be able to:
	//	//	 2. List the positions available and navigate to the corresponding companies
	//	//
	//	//	8. An actor who is authenticated must be able to:
	//	//		1. Do the same as an actor who is not authenticated, but register to the system.
	@Test
	public void actorsListPositionsDriver() {
		final Object testingData[][] = {
			{
				//An actor who is hacker- WORK
				"rookie", null
			}, {
				//An actor who is  company- WORK
				"company", null
			}, {
				//An actor who is administrator- WORK
				"admin", null
			}
		};

		for (int i = 0; i < testingData.length; i++)

			this.actorsListPositionsTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}
	protected void actorsListPositionsTemplate(final String user, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			super.authenticate(user);

			this.positionService.findAll();

			final Company c = this.companyService.findOne(this.getEntityId("company1"));
			for (final Position p : c.getPositions())
				this.positionService.findOne(p.getId());

			super.unauthenticate();

		} catch (final Throwable oops) {
			System.out.println(oops);

			caught = oops.getClass();
		}
		System.out.println("--------------List Positions----------------");
		System.out.println("Se esperaba " + expected + " y es " + caught);
		this.checkExceptions(expected, caught);

	}
	//	//Analysis of sentence coverage: 11/11
	//	//Analysis of data coverage: 3/3
	//
	//	//7. An actor who is not authenticated must be able to:
	//	//	 3. List the companies available and navigate to the corresponding positions.
	@Test
	public void actorsListCompaniesDriver() {
		final Object testingData[][] = {
			{

				//An actor who is hacker- WORK
				"rookie", null
			}, {
				//An actor who is  company- WORK
				"company", null
			}, {
				//An actor who is administrator- WORK
				"admin", null
			}
		};

		for (int i = 0; i < testingData.length; i++)

			this.actorsListCompaniesTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}
	protected void actorsListCompaniesTemplate(final String user, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			super.authenticate(user);

			this.companyService.findAll();

			final Company c = this.companyService.findOne(this.getEntityId("company1"));
			for (final Position p : c.getPositions())
				this.positionService.findOne(p.getId());

			super.unauthenticate();

		} catch (final Throwable oops) {
			System.out.println(oops);

			caught = oops.getClass();
		}
		System.out.println("--------------List Companies----------------");
		System.out.println("Se esperaba " + expected + " y es " + caught);
		this.checkExceptions(expected, caught);

	}
	//Analysis of sentence coverage: 11/11
	//		//Analysis of data coverage: 3/3
	//
	//	//7. An actor who is not authenticated must be able to:
	//	//	 4. Search for a position using a single key word that must be contained in its title, 
	//	//		its description, its profile, its skills, its technologies, or the name of the corresponding company.
	@Test
	public void searchDriver() {
		final Object testingData[][] = {
			{
				//An actor who  not authenticated - WORK
				null, "keyword", null
			}, {
				//An actor who is hacker- WORK
				"rookie", "keyword", null
			}, {
				//An actor who is  company- WORK
				"company1", "keyword", null
			}, {
				//An actor who is administrator- WORK
				"admin", "keyword", null
			}
		};

		for (int i = 0; i < testingData.length; i++)

			this.searchTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void searchTemplate(final String user, final String keyword, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			super.authenticate(user);

			this.positionService.searchPositions(keyword);
			this.companyService.searchCompanyByCommercialName(keyword);

			super.unauthenticate();

		} catch (final Throwable oops) {
			System.out.println(oops);

			caught = oops.getClass();
		}
		System.out.println("--------------Search positions----------------");
		System.out.println("Se esperaba " + expected + " y es " + caught);
		this.checkExceptions(expected, caught);

	}
	//	//Analysis of sentence coverage: 2/2 
	//	//Analysis of data coverage: 3/3
	//
	//	//9. An actor who is not authenticated must be able to:
	//	//	3. Register to the system as a provider.
	//
	@Test
	public void registerProviderDriver() {
		final Object testingData[][] = {
			{
				//An actor who is not authenticated must be able to:Register to the system as a company- WORK
				"username1", "pass123", null
			}, {
				//An actor who is not authenticated must be able to:Register to the system as a company with a valid username- DOESN'T WORK
				"", "pass123", IllegalArgumentException.class
			}, {
				//An actor who is not authenticated must be able to:Register to the system as a company with a valid password- DOESN'T WORK
				"username1", "", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)

			this.registerProviderTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void registerProviderTemplate(final String username, final String password, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			this.authenticate(null);
			final Provider p = this.providerService.create();

			p.setName("name");
			p.setSurname("surname");
			p.setEmail("email@gmail.com");
			p.setVat(0.21);
			p.setMake("make");

			p.getUserAccount().setUsername(username);
			p.getUserAccount().setPassword(password);

			p.getCreditCard().setBrandName("BrandName");
			p.getCreditCard().setCvv(120);
			p.getCreditCard().setExpMonth(10);
			p.getCreditCard().setHolderName("name");
			p.getCreditCard().setExpYear(22);
			p.getCreditCard().setNumber("4024007120853782");

			this.providerService.save(p);
			//
			//			this.companyRepository.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		}
		System.out.println("--------------Register Provider----------------");
		System.out.println("Se esperaba " + expected + " y es " + caught);
		this.checkExceptions(expected, caught);

	}
	//Analysis of sentence coverage: 30/32 
	//Analysis of data coverage: 1/3
}
