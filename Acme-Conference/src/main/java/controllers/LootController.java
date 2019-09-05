
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ConferenceService;
import services.LootService;
import domain.Loot;

@Controller
@RequestMapping(value = "/loot/")
public class LootController extends AbstractController {

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
		final Collection<Loot> loots = new ArrayList<>();

		final Collection<Loot> lootsAll = this.conferenceService.findOne(conferenceId).getLoots();
		for (final Loot w : lootsAll)
			if (w.isDraftMode() == false)
				loots.add(w);

		result = new ModelAndView("loot/list");
		result.addObject("loots", loots);

		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		result.addObject("languaje", languaje);

		result.addObject("mes", mes);
		result.addObject("dosMeses", dosMeses);

		result.addObject("conferenceId", conferenceId);
		result.addObject("requestURI", "loot/list.do");
		//		result.addObject("admin", this.administartorService.findByPrincipal());

		return result;
	}

}
