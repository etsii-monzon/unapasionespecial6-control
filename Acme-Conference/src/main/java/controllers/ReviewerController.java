
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ReviewerService;
import domain.Reviewer;

@Controller
@RequestMapping("/reviewer")
public class ReviewerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ReviewerService	reviewerService;


	// Constructors -----------------------------------------------------------

	public ReviewerController() {
		super();
	}

	// Creación: Crear nuevo Reviewer ---------------------------------------------------------------		
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		final ModelAndView result;
		final Reviewer reviewer = this.reviewerService.create();

		result = this.createEditModelAndView(reviewer);
		result.addObject("reviewer", reviewer);

		return result;
	}

	// Edición: Botón Save ---------------------------------------------------------------		
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

			} catch (final Throwable oops) {
				System.out.println(oops);
				if (oops.getMessage().equals("email error"))
					result = this.createEditModelAndView(reviewer, "reviewer.email.error");
				else if (oops.getMessage().equals("username error"))
					result = this.createEditModelAndView(reviewer, "reviewer.username.error");
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

		result = new ModelAndView("reviewer/create");

		result.addObject("reviewer", reviewer);
		result.addObject("message", messageCode);
		return result;
	}

}
