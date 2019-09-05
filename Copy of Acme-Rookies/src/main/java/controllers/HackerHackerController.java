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
import services.CreditCardService;
import services.HackerService;
import domain.Hacker;
import forms.HackerForm;

@Controller
@RequestMapping("/rookie/rookie")
public class HackerHackerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private HackerService		hackerService;
	@Autowired
	private ActorService		actorService;

	@Autowired
	private CreditCardService	creditCardService;


	// Constructors -----------------------------------------------------------

	public HackerHackerController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		final Hacker hacker;
		HackerForm hackerForm;

		hacker = this.hackerService.findByPrincipal();
		hackerForm = this.hackerService.deconstruct(hacker);
		result = this.createEditModelAndView(hackerForm);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("hacker") final HackerForm hackerForm, final BindingResult binding) {
		ModelAndView result;
		Hacker res = null;
		try {
			res = this.hackerService.reconstruct(hackerForm, binding);
		} catch (final Throwable oops) {
		}
		if (binding.hasErrors()) {
			System.out.print(binding);
			result = this.createEditModelAndView(hackerForm);

		} else if (res.getPhoneNumber().length() < 4)
			result = this.createEditModelAndView(hackerForm, "hacker.phone.error");
		else if (this.actorService.checkUserEmail(res.getEmail()) == false)
			result = this.createEditModelAndView(hackerForm, "hacker.email.error");
		else if (this.creditCardService.checkCreditCard(res.getCreditCard()) == false)
			result = this.createEditModelAndView(hackerForm, "hacker.creditCard.error");
		else
			try {

				this.hackerService.save(res);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(hackerForm, "hacker.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndView(final HackerForm hackerForm) {
		ModelAndView result;
		result = this.createEditModelAndView(hackerForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final HackerForm hackerForm, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("rookie/edit");

		result.addObject("hacker", hackerForm);
		result.addObject("message", messageCode);
		return result;
	}

	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public ModelAndView getAdministrator() {
		ModelAndView result;

		final Hacker avg = this.hackerService.findByPrincipal();

		result = new ModelAndView("rookie/data");
		result.addObject("hacker", avg);
		result.addObject("applications", avg.getApplications());

		result.addObject("requestURI", "rookie/rookie/data.do");

		return result;

	}

	@RequestMapping(value = "/deleteAccount", method = RequestMethod.GET)
	public ModelAndView delete() {
		ModelAndView result;
		final Hacker hacker = this.hackerService.findByPrincipal();
		result = new ModelAndView("redirect:/j_spring_security_logout");

		this.hackerService.delete(hacker);
		//result = new ModelAndView("redirect:/j_spring_security_logout");
		return result;

	}

}
