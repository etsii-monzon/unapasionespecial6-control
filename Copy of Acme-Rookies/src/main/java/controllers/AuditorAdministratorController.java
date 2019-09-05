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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.AuditorService;
import services.CompanyService;
import services.CreditCardService;
import services.HackerService;
import services.PositionService;
import domain.Administrator;
import domain.Auditor;
import forms.AuditorForm;

@Controller
@RequestMapping("/auditor/administrator")
public class AuditorAdministratorController extends AbstractController {

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
	private AuditorService			auditorService;

	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------

	public AuditorAdministratorController() {
		super();
	}

	//Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		AuditorForm auditorForm;

		auditorForm = new AuditorForm();
		result = this.createEditModelAndView(auditorForm);

		return result;
	}
	// Edition ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("auditor") final AuditorForm auditorForm, final BindingResult binding) {
		ModelAndView result;
		Auditor res;
		res = this.auditorService.reconstruct(auditorForm, binding);

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(auditorForm);
		} else if (!(auditorForm.getConfirmPassword().equals(res.getUserAccount().getPassword())))
			result = this.createEditModelAndView(auditorForm, "auditor.password.error");
		else if (this.actorService.checkUserEmail(res.getEmail()) == false)
			result = this.createEditModelAndView(auditorForm, "auditor.email.error");
		else if (res.getPhoneNumber().length() < 4)
			result = this.createEditModelAndView(auditorForm, "auditor.phone.error");
		else if (this.creditCardService.checkCreditCard(res.getCreditCard()) == false)
			result = this.createEditModelAndView(auditorForm, "auditor.creditCard.error");
		else
			try {
				this.auditorService.save(res);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final DataIntegrityViolationException oops) {
				result = this.createEditModelAndView(auditorForm, "auditor.username.error");
			} catch (final Throwable oops) {
				System.out.println(oops);
				result = this.createEditModelAndView(auditorForm, "auditor.commit.error");
			}
		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final AuditorForm auditorForm) {
		ModelAndView result;

		result = this.createEditModelAndView(auditorForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final AuditorForm auditorForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("auditor/edit");
		result.addObject("auditor", auditorForm);

		result.addObject("message", message);

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
}
