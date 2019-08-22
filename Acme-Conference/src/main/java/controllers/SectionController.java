
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SectionService;
import services.TutorialService;
import domain.Section;
import domain.Tutorial;

@Controller
@RequestMapping("/section")
public class SectionController extends AbstractController {

	@Autowired
	private SectionService	secService;

	@Autowired
	private TutorialService	tutService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tutorialId) {
		ModelAndView result;
		final Collection<Section> sections;
		final Tutorial tut = this.tutService.findOne(tutorialId);

		sections = this.secService.findAllByTutorial(tut);

		result = new ModelAndView("section/list");

		result.addObject("sections", sections);
		result.addObject("tutorialId", tutorialId);
		result.addObject("conferenceId", tut.getConference().getId());
		result.addObject("requestURI", "section/list.do");

		return result;
	}

}
