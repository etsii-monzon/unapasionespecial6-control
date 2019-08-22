
package controllers;

import java.util.Collection;

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
import domain.Category;
import domain.Conference;

@Controller
@RequestMapping("/conference/actor")
public class ConferenceActorController extends AbstractController {

	@Autowired
	ConferenceService		conferenceService;

	@Autowired
	AdministratorService	administratorService;

	@Autowired
	CategoryService			categoryService;


	@RequestMapping(value = "/listByCategory", method = RequestMethod.GET, params = "save")
	public ModelAndView listByCategory(@RequestParam final int categoryId) {
		ModelAndView result;
		System.out.println(categoryId);
		Collection<Conference> conferences;

		final Category c = this.categoryService.findOne(categoryId);
		if (c == null)
			conferences = this.conferenceService.findAll();
		else
			conferences = this.conferenceService.getConferencesByCategory(c);
		System.out.println(conferences);

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		result.addObject("languaje", languaje);
		result.addObject("requestURI", "conference/actor/listByCategory.do");

		return result;
	}
}
