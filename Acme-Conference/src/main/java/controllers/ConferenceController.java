
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CategoryService;
import services.ConferenceService;
import domain.Conference;

@Controller
@RequestMapping("/conference")
public class ConferenceController extends AbstractController {

	@Autowired
	ConferenceService		conferenceService;

	@Autowired
	AdministratorService	administratorService;

	@Autowired
	CategoryService			categoryService;


	@RequestMapping(value = "/listProx", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Conference> conferencesAll;
		final Collection<Conference> conferences = new ArrayList<>();

		conferencesAll = this.conferenceService.findAll();
		for (final Conference c : conferencesAll)
			if (c.getStartDate().after(new Date()) && c.isDraftMode() == false)
				conferences.add(c);

		result = new ModelAndView("conference/listProx");
		result.addObject("conferences", conferences);
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		result.addObject("languaje", languaje);
		result.addObject("requestURI", "conference/listProx.do");

		return result;
	}

	@RequestMapping(value = "/listPast", method = RequestMethod.GET)
	public ModelAndView listp() {
		ModelAndView result;
		Collection<Conference> conferencesAll;
		final Collection<Conference> conferences = new ArrayList<>();

		conferencesAll = this.conferenceService.findAll();
		for (final Conference c : conferencesAll)
			if (c.getEndDate().before(new Date()) && c.isDraftMode() == false)
				conferences.add(c);

		result = new ModelAndView("conference/listPast");
		result.addObject("conferences", conferences);
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		result.addObject("languaje", languaje);
		result.addObject("requestURI", "conference/listPast.do");

		return result;
	}

	@RequestMapping(value = "/listEjec", method = RequestMethod.GET)
	public ModelAndView liste() {
		ModelAndView result;
		Collection<Conference> conferencesAll;
		final Collection<Conference> conferences = new ArrayList<>();

		conferencesAll = this.conferenceService.findAll();
		for (final Conference c : conferencesAll)
			if (c.getStartDate().before(new Date()) && c.getEndDate().after(new Date()) && c.isDraftMode() == false)
				conferences.add(c);

		result = new ModelAndView("conference/listEjec");
		result.addObject("conferences", conferences);
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		result.addObject("languaje", languaje);
		result.addObject("requestURI", "conference/listEjec.do");

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView res;
		res = new ModelAndView("conference/search");

		res.addObject("categories", this.categoryService.findAll());
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		res.addObject("languaje", languaje);

		return res;
	}

	@RequestMapping(value = "/searchList", method = RequestMethod.GET, params = "search")
	public ModelAndView searchList(@RequestParam final String keyword) {
		ModelAndView result;

		final Collection<Conference> conferences = this.conferenceService.searchConferenceByKeyword(keyword);

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		result.addObject("languaje", languaje);

		result.addObject("requestURI", "conference/searchList.do");

		return result;
	}

}
