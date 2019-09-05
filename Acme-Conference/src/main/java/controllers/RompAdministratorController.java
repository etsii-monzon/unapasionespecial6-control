
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
import services.RompService;
import domain.Romp;

@Controller
@RequestMapping(value = "/romp/administrator")
public class RompAdministratorController extends AbstractController {

	@Autowired
	private RompService				rompService;

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

		final Collection<Romp> romps = this.conferenceService.findOne(conferenceId).getRomps();

		result = new ModelAndView("romp/list");
		result.addObject("romps", romps);

		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		result.addObject("languaje", languaje);

		result.addObject("mes", mes);
		result.addObject("dosMeses", dosMeses);

		result.addObject("conferenceId", conferenceId);
		result.addObject("requestURI", "romp/administrator/list.do");
		result.addObject("admin", this.administartorService.findByPrincipal());

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int conferenceId) {
		ModelAndView result;
		Romp romp;

		romp = this.rompService.create();

		result = this.createEditModelAndView(romp, conferenceId);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int rompId) {
		ModelAndView result;
		Romp romp;
		romp = this.rompService.findOne(rompId);

		try {
			Assert.isTrue(romp.getAdministrator().equals(this.administartorService.findByPrincipal()), "hacking");
			Assert.isTrue(romp.isDraftMode(), "draft mode");
			result = this.createEditModelAndView(romp, this.conferenceService.getConferenceByRomp(rompId).getId());
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("hacking"))
				result = new ModelAndView("misc/403");
			else if (oops.getMessage().equals("draft mode"))
				result = new ModelAndView("misc/403");
			else
				result = this.createEditModelAndView(romp, "romp.commit.error");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Romp romp, final BindingResult binding, final HttpServletRequest req) {
		ModelAndView result;

		System.out.println(romp.getTicker());
		System.out.println(romp.getAdministrator());
		System.out.println(romp.getPublicationMoment());
		System.out.println(romp);

		final int conferenceId = Integer.parseInt(req.getParameter("conferenceId"));
		if (binding.hasErrors()) {
			System.out.println(binding);
			result = this.createEditModelAndView(romp, conferenceId);
		} else
			try {
				final Romp res = this.rompService.save(romp);
				if (romp.getId() == 0)
					this.conferenceService.findOne(conferenceId).getRomps().add(res);

				result = new ModelAndView("redirect:list.do?conferenceId=" + this.conferenceService.getConferenceByRomp(res.getId()).getId());

			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());

				result = this.createEditModelAndView(romp, "romp.commit.error");
			}
		return result;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int rompId) {
		ModelAndView result;
		try {
			final Romp romp = this.rompService.findOne(rompId);
			Assert.isTrue(romp.getAdministrator().equals(this.administartorService.findByPrincipal()), "hacking");
			result = new ModelAndView("redirect:list.do?conferenceId=" + this.conferenceService.getConferenceByRomp(romp.getId()).getId());
			this.rompService.delete(romp);

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
	protected ModelAndView createEditModelAndView(final Romp romp, final int conferenceId) {
		ModelAndView result;

		result = this.createEditModelAndView(romp, null);

		result.addObject("conferenceId", conferenceId);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Romp romp, final String messageCode) {
		final ModelAndView result;
		final String languaje = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("romp/edit");
		result.addObject("romp", romp);
		result.addObject("message", messageCode);
		result.addObject("languaje", languaje);

		result.addObject("requestURI", "romp/administrator/edit.do");

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int rompId) {
		ModelAndView result;
		final Romp romp;

		try {
			romp = this.rompService.findOne(rompId);
			final String languaje = LocaleContextHolder.getLocale().getLanguage();

			result = new ModelAndView("romp/show");
			result.addObject("requestURI", "romp/administrator/show.do");
			result.addObject("romp", romp);
			result.addObject("conferenceId", this.conferenceService.getConferenceByRomp(rompId).getId());
			result.addObject("languaje", languaje);

		} catch (final Throwable oops) {
			// TODO: handle exception

			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}
}
