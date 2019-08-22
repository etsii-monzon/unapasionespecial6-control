
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import services.ConfigurationService;

@ControllerAdvice
public class BannerController {

	@Autowired
	private ConfigurationService	configurationService;


	@ModelAttribute("bannerURL")
	public String populateUser() {
		final String banner = this.configurationService.find().getBannerURL();
		return banner;
	}
	@ModelAttribute("systemName")
	public String populateUser2() {
		final String systemName = this.configurationService.findSystemName();
		return systemName;
	}
}
