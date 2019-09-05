
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class DashboardServiceTest extends AbstractTest {

	@Autowired
	private CompanyService	companyService;

	@Autowired
	private HackerService	hackerService;

	@Autowired
	private PositionService	positionService;

	@Autowired
	private AuditService	auditService;

	@Autowired
	private ProviderService	providerService;


	//An actor who is authenticated as an administrator must be able to display a dashboard with the following information:
	//	  · The average, the minimum, the maximum, and the standard deviation of the number of positions per company.
	//	  · The average, the minimum, the maximum, and the standard deviation of the number of applications per hacker.
	//	  · The companies that have offered more positions.
	//	  · The hackers who have made more applications.
	//	  · The average, the minimum, the maximum, and the standard deviation of the salaries offered.
	//	  · The best and the worst position in terms of salary.
	//	  · The average, the minimum, the maximum and the standard deviation of the audit score of the positions stored in the system.
	//	  · The average, the minimum, the maximum and the standard deviation of the audit score of the companies registered in the system.
	//	  · The companies with the highest audit score.
	//	  · The average salary offered by the positions that have the highest average audit score.
	//	  · The minimum, the maximum, the average and the standard deviation of the numbers of items per provided.
	//	  · The top-5 providers in terms of total number of items provided.

	@Test
	public void dashboardDriver() {
		final Object testingData[][] = {
			{//User authenticated as an admin tries to display a dashboard of statistics - WORK
				"admin", null

			}, {//User authenticated as a hacker tries to display a dashboard of statistics - DOESN'T WORK
				"hacker", IllegalArgumentException.class

			}, {//User not authenticated tries to display a dashboard of statistics -DOESN'T WORK
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)

			this.dashboardTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	protected void dashboardTemplate(final String user, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(user);
			this.companyService.averagePositionsPerCompany();
			this.companyService.findCompaniesMorePositions();
			this.companyService.maxPositionsPerCompany();
			this.companyService.minPositionsPerCompany();
			this.companyService.stdDevPositionsPerCompany();
			this.hackerService.averageApplicationsPerHacker();
			this.hackerService.findHackersMoreApplications();
			this.hackerService.maxApplicationsPerHacker();
			this.hackerService.minApplicationsPerHacker();
			this.hackerService.stddevApplicationsPerHacker();
			this.positionService.averageSalaries();
			this.positionService.maxSalary();
			this.positionService.minSalary();
			this.positionService.stddevSalary();
			this.positionService.findBestPosition();
			this.positionService.findWorstPosition();

			this.auditService.avgAuditScoresPerPosition();
			this.auditService.minAuditScoresPerPosition();
			this.auditService.maxAuditScoresPerPosition();
			this.auditService.stdDevAuditScoresPerPosition();
			this.auditService.avgSalaryFromHighestAuditScorePositions();
			this.auditService.avgAuditScorePerCompany();
			this.auditService.minAuditScorePerCompany();
			this.auditService.maxAuditScorePerCompany();
			this.auditService.stdDevAuditScorePerCompany();
			this.auditService.highestScore();

			this.providerService.minItemsPerProvider();
			this.providerService.maxItemsPerProvider();
			this.providerService.avgItemsPerProvider();
			this.providerService.stdDevItemsPerProvider();
			this.providerService.findTopProviders();

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}
		System.out.println("--------------list----------------");
		System.out.println("Se esperaba " + expected + " y es " + caught);
		this.checkExceptions(expected, caught);

	}
	//Analysis of sentence coverage: 88/88
	//Analysis of data coverage: 3/3
}
