
package controllers;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConferenceService;
import services.PanelService;
import services.PresentationService;
import services.SubmissionService;
import services.TutorialService;
import domain.Conference;
import domain.Panel;
import domain.Presentation;
import domain.Submission;
import domain.Tutorial;

@Controller
@RequestMapping("/activity")
public class ActivityController extends AbstractController {

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private PanelService		panelService;

	@Autowired
	private PresentationService	presentationService;

	@Autowired
	private ConferenceService	confService;

	@Autowired
	private SubmissionService	submissionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int conferenceId) {
		final Conference conf = this.confService.findOne(conferenceId);
		final Collection<Tutorial> tutorials = this.tutorialService.findAllTutorialsByConference(conf);
		final Collection<Panel> panels = this.panelService.findAllPanelsByConference(conf);
		final Collection<Presentation> presents = this.presentationService.findAllPresentationsByConference(conf);
		final Date fecha = new GregorianCalendar().getTime();
		ModelAndView result;

		final Collection<Submission> subms = this.submissionService.findAllCameraReadyVersion(conferenceId);
		final Boolean allowed = !subms.isEmpty();

		System.out.println(panels);

		result = new ModelAndView("activity/list");
		result.addObject("conference", conf);
		result.addObject("conferenceId", conferenceId);
		result.addObject("tutorials", tutorials);
		result.addObject("panels", panels);
		result.addObject("presentations", presents);
		result.addObject("allowed", allowed);
		result.addObject("fecha", fecha);
		result.addObject("requestURI", "activity/list.do");
		result.addObject("type", this.confService.checkType(conferenceId));

		return result;

	}

}
