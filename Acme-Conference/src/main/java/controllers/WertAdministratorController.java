
package controllers;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.DateTime;
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
import services.ConferenceService;
import services.WertService;
import domain.Wert;

@Controller
@RequestMapping(value = "/wert/administrator")
public class WertAdministratorController extends AbstractController {

	@Autowired
	private WertService				wertService;

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private AdministratorService	administartorService;


	//To open the view to edit-----------------
	//Categories
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final int conferenceId) {
		ModelAndView result;
		final Date mes = new DateTime().minusMonths(1).toDate();
		final Date dosMeses = new DateTime().minusMonths(2).toDate();

		final Collection<Wert> werts = this.conferenceService.findOne(conferenceId).getWerts();

		result = new ModelAndView("wert/list");
		result.addObject("werts", werts);

		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		result.addObject("languaje", languaje);

		result.addObject("mes", mes);
		result.addObject("dosMeses", dosMeses);

		result.addObject("conferenceId", conferenceId);
		result.addObject("requestURI", "wert/administrator/list.do");
		result.addObject("admin", this.administartorService.findByPrincipal());

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int conferenceId) {
		ModelAndView result;
		Wert wert;

		wert = this.wertService.create();

		result = this.createEditModelAndView(wert, conferenceId);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int wertId) {
		ModelAndView result;
		Wert wert;
		wert = this.wertService.findOne(wertId);

		try {
			Assert.isTrue(wert.getAdministrator().equals(this.administartorService.findByPrincipal()), "hacking");
			Assert.isTrue(wert.isDraftMode(), "draft mode");
			result = this.createEditModelAndView(wert, this.conferenceService.getConferenceByWert(wertId).getId());
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("hacking"))
				result = new ModelAndView("misc/403");
			else if (oops.getMessage().equals("draft mode"))
				result = new ModelAndView("misc/403");
			else
				result = this.createEditModelAndView(wert, "wert.commit.error");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Wert wert, final BindingResult binding, final HttpServletRequest req) {
		ModelAndView result;

		System.out.println(wert.getTicker());
		System.out.println(wert.getAdministrator());
		System.out.println(wert.getPublicationMoment());
		System.out.println(wert);

		final int conferenceId = Integer.parseInt(req.getParameter("conferenceId"));
		if (binding.hasErrors()) {
			System.out.println(binding);
			result = this.createEditModelAndView(wert, conferenceId);
		} else
			try {
				final Wert res = this.wertService.save(wert);
				if (wert.getId() == 0)
					this.conferenceService.findOne(conferenceId).getWerts().add(res);

				result = new ModelAndView("redirect:list.do?conferenceId=" + this.conferenceService.getConferenceByWert(res.getId()).getId());

			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());

				result = this.createEditModelAndView(wert, "wert.commit.error");
			}
		return result;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int wertId) {
		ModelAndView result;
		try {
			final Wert wert = this.wertService.findOne(wertId);
			Assert.isTrue(wert.getAdministrator().equals(this.administartorService.findByPrincipal()), "hacking");
			result = new ModelAndView("redirect:list.do?conferenceId=" + this.conferenceService.getConferenceByWert(wert.getId()).getId());
			this.wertService.delete(wert);

			return result;
		} catch (final Throwable oops) {
			// TODO: handle exception
			if (oops.getMessage().equals("draft mode"))
				result = new ModelAndView("misc/403");
			else if (oops.getMessage().equals("hacking"))
				result = new ModelAndView("misc/403");
			else
				result = new ModelAndView("redirect:/");
		}
		return result;
	}
	protected ModelAndView createEditModelAndView(final Wert wert, final int conferenceId) {
		ModelAndView result;

		result = this.createEditModelAndView(wert, null);

		result.addObject("conferenceId", conferenceId);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Wert wert, final String messageCode) {
		final ModelAndView result;
		final String languaje = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("wert/edit");
		result.addObject("wert", wert);
		result.addObject("message", messageCode);
		result.addObject("languaje", languaje);

		result.addObject("requestURI", "wert/administrator/edit.do");

		return result;
	}
}
