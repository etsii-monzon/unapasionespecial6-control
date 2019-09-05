
package controllers;

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
import services.RompService;
import domain.Romp;

@Controller
@RequestMapping(value = "/romp/")
public class RompController extends AbstractController {

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

		final Collection<Romp> romps = this.rompService.getFinalRomps(conferenceId);

		result = new ModelAndView("romp/list");
		result.addObject("romps", romps);

		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		result.addObject("languaje", languaje);

		result.addObject("mes", mes);
		result.addObject("dosMeses", dosMeses);

		result.addObject("conferenceId", conferenceId);
		result.addObject("requestURI", "romp/list.do");
		//		result.addObject("admin", this.administartorService.findByPrincipal());

		return result;
	}

}
