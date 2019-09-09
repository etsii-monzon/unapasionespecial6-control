
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
import services.CreditCardService;
import services.HackerService;
import domain.Hacker;
import forms.HackerForm;

@Controller
@RequestMapping("/rookie")
public class HackerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private HackerService		hackerService;

	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public HackerController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final HackerForm hackerForm = new HackerForm();

		result = this.createEditModelAndView(hackerForm);

		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("hacker") final HackerForm hackerForm, final BindingResult binding) {
		ModelAndView result;
		Hacker res;
		res = this.hackerService.reconstruct(hackerForm, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(hackerForm);
		else if (!(hackerForm.getConfirmPassword().equals(res.getUserAccount().getPassword())))
			result = this.createEditModelAndView(hackerForm, "hacker.password.error");
		else if (this.actorService.checkUserEmail(res.getEmail()) == false)
			result = this.createEditModelAndView(hackerForm, "hacker.email.error");
		else if (res.getPhoneNumber().length() < 4)
			result = this.createEditModelAndView(hackerForm, "hacker.phone.error");
		else if (this.creditCardService.checkCreditCard(res.getCreditCard()) == false)
			result = this.createEditModelAndView(hackerForm, "hacker.creditCard.error");
		else
			try {
				this.hackerService.save(res);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final DataIntegrityViolationException oops) {
				result = this.createEditModelAndView(hackerForm, "hacker.username.error");
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

		result = new ModelAndView("rookie/create");

		result.addObject("hacker", hackerForm);
		result.addObject("message", messageCode);
		return result;
	}
}
