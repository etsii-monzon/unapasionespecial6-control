
package controllers;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SubmissionService;
import domain.Reviewer;
import domain.Submission;

@Controller
@RequestMapping("/submission/administrator")
public class SubmissionAdministratorController extends AbstractController {

	@Autowired
	private SubmissionService	submissionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Submission> submissions;

		submissions = this.submissionService.getSubmissionGroupedByStatus();

		result = new ModelAndView("submission/list");

		result.addObject("submissions", submissions);
		result.addObject("now", new GregorianCalendar().getTime());

		result.addObject("requestURI", "submission/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/assignReviewers", method = RequestMethod.GET)
	public ModelAndView assignReviewers() {
		ModelAndView result;

		this.submissionService.assignReviewers();

		result = new ModelAndView("redirect:list.do");

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int submissionId) {
		ModelAndView result;
		final Submission submission;
		try {
			submission = this.submissionService.findOne(submissionId);
			result = new ModelAndView("submission/show");
			result.addObject("requestURI", "submission/administrator/show.do");
			result.addObject("submission", submission);
		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/");
		}
		return result;
	}
	@RequestMapping(value = "/assign", method = RequestMethod.GET)
	public ModelAndView assignManually(@RequestParam final int submissionId) {
		ModelAndView result;
		final Date hoy = new Date();

		try {
			final Submission sub = this.submissionService.findOne(submissionId);
			Assert.isTrue(sub.getStatus().equals("UNDER-REVIEW"), "status error");
			Assert.isTrue(sub.getReviewers().isEmpty(), "status error");
			Assert.isTrue(sub.getConference().getNotificationDeadline().after(hoy), "fecha pasada");
			Assert.isTrue(sub.getConference().getSubmissionDeadline().before(hoy), "fecha pasada");
			//Assert.isTrue(this.adminService.findByPrincipal().getConferences().equals(sub.getConference()), "hacking");

			result = this.createAssignModelAndView(sub);
		} catch (final Throwable oops) {
			if (oops.getMessage() == "status error")
				result = new ModelAndView("misc/403");
			else if (oops.getMessage().equals("fecha pasada"))
				result = new ModelAndView("misc/403");
			else {
				System.out.println(oops.getMessage() + " " + oops.getCause());
				result = new ModelAndView("redirect:/");
			}
		}

		return result;
	}
	protected ModelAndView createAssignModelAndView(final Submission submission) {
		ModelAndView result;

		result = this.createAssignModelAndView(submission, null);

		return result;
	}

	protected ModelAndView createAssignModelAndView(final Submission submission, final String messageCode) {
		final ModelAndView result;
		final Collection<Reviewer> reviewers = this.submissionService.findAllReviewersNotSubmission(submission.getId());
		//final Collection<Reviewer> reviewers = this.revService.findAll();

		result = new ModelAndView("submission/assign");
		Assert.isTrue(!reviewers.isEmpty(), "error reviewers");
		result.addObject("submission", submission);
		result.addObject("reviewers", reviewers);

		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/assign", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Submission submission, final BindingResult binding) throws NullPointerException {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.print(binding);
			result = this.createAssignModelAndView(submission);

		} else
			try {
				System.out.print("Entra");

				this.submissionService.save(submission);
				//Assert.isTrue(this.adminService.findByPrincipal().getConferences().contains(sub.getConference()), "hacking");

				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.print(oops);
				if (oops.getMessage() == "no revs" || oops.getClass().equals(java.lang.NullPointerException.class))
					result = this.createAssignModelAndView(submission, "submission.reviewers.error");
				else {
					System.out.println("" + oops.getMessage());
					result = this.createAssignModelAndView(submission, "submission.commit.error");
				}
			}
		return result;
	}

	@RequestMapping(value = "/decisionMaking", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int submissionId) {
		ModelAndView result;
		final Date hoy = new Date();

		try {

			final Submission submission = this.submissionService.findOne(submissionId);
			Assert.isTrue(submission.getStatus().equals("UNDER-REVIEW"), "status error");
			Assert.isTrue(submission.getConference().getNotificationDeadline().after(hoy), "fecha pasada");
			Assert.isTrue(submission.getConference().getSubmissionDeadline().before(hoy), "fecha pasada");

			this.submissionService.submissionStatus(submissionId);

			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {

			if (oops.getMessage().equals("status error"))
				result = new ModelAndView("misc/403");
			else if (oops.getMessage().equals("fecha pasada"))
				result = new ModelAndView("misc/403");
			else
				result = new ModelAndView("redirect:/");
		}

		return result;
	}

}
