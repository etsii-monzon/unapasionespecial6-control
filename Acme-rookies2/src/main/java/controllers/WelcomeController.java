/*
 * 
 * WelcomeController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.AdministratorService;
import services.CompanyService;
import services.ConfigurationService;
import services.HackerService;
import domain.Actor;
import domain.Configuration;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private HackerService			hackerService;
	@Autowired
	private CompanyService			companyService;
	@Autowired
	private AdministratorService	adminService;
	@Autowired
	private ActorService			actorService;

	@Autowired
	private LoginService			loginService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		String mensaje = new String();
		String name = "";
		Boolean notify = true;

		final String languaje = LocaleContextHolder.getLocale().getLanguage();

		if (languaje.equals("en"))
			mensaje = this.configurationService.findWelcomeEN();
		else if (languaje.equals("es"))
			mensaje = this.configurationService.findWelcomeSP();

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		result = new ModelAndView("welcome/index");
		final Configuration c = this.configurationService.find();

		if (LoginService.hasRole("ROOKIE") || LoginService.hasRole("ADMIN") || LoginService.hasRole("COMPANY") || LoginService.hasRole("PROVIDER") || LoginService.hasRole("AUDITOR")) {
			name = this.actorService.findByPrincipal().getName();
			if (this.actorService.findByPrincipal().getUserAccount().isNotify() == false && this.configurationService.find().isReBrand() == true) {
				notify = false;
				final Actor h = this.actorService.findByPrincipal();
				h.getUserAccount().setNotify(true);
				this.actorService.save(h);
			}
		}

		result.addObject("name", name);
		result.addObject("reBrand", c.isReBrand());
		result.addObject("isNotify", notify);
		result.addObject("moment", moment);
		result.addObject("mensaje", mensaje);
		return result;

	}
}
