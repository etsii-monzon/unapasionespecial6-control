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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CommentService;
import services.ConferenceService;
import services.RegistrationService;
import services.SubmissionService;
import domain.Administrator;

@Controller
@RequestMapping("/administrator/administrator")
public class AdministratorAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConferenceService		confService;

	@Autowired
	private SubmissionService		submService;

	@Autowired
	private RegistrationService		regService;

	@Autowired
	private CommentService			commentSevice;


	// Constructors -----------------------------------------------------------

	public AdministratorAdministratorController() {
		super();
	}

	//Edición: Obtener los datos del admin-----------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		final Administrator administrator;

		administrator = this.administratorService.findByPrincipal();
		System.out.println(administrator.getPhoneNumber());
		result = this.createEditModelAndView(administrator);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Administrator adminsitrator, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding);
			result = this.createEditModelAndView(adminsitrator);
		} else
			try {
				System.out.println(adminsitrator.getPhoneNumber());
				this.administratorService.save(adminsitrator);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final DataIntegrityViolationException oops) {
				result = this.createEditModelAndView(adminsitrator, "administrator.username.error");
			} catch (final Throwable oops) {
				System.out.println(oops);
				if (oops.getMessage().equals("email error"))
					result = this.createEditModelAndView(adminsitrator, "administrator.email.error");
				else
					result = this.createEditModelAndView(adminsitrator, "administrator.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndView(final Administrator administrator) {
		ModelAndView result;
		result = this.createEditModelAndView(administrator, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator administrator, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("administrator/edit");

		result.addObject("administrator", administrator);
		result.addObject("message", messageCode);
		return result;
	}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final Double avgSubs = this.submService.avgSubmissionsPerConference();
		final Integer minSubs = this.submService.minSubmissionsPerConference();
		final Integer maxSubs = this.submService.maxSubmissionsPerConference();
		final Double stdSubs = this.submService.stdDevSubmissionsPerConference();

		final Double avgDays = this.confService.avgDaysPerConference();
		final Integer minDays = this.confService.minDaysPerConference();
		final Integer maxDays = this.confService.maxDaysPerConference();
		final Double stdDevDays = this.confService.stdDevDaysPerConference();

		final Double avgRegs = this.regService.avgRegistrationsPerConference();
		final Integer minRegs = this.regService.minRegistrationsPerConference();
		final Integer maxRegs = this.regService.maxRegistrationsPerConference();
		final Double stdRegs = this.regService.stdDevRegistrationsPerConference();

		final Double avgFees = this.confService.avgConferenceFees();
		final Double minFees = this.confService.minConferenceFees();
		final Double maxFees = this.confService.maxConferenceFees();
		final Double stdFees = this.confService.stdConferenceFees();

		final Double avgCommentsConf = this.commentSevice.avgNumberCommentsPerConference();
		final Double minCommentsConf = this.commentSevice.minNumberCommentsPerConference();
		final Double maxCommentsConf = this.commentSevice.maxNumberCommentsPerConference();
		final Double stdCommentConf = this.commentSevice.stdNumberCommentsPerConference();

		final Double avgCommentsAct = this.commentSevice.avgNumberCommentsPerActivity();
		final Double minCommentsAct = this.commentSevice.minNumberCommentsPerActivity();
		final Double maxCommentsAct = this.commentSevice.maxNumberCommentsPerActivity();
		final Double stdCommentsAct = this.commentSevice.stdNumberCommentsPerActivity();

		final Double avgConferecePerCategory = this.confService.avgConferencePerCategory();
		final Integer minConferecePerCategory = this.confService.minConferencePerCategory();
		final Integer maxConferecePerCategory = this.confService.maxConferencePerCategory();
		final Double stdConferecePerCategory = this.confService.stdDevConferencePerCategory();

		result = new ModelAndView("administrator/list");
		result.addObject("avgDays", avgDays);
		result.addObject("minDays", minDays);
		result.addObject("maxDays", maxDays);
		result.addObject("stdDevDays", stdDevDays);
		result.addObject("avgSubs", avgSubs);
		result.addObject("minSubs", minSubs);
		result.addObject("maxSubs", maxSubs);
		result.addObject("stdSubs", stdSubs);
		result.addObject("avgRegs", avgRegs);
		result.addObject("minRegs", minRegs);
		result.addObject("maxRegs", maxRegs);
		result.addObject("stdRegs", stdRegs);
		result.addObject("avgFees", avgFees);
		result.addObject("minFees", minFees);
		result.addObject("maxFees", maxFees);
		result.addObject("stdFees", stdFees);
		result.addObject("avgCommentsConf", avgCommentsConf);
		result.addObject("minCommentsConf", minCommentsConf);
		result.addObject("maxCommentsConf", maxCommentsConf);
		result.addObject("stdCommentConf", stdCommentConf);
		result.addObject("avgCommentsAct", avgCommentsAct);
		result.addObject("minCommentsAct", minCommentsAct);
		result.addObject("maxCommentsAct", maxCommentsAct);
		result.addObject("stdCommentsAct", stdCommentsAct);
		result.addObject("avgConferecePerCategory", avgConferecePerCategory);
		result.addObject("maxConferecePerCategory", maxConferecePerCategory);
		result.addObject("minConferecePerCategory", minConferecePerCategory);
		result.addObject("stdConferecePerCategory", stdConferecePerCategory);

		return result;
	}
}
