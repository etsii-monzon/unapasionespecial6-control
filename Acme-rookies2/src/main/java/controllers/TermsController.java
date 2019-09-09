
package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/help")
public class TermsController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public TermsController() {
		super();
	}

	@RequestMapping(value = "/terms", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = new ModelAndView("help/terms");
		result.addObject("requestURI", "help/terms.do");

		return result;
	}

}
