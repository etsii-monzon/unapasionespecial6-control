
package controllers;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AuditService;
import services.CompanyService;
import services.CreditCardService;
import services.PositionService;
import domain.Company;
import domain.Position;
import forms.CompanyForm;

@Controller
@RequestMapping("/company")
public class CompanyController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CompanyService		companyService;
	@Autowired
	private PositionService		positionService;

	@Autowired
	private CreditCardService	creditCardService;
	@Autowired
	private AuditService		auditService;

	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public CompanyController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final CompanyForm companyForm = new CompanyForm();

		result = this.createEditModelAndView(companyForm);

		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("company") final CompanyForm companyForm, final BindingResult binding) {
		ModelAndView result;
		Company res;
		res = this.companyService.reconstruct(companyForm, binding);

		if (binding.hasErrors()) {
			System.out.println(binding);
			result = this.createEditModelAndView(companyForm);
		} else if (this.actorService.checkUserEmail(res.getEmail()) == false)
			result = this.createEditModelAndView(companyForm, "company.email.error");
		else if (!(companyForm.getConfirmPassword().equals(res.getUserAccount().getPassword())))
			result = this.createEditModelAndView(companyForm, "company.password.error");
		else if (res.getPhoneNumber().length() < 4)
			result = this.createEditModelAndView(companyForm, "company.phone.error");
		else if (this.creditCardService.checkCreditCard(res.getCreditCard()) == false)
			result = this.createEditModelAndView(companyForm, "company.creditCard.error");
		else
			try {
				this.companyService.save(res);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final DataIntegrityViolationException oops) {
				result = this.createEditModelAndView(companyForm, "company.username.error");
			} catch (final Throwable oops) {
				System.out.println(oops);
				result = this.createEditModelAndView(companyForm, "company.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndView(final CompanyForm companyForm) {
		ModelAndView result;
		result = this.createEditModelAndView(companyForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final CompanyForm companyForm, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("company/create");

		result.addObject("company", companyForm);
		result.addObject("message", messageCode);
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int positionId) {
		ModelAndView result;
		Company company;
		Position position;
		Double companyScore;

		company = new Company();
		position = this.positionService.findOne(positionId);
		final Collection<Company> comps = this.companyService.findAll();
		final Map<Company, Double> companiesScores = this.auditService.auditScoresCompany();

		for (final Company c : comps)
			if (c.getPositions().contains(position))
				company = c;

		companyScore = companiesScores.get(company);

		result = new ModelAndView("company/show");
		result.addObject("requestURI", "company/show.do");
		result.addObject("company", company);
		result.addObject("companyScore", companyScore);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Company> companies;

		companies = this.companyService.findAll();

		result = new ModelAndView("company/list");
		result.addObject("companies", companies);
		result.addObject("requestURI", "company/list.do");

		return result;
	}

}
