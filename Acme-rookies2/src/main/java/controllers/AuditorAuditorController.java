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
import services.AuditorService;
import services.CreditCardService;
import domain.Auditor;
import forms.AuditorForm;

@Controller
@RequestMapping("/auditor/auditor")
public class AuditorAuditorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AuditorService		auditorService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CreditCardService	creditCardService;


	// Constructors -----------------------------------------------------------

	public AuditorAuditorController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		final Auditor auditor;
		AuditorForm auditorForm;

		auditor = this.auditorService.findByPrincipal();
		auditorForm = this.auditorService.deconstruct(auditor);
		result = this.createEditModelAndView(auditorForm);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("auditor") final AuditorForm auditorForm, final BindingResult binding) {
		ModelAndView result;
		Auditor res = null;
		try {
			res = this.auditorService.reconstruct(auditorForm, binding);
		} catch (final Throwable oops) {
		}
		if (binding.hasErrors()) {
			System.out.print(binding);
			result = this.createEditModelAndView(auditorForm);

		} else if (this.actorService.checkUserEmail(res.getEmail()) == false)
			result = this.createEditModelAndView(auditorForm, "auditor.email.error");
		else if (res.getPhoneNumber().length() < 4)
			result = this.createEditModelAndView(auditorForm, "auditor.phone.error");
		else if (this.creditCardService.checkCreditCard(res.getCreditCard()) == false)
			result = this.createEditModelAndView(auditorForm, "auditor.creditCard.error");
		else
			try {
				this.auditorService.save(res);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(auditorForm, "auditor.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndView(final AuditorForm auditorForm) {
		ModelAndView result;
		result = this.createEditModelAndView(auditorForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final AuditorForm auditorForm, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("auditor/edit");

		result.addObject("auditor", auditorForm);
		result.addObject("message", messageCode);
		return result;
	}
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public ModelAndView data() {
		ModelAndView result;

		final Auditor auditor = this.auditorService.findByPrincipal();

		result = new ModelAndView("auditor/data");
		result.addObject("auditor", auditor);
		result.addObject("audits", auditor.getAudits());

		result.addObject("requestURI", "auditor/auditor/data.do");

		return result;

	}

	@RequestMapping(value = "/deleteAccount", method = RequestMethod.GET)
	public ModelAndView delete() {
		ModelAndView result;
		final Auditor auditor = this.auditorService.findByPrincipal();
		result = new ModelAndView("redirect:/j_spring_security_logout");

		this.auditorService.delete(auditor);
		//result = new ModelAndView("redirect:/j_spring_security_logout");
		return result;

	}
}
