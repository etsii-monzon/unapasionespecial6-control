/*
 * WelcomeController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
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
import services.ConfigurationService;
import domain.Configuration;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	@Autowired
	private ConfigurationService	configurationService;

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

		final String name = "";
		final Boolean notify = true;
		//	final Actor actor;

		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		//actor = this.actorService.findByPrincipal();

		if (languaje.equals("en"))
			mensaje = this.configurationService.findWelcomeEN();
		else if (languaje.equals("es"))
			mensaje = this.configurationService.findWelcomeSP();

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		result = new ModelAndView("welcome/index");
		final Configuration c = this.configurationService.find();

		result.addObject("name", name);
		result.addObject("moment", moment);
		//	result.addObject("actor", actor);
		result.addObject("mensaje", mensaje);

		return result;

	}
}
