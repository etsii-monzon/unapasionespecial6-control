/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CompanyService;
import services.CreditCardService;
import domain.Company;
import forms.CompanyForm;

@Controller
@RequestMapping("/company/company")
public class CompanyCompanyController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CreditCardService	creditCardService;


	// Constructors -----------------------------------------------------------

	public CompanyCompanyController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		final Company company;
		CompanyForm companyForm;

		company = this.companyService.findByPrincipal();
		companyForm = this.companyService.deconstruct(company);
		result = this.createEditModelAndView(companyForm);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("company") final CompanyForm companyForm, final BindingResult binding) {
		ModelAndView result;
		Company res = null;
		try {
			res = this.companyService.reconstruct(companyForm, binding);
		} catch (final Throwable oops) {
		}
		if (binding.hasErrors()) {
			System.out.print(binding);
			result = this.createEditModelAndView(companyForm);

		} else if (this.actorService.checkUserEmail(res.getEmail()) == false)
			result = this.createEditModelAndView(companyForm, "company.email.error");
		else if (res.getPhoneNumber().length() < 4)
			result = this.createEditModelAndView(companyForm, "company.phone.error");
		else if (this.creditCardService.checkCreditCard(res.getCreditCard()) == false)
			result = this.createEditModelAndView(companyForm, "company.creditCard.error");
		else
			try {
				this.companyService.save(res);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
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

		result = new ModelAndView("company/edit");

		result.addObject("company", companyForm);
		result.addObject("message", messageCode);
		return result;
	}
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public ModelAndView data() {
		ModelAndView result;

		final Company avg = this.companyService.findByPrincipal();

		result = new ModelAndView("company/data");
		result.addObject("company", avg);
		result.addObject("problems", avg.getProblems());
		result.addObject("positions", avg.getPositions());

		result.addObject("requestURI", "company/company/data.do");

		return result;

	}

	@RequestMapping(value = "/deleteAccount", method = RequestMethod.GET)
	public ModelAndView delete() {
		ModelAndView result;
		final Company company = this.companyService.findByPrincipal();
		result = new ModelAndView("redirect:/j_spring_security_logout");

		this.companyService.delete(company);
		//result = new ModelAndView("redirect:/j_spring_security_logout");
		return result;

	}
}
