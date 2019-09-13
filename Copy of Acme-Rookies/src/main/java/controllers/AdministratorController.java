/*
 * /*
 * AdministratorController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.AuditService;
import services.CompanyService;
import services.CreditCardService;
import services.HackerService;
import services.NetcasheService;
import services.PositionService;
import services.ProviderService;
import domain.Administrator;
import domain.Company;

@Controller
@RequestMapping("/administrator/administrator")
public class AdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AuditService			auditService;

	@Autowired
	private ProviderService			providerService;
	@Autowired
	private NetcasheService			xompService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	//Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Administrator admin;

		admin = this.administratorService.create();
		result = this.createEditModelAndView(admin);

		return result;
	}
	// Edition ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		result = this.createEditModelAndView(admin);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Administrator admin, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(admin);
		else if (admin.getPhoneNumber().length() < 4)
			result = this.createEditModelAndView(admin, "administrator.phone.error");
		else if (this.administratorService.checkAdminEmail(admin.getEmail()) == false)
			result = this.createEditModelAndView(admin, "admin.email.error");
		else if (this.creditCardService.checkCreditCard(admin.getCreditCard()) == false)
			result = this.createEditModelAndView(admin, "admin.creditCard.error");
		else
			try {
				this.administratorService.save(admin);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final DataIntegrityViolationException oops) {
				result = this.createEditModelAndView(admin, "administrator.username.error");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(admin, "administrator.commit.error");
			}
		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Administrator admin) {
		ModelAndView result;

		result = this.createEditModelAndView(admin, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator admin, final String message) {
		ModelAndView result;

		result = new ModelAndView("administrator/edit");
		result.addObject("administrator", admin);

		result.addObject("message", message);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final Double avgNetcashesAud = this.xompService.avgNumberNetcashesPerAudit();
		final Double stdNetcashesAud = this.xompService.stdNumberNetcashesPerAudit();
		final Double ratioNetcashesPublished = this.xompService.ratioNetcashesPublished();
		final Double ratioNetcashesUnpublished = this.xompService.ratioNetcashesUnpublished();

		final Double avgComp = this.companyService.averagePositionsPerCompany();
		final Integer minComp = this.companyService.minPositionsPerCompany();
		final Integer maxComp = this.companyService.maxPositionsPerCompany();
		final Double destipComp = this.companyService.stdDevPositionsPerCompany();

		final Collection<String> comps = this.companyService.findCompaniesMorePositions();

		final Double avgHck = this.hackerService.averageApplicationsPerHacker();
		final Integer minHck = this.hackerService.minApplicationsPerHacker();
		final Integer maxHck = this.hackerService.maxApplicationsPerHacker();
		final Double stdDevHck = this.hackerService.stddevApplicationsPerHacker();

		final Collection<String> hckrs = this.hackerService.findHackersMoreApplications();

		final Double avgPos = this.positionService.averageSalaries();
		final Double minPos = this.positionService.minSalary();
		final Double maxPos = this.positionService.maxSalary();
		final Double stdDevPos = this.positionService.stddevSalary();

		final String best = this.positionService.findBestPosition();
		final String worst = this.positionService.findWorstPosition();

		//		final Double avgScPos = this.auditService.avgAuditScoresPerPosition();
		//		final Double minScPos = this.auditService.minAuditScoresPerPosition();
		//		//		final Double maxScPos = this.auditService.maxAuditScoresPerPosition();
		//		final Double stdScPos = this.auditService.stdDevAuditScoresPerPosition();
		//		final Double salScPos = this.auditService.avgSalaryFromHighestAuditScorePositions();

		final Double avgScComp = this.auditService.avgAuditScorePerCompany();
		final Double minScComp = this.auditService.minAuditScorePerCompany();
		final Double maxScComp = this.auditService.maxAuditScorePerCompany();
		final Double stdScComp = this.auditService.stdDevAuditScorePerCompany();
		final Collection<String> highestSc = this.auditService.highestScore();

		final Double avgItemsProv = this.providerService.avgItemsPerProvider();
		final Integer minItemsProv = this.providerService.minItemsPerProvider();
		final Integer maxItemsProv = this.providerService.maxItemsPerProvider();
		final Double stdDevItemsProv = this.providerService.stdDevItemsPerProvider();
		final Collection<String> topProvs = this.providerService.findTopProviders();

		result = new ModelAndView("administrator/list");
		result.addObject("avgComp", avgComp);
		result.addObject("minComp", minComp);
		result.addObject("maxComp", maxComp);
		result.addObject("devComp", destipComp);

		result.addObject("comps", comps);

		result.addObject("avgHck", avgHck);
		result.addObject("minHck", minHck);
		result.addObject("maxHck", maxHck);
		result.addObject("devHck", stdDevHck);

		result.addObject("hckrs", hckrs);

		result.addObject("avgPos", avgPos);
		result.addObject("minPos", minPos);
		result.addObject("maxPos", maxPos);
		result.addObject("devPos", stdDevPos);

		result.addObject("best", best);
		result.addObject("worst", worst);

		//		result.addObject("avgScPos", avgScPos);
		//		result.addObject("minScPos", minScPos);
		//		//		result.addObject("maxScPos", maxScPos);
		//		result.addObject("stdScPos", stdScPos);
		//		result.addObject("salScPos", salScPos);

		result.addObject("avgScComp", avgScComp);
		result.addObject("minScComp", minScComp);
		result.addObject("maxScComp", maxScComp);
		result.addObject("stdScComp", stdScComp);
		result.addObject("highestPos", highestSc);

		result.addObject("avgItemsProv", avgItemsProv);
		result.addObject("minItemsProv", minItemsProv);
		result.addObject("maxItemsProv", maxItemsProv);
		result.addObject("stdDevItemsProv", stdDevItemsProv);
		result.addObject("topProvs", topProvs);

		result.addObject("avgNetcashesAud", avgNetcashesAud);
		result.addObject("stdNetcashesAud", stdNetcashesAud);
		result.addObject("ratioNetcashesPublished", ratioNetcashesPublished);
		result.addObject("ratioNetcashesUnpublished", ratioNetcashesUnpublished);

		result.addObject("requestURI", "administrator/list.do");

		return result;

	}
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public ModelAndView data() {
		ModelAndView result;

		final Administrator avg = this.administratorService.findByPrincipal();
		//		final String res = this.actorService.createJson(avg);

		result = new ModelAndView("administrator/data");
		result.addObject("admin", avg);
		result.addObject("requestURI", "administrator/administrator/data.do");

		return result;

	}

	@RequestMapping(value = "/deleteAccount", method = RequestMethod.GET)
	public ModelAndView delete() {
		ModelAndView result;
		final Administrator admin = this.administratorService.findByPrincipal();
		result = new ModelAndView("redirect:/j_spring_security_logout");

		this.administratorService.delete(admin);
		//result = new ModelAndView("redirect:/j_spring_security_logout");
		return result;

	}

	@RequestMapping(value = "/compute", method = RequestMethod.GET)
	public ModelAndView computeScores() {
		final ModelAndView result;
		final Map<Company, Double> companiesScores = this.auditService.processAuditScores();

		result = new ModelAndView("administrator/compute");
		result.addObject("companiesScores", companiesScores);
		return result;
	}
}
