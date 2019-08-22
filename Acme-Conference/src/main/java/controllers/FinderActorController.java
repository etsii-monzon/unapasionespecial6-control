
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CategoryService;
import services.ConferenceService;
import services.FinderService;
import domain.Category;
import domain.Finder;

@Controller
@RequestMapping("/finder/actor")
public class FinderActorController extends AbstractController {

	@Autowired
	ConferenceService	conferenceService;

	@Autowired
	ActorService		actorService;

	@Autowired
	FinderService		finderService;

	@Autowired
	CategoryService		categoryService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Finder finder;
		finder = this.actorService.findByPrincipal().getFinder();

		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		final Collection<Category> categories = this.categoryService.findAll();

		result = new ModelAndView("conference/list");
		result.addObject("categories", categories);
		result.addObject("languaje", languaje);
		result.addObject("conferences", finder.getConferences());

		result.addObject("requestURI", "finder/actor/list.do");
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Finder finder;
		finder = this.actorService.findByPrincipal().getFinder();
		try {
			result = this.createEditModelAndView(finder);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(finder, "finder.commit.error");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println(binding);
			result = this.createEditModelAndView(finder);
		} else
			try {
				if (finder.getStartDate() != null && finder.getEndDate() != null)
					Assert.isTrue(finder.getStartDate().before(finder.getEndDate()), "fecha incorrecta");
				this.finderService.find(finder);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());
				if (oops.getMessage().equals("fecha incorrecta"))
					result = this.createEditModelAndView(finder, "finder.fecha.error");
				else
					result = this.createEditModelAndView(finder, "finder.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
		final ModelAndView result;
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		final Collection<Category> categories = this.categoryService.findAll();

		result = new ModelAndView("finder/edit");
		result.addObject("finder", finder);
		result.addObject("categories", categories);
		result.addObject("message", messageCode);
		result.addObject("languaje", languaje);

		result.addObject("requestURI", "finder/actor/edit.do");

		return result;
	}

}
