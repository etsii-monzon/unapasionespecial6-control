
package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CategoryService;
import services.ConferenceService;
import domain.Conference;

@Controller
@RequestMapping("/conference/administrator")
public class ConferenceAdministratorController extends AbstractController {

	@Autowired
	ConferenceService		conferenceService;

	@Autowired
	AdministratorService	administratorService;
	@Autowired
	CategoryService			categoryService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Conference> conferences;
		final Date fecha = new GregorianCalendar().getTime();

		conferences = this.conferenceService.findAll();

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("fecha", fecha);
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		result.addObject("languaje", languaje);

		result.addObject("requestURI", "conference/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/listst", method = RequestMethod.GET)
	public ModelAndView list1() {
		ModelAndView result;
		Collection<Conference> conferencesAll;
		final Collection<Conference> conferences = new ArrayList<>();

		conferencesAll = this.conferenceService.findAll();
		for (final Conference c : conferencesAll)
			if (c.getStartDate().after(new Date()) && c.getStartDate().before(this.conferenceService.variarFecha(new Date(), Calendar.DAY_OF_YEAR, 5)))
				conferences.add(c);

		result = new ModelAndView("conference/listst");
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		result.addObject("languaje", languaje);
		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/administrator/listst.do");

		return result;
	}
	@RequestMapping(value = "/listnot", method = RequestMethod.GET)
	public ModelAndView list2() {
		ModelAndView result;
		Collection<Conference> conferencesAll;
		final Collection<Conference> conferences = new ArrayList<>();

		conferencesAll = this.conferenceService.findAll();
		for (final Conference c : conferencesAll)
			if (c.getNotificationDeadline().after(new Date()) && c.getNotificationDeadline().before(this.conferenceService.variarFecha(new Date(), Calendar.DAY_OF_YEAR, 5)))
				conferences.add(c);

		result = new ModelAndView("conference/listnot");
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		result.addObject("languaje", languaje);
		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/administrator/listnot.do");

		return result;
	}
	@RequestMapping(value = "/listcam", method = RequestMethod.GET)
	public ModelAndView list3() {
		ModelAndView result;
		Collection<Conference> conferencesAll;
		final Collection<Conference> conferences = new ArrayList<>();

		conferencesAll = this.conferenceService.findAll();
		for (final Conference c : conferencesAll)
			if (c.getCameraDeadline().after(new Date()) && c.getCameraDeadline().before(this.conferenceService.variarFecha(new Date(), Calendar.DAY_OF_YEAR, 5)))
				conferences.add(c);

		result = new ModelAndView("conference/listcam");
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		result.addObject("languaje", languaje);
		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/administrator/listcam.do");

		return result;
	}
	@RequestMapping(value = "/listsub", method = RequestMethod.GET)
	public ModelAndView list4() {
		ModelAndView result;
		Collection<Conference> conferencesAll;
		final Collection<Conference> conferences = new ArrayList<>();

		conferencesAll = this.conferenceService.findAll();
		for (final Conference c : conferencesAll)
			if (c.getSubmissionDeadline().before(new Date()) && c.getSubmissionDeadline().after(this.conferenceService.variarFecha(new Date(), Calendar.DAY_OF_YEAR, -5)))
				conferences.add(c);

		result = new ModelAndView("conference/listsub");
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		result.addObject("languaje", languaje);
		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/administrator/listsub.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Conference conference;

		conference = this.conferenceService.create();

		result = this.createEditModelAndView(conference);

		return result;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int conferenceId) {
		ModelAndView result;
		final Conference conference;
		conference = this.conferenceService.findOne(conferenceId);

		try {
			Assert.notNull(conference);
			Assert.isTrue(conference.isDraftMode(), "final mode");

			result = this.createEditModelAndView(conference);

		} catch (final Throwable oops) {
			// TODO: handle exception
			if (oops.getMessage().equals("final mode"))
				result = new ModelAndView("misc/403");
			else
				return new ModelAndView("redirect:/");
		}

		return result;
	}
	protected ModelAndView createEditModelAndView(final Conference conference) {
		ModelAndView result;

		result = this.createEditModelAndView(conference, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Conference conference, final String messageCode) {
		final ModelAndView result;
		final String languaje = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("conference/edit");
		result.addObject("conference", conference);
		result.addObject("categories", this.categoryService.findAll());
		result.addObject("languaje", languaje);

		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Conference conference, final BindingResult binding) {
		ModelAndView result;
		System.out.println(conference.getCameraDeadline());
		if (binding.hasErrors()) {
			System.out.print(binding);
			result = this.createEditModelAndView(conference);

		} else if (conference.getSubmissionDeadline().after(conference.getNotificationDeadline()) || conference.getSubmissionDeadline().equals(conference.getNotificationDeadline()))
			result = this.createEditModelAndView(conference, "conferencesub.commit.error");
		else if (conference.getNotificationDeadline().after(conference.getCameraDeadline()) || conference.getNotificationDeadline().equals(conference.getCameraDeadline()))
			result = this.createEditModelAndView(conference, "conferencenot.commit.error");
		else if (conference.getCameraDeadline().after(conference.getStartDate()) || conference.getCameraDeadline().equals(conference.getStartDate()))
			result = this.createEditModelAndView(conference, "conferencecam.commit.error");
		else if (conference.getStartDate().after(conference.getEndDate()) || conference.getStartDate().equals(conference.getEndDate()))
			result = this.createEditModelAndView(conference, "conferencest.commit.error");
		else
			try {
				System.out.print("Entra");

				this.conferenceService.save(conference);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.print(oops);

				result = this.createEditModelAndView(conference, "conference.commit.error");
			}
		return result;
	}
	//delete

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Conference conference, final BindingResult binding) {
		ModelAndView result;
		try {
			Assert.isTrue(conference.isDraftMode(), "final mode");

			this.conferenceService.delete(conference);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			System.out.println(oops);
			if (oops.getMessage().equals("final mode"))
				result = this.createEditModelAndView(conference, "conference.delete.error");
			else
				result = this.createEditModelAndView(conference, "conference.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int conferenceId) {
		final ModelAndView result;
		final Conference conference;
		final String languaje = LocaleContextHolder.getLocale().getLanguage();

		conference = this.conferenceService.findOne(conferenceId);

		result = new ModelAndView("conference/show");
		result.addObject("requestURI", "conference/administrator/show.do");
		result.addObject("conference", conference);
		result.addObject("languaje", languaje);

		return result;
	}

}
