
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import domain.Configuration;

@Controller
@RequestMapping(value = "/configuration/administrator")
public class ConfigurationAdministratorController extends AbstractController {

	@Autowired
	private ConfigurationService	configurationService;


	//To open the view to edit-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView result;
		Configuration configuration;

		configuration = (Configuration) this.configurationService.findall().toArray()[0];

		result = this.createEditModelAndView(configuration);

		return result;

	}

	//To save what has been edited-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Configuration configuration, final BindingResult binding) {

		ModelAndView result = new ModelAndView("configuration/edit");

		if (binding.hasErrors())
			//result = this.createEditModelAndView(configurationParameters);
			result.addObject("errors", binding.getFieldErrors());
		else
			try {
				configuration.setReBrand(this.configurationService.find().isReBrand());
				this.configurationService.save(configuration);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(configuration, "configuration.commit.error");
			}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "notify")
	public ModelAndView reBrand(@Valid final Configuration configuration, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			//result = this.createEditModelAndView(configurationParameters);
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(configuration);
		} else
			try {
				configuration.setReBrand(true);
				this.configurationService.save(configuration);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				System.out.println(oops);
				result = this.createEditModelAndView(configuration, "configuration.commit.error");
			}

		return result;

	}
	//Ancillary methods------------------

	protected ModelAndView createEditModelAndView(final Configuration configuration) {

		Assert.notNull(configuration);
		ModelAndView result;
		result = this.createEditModelAndView(configuration, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Configuration configuration, final String messageCode) {
		Assert.notNull(configuration);

		ModelAndView result;

		result = new ModelAndView("configuration/edit");
		result.addObject("configuration", configuration);
		result.addObject("reBrand", configuration.isReBrand());
		result.addObject("message", messageCode);
		result.addObject("RequestURI", "configuration/administrator/edit.do");

		return result;

	}
}
