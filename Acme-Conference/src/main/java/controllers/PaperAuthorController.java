
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import services.PaperService;
import services.SubmissionService;
import domain.Paper;

@Controller
@RequestMapping("/paper/author")
public class PaperAuthorController extends AbstractController {

	@Autowired
	PaperService		paperService;

	@Autowired
	AuthorService		authorService;
	@Autowired
	SubmissionService	submissionService;


	@RequestMapping(value = "/showp", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int submissionId) {
		ModelAndView result;
		final Paper paper;

		try {
			paper = this.submissionService.findOne(submissionId).getPaper();
			Assert.isTrue(this.authorService.findByPrincipal().getSubmissions().contains(this.submissionService.findOne(submissionId)), "hacking");

			result = new ModelAndView("paper/showp");
			result.addObject("requestURI", "paper/author/showp.do");
			result.addObject("paper", paper);
		} catch (final Throwable oops) {
			// TODO: handle exception
			if (oops.getMessage().equals("hacking"))
				result = new ModelAndView("misc/403");
			else
				result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

}
