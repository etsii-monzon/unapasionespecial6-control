
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ReportService;
import services.ReviewerService;
import services.SubmissionService;
import domain.Report;

@Controller
@RequestMapping("/report/administrator")
public class ReportAdministratorController extends AbstractController {

	@Autowired
	ReportService		reportService;

	@Autowired
	ReviewerService		reviewerService;
	@Autowired
	SubmissionService	submissionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int submissionId) {
		ModelAndView result;
		final Collection<Report> reports = new ArrayList<>();

		Collection<Report> reportsAll;

		reportsAll = this.reportService.findAll();
		for (final Report r : reportsAll)
			if (r.getSubmission().equals(this.submissionService.findOne(submissionId)))
				reports.add(r);

		result = new ModelAndView("report/list");
		result.addObject("reports", reports);
		result.addObject("requestURI", "report/administrator/list.do");

		return result;
	}

}
