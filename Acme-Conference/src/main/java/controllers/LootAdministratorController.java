
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
import services.LootService;
import domain.Loot;

@Controller
@RequestMapping(value = "/loot/administrator")
public class LootAdministratorController extends AbstractController {

	@Autowired
	private LootService				lootService;

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

		final Collection<Loot> loots = this.conferenceService.findOne(conferenceId).getLoots();

		result = new ModelAndView("loot/list");
		result.addObject("loots", loots);

		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		result.addObject("languaje", languaje);

		result.addObject("mes", mes);
		result.addObject("dosMeses", dosMeses);

		result.addObject("conferenceId", conferenceId);
		result.addObject("requestURI", "loot/administrator/list.do");
		result.addObject("admin", this.administartorService.findByPrincipal());

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int conferenceId) {
		ModelAndView result;
		Loot loot;

		loot = this.lootService.create();

		result = this.createEditModelAndView(loot, conferenceId);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int lootId) {
		ModelAndView result;
		Loot loot;
		loot = this.lootService.findOne(lootId);

		try {
			Assert.isTrue(loot.getAdministrator().equals(this.administartorService.findByPrincipal()), "hacking");
			Assert.isTrue(loot.isDraftMode(), "draft mode");
			result = this.createEditModelAndView(loot, this.conferenceService.getConferenceByLoot(lootId).getId());
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("hacking"))
				result = new ModelAndView("misc/403");
			else if (oops.getMessage().equals("draft mode"))
				result = new ModelAndView("misc/403");
			else
				result = this.createEditModelAndView(loot, "loot.commit.error");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Loot loot, final BindingResult binding, final HttpServletRequest req) {
		ModelAndView result;

		System.out.println(loot.getTicker());
		System.out.println(loot.getAdministrator());
		System.out.println(loot.getPublicationMoment());
		System.out.println(loot);

		final int conferenceId = Integer.parseInt(req.getParameter("conferenceId"));
		if (binding.hasErrors()) {
			System.out.println(binding);
			result = this.createEditModelAndView(loot, conferenceId);
		} else
			try {
				final Loot res = this.lootService.save(loot);
				if (loot.getId() == 0)
					this.conferenceService.findOne(conferenceId).getLoots().add(res);

				result = new ModelAndView("redirect:list.do?conferenceId=" + this.conferenceService.getConferenceByLoot(res.getId()).getId());

			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());

				result = this.createEditModelAndView(loot, "loot.commit.error");
			}
		return result;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int lootId) {
		ModelAndView result;
		try {
			final Loot loot = this.lootService.findOne(lootId);
			Assert.isTrue(loot.getAdministrator().equals(this.administartorService.findByPrincipal()), "hacking");
			result = new ModelAndView("redirect:list.do?conferenceId=" + this.conferenceService.getConferenceByLoot(loot.getId()).getId());
			this.lootService.delete(loot);

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
	protected ModelAndView createEditModelAndView(final Loot loot, final int conferenceId) {
		ModelAndView result;

		result = this.createEditModelAndView(loot, null);

		result.addObject("conferenceId", conferenceId);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Loot loot, final String messageCode) {
		final ModelAndView result;
		final String languaje = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("loot/edit");
		result.addObject("loot", loot);
		result.addObject("message", messageCode);
		result.addObject("languaje", languaje);

		result.addObject("requestURI", "loot/administrator/edit.do");

		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int lootId) {
		final ModelAndView result;
		final Loot loot;

		loot = this.lootService.findOne(lootId);

		result = new ModelAndView("loot/show");
		result.addObject("requestURI", "loot/administrator/show.do");
		result.addObject("loot", loot);

		return result;
	}
}
