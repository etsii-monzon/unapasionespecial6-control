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

import services.ReviewerService;
import domain.Reviewer;

@Controller
@RequestMapping("/reviewer/reviewer")
public class ReviewerReviewerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ReviewerService	reviewerService;


	// Constructors -----------------------------------------------------------

	public ReviewerReviewerController() {
		super();
	}

	//Edición: Obtener los datos del reviewer-----------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		final Reviewer reviewer;

		reviewer = this.reviewerService.findByPrincipal();
		result = this.createEditModelAndView(reviewer);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Reviewer reviewer, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding);
			result = this.createEditModelAndView(reviewer);
		} else
			try {
				this.reviewerService.save(reviewer);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final DataIntegrityViolationException oops) {
				result = this.createEditModelAndView(reviewer, "reviewer.username.error");
			} catch (final Throwable oops) {
				System.out.println(oops);
				if (oops.getMessage() == "email error")
					result = this.createEditModelAndView(reviewer, "reviewer.email.error");
				else
					result = this.createEditModelAndView(reviewer, "reviewer.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndView(final Reviewer reviewer) {
		ModelAndView result;
		result = this.createEditModelAndView(reviewer, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Reviewer reviewer, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("reviewer/edit");

		result.addObject("reviewer", reviewer);
		result.addObject("message", messageCode);
		return result;
	}

}
