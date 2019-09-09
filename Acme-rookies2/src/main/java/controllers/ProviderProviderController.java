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
import services.ProviderService;
import domain.Provider;
import forms.ProviderForm;

@Controller
@RequestMapping("/provider/provider")
public class ProviderProviderController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ProviderService		providerService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CreditCardService	creditCardService;


	// Constructors -----------------------------------------------------------

	public ProviderProviderController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		final Provider provider;
		ProviderForm providerForm;

		provider = this.providerService.findByPrincipal();
		providerForm = this.providerService.deconstruct(provider);
		result = this.createEditModelAndView(providerForm);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("provider") final ProviderForm providerForm, final BindingResult binding) {
		ModelAndView result;
		Provider res = null;
		try {
			res = this.providerService.reconstruct(providerForm, binding);
		} catch (final Throwable oops) {
		}
		if (binding.hasErrors()) {
			System.out.print(binding);
			result = this.createEditModelAndView(providerForm);

		} else if (res.getPhoneNumber().length() < 4)
			result = this.createEditModelAndView(providerForm, "provider.phone.error");
		else if (this.actorService.checkUserEmail(res.getEmail()) == false)
			result = this.createEditModelAndView(providerForm, "provider.email.error");
		else if (this.creditCardService.checkCreditCard(res.getCreditCard()) == false)
			result = this.createEditModelAndView(providerForm, "provider.creditCard.error");
		else
			try {
				this.providerService.save(res);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(providerForm, "provider.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndView(final ProviderForm providerForm) {
		ModelAndView result;
		result = this.createEditModelAndView(providerForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final ProviderForm providerForm, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("provider/edit");

		result.addObject("provider", providerForm);
		result.addObject("message", messageCode);
		return result;
	}
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public ModelAndView data() {
		ModelAndView result;

		final Provider provider = this.providerService.findByPrincipal();

		result = new ModelAndView("provider/data");
		result.addObject("provider", provider);
		result.addObject("items", provider.getItems());

		result.addObject("requestURI", "provider/provider/data.do");

		return result;

	}

	@RequestMapping(value = "/deleteAccount", method = RequestMethod.GET)
	public ModelAndView delete() {
		ModelAndView result;
		final Provider provider = this.providerService.findByPrincipal();
		result = new ModelAndView("redirect:/j_spring_security_logout");

		this.providerService.delete(provider);
		//result = new ModelAndView("redirect:/j_spring_security_logout");
		return result;

	}
}
