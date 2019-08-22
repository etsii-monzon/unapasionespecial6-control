
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PaperService;
import services.ReviewerService;
import services.SubmissionService;
import domain.Paper;

@Controller
@RequestMapping("/paper/reviewer")
public class PaperReviewerController extends AbstractController {

	@Autowired
	PaperService		paperService;

	@Autowired
	ReviewerService		reviewerService;
	@Autowired
	SubmissionService	submissionService;


	@RequestMapping(value = "/showp", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int submissionId) {
		ModelAndView result;
		try {

			final Paper paper;

			paper = this.submissionService.findOne(submissionId).getPaper();
			Assert.isTrue(this.submissionService.findOne(submissionId).getReviewers().contains(this.reviewerService.findByPrincipal()), "hacking");
			result = new ModelAndView("paper/showp");
			result.addObject("requestURI", "paper/reviewer/showp.do");
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
