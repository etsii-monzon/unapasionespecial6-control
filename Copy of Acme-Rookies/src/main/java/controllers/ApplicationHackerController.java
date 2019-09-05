
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.PositionService;
import domain.Application;
import domain.Position;

@Controller
@RequestMapping("/application/rookie")
public class ApplicationHackerController extends AbstractController {

	@Autowired
	private ApplicationService	appService;

	@Autowired
	private PositionService		posService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int positionId) {
		ModelAndView result;
		Application app;

		app = this.appService.create(positionId);
		this.appService.save(app);
		result = new ModelAndView("redirect:list.do");
		result.addObject("positionId", positionId);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Application app) {
		ModelAndView result;

		result = this.createEditModelAndView(app, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("application/edit");
		result.addObject("application", application);

		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application app, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.print(binding);
			result = this.createEditModelAndView(app);

		} else
			try {
				System.out.print("Entra");

				this.appService.save(app);
				final int positionId = app.getPosition().getId();
				result = new ModelAndView("redirect:list.do");
				result.addObject("positionId", positionId);
			} catch (final Throwable oops) {
				System.out.print(oops);

				result = this.createEditModelAndView(app, "application.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int positionId) {
		ModelAndView result;
		Collection<Application> apps;
		Position pos;

		pos = this.posService.findOne(positionId);
		apps = this.appService.findAllGrouped(pos);

		result = new ModelAndView("application/list");
		result.addObject("applications", apps);
		result.addObject("requestURI", "application/rookie/list.do");

		return result;
	}

	@RequestMapping(value = "/list/all", method = RequestMethod.GET)
	public ModelAndView listMaster() {
		ModelAndView result;
		Collection<Application> apps;

		apps = this.appService.findAll();

		result = new ModelAndView("application/list");
		result.addObject("applications", apps);
		result.addObject("requestURI", "application/rookie/list/all.do");

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int applicationId) {
		final ModelAndView result;
		final Application app;

		app = this.appService.findOne(applicationId);

		result = new ModelAndView("application/show");
		result.addObject("requestURI", "application/show.do");
		result.addObject("application", app);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result;
		final Application app;
		app = this.appService.findOne(applicationId);
		Assert.notNull(app);

		result = this.createEditModelAndView(app);
		result.addObject("requestURI", "application/rookie/edit.do");

		return result;
	}

}
