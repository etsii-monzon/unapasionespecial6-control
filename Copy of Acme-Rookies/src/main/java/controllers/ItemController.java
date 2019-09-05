
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ItemService;
import services.ProviderService;
import domain.Item;

@Controller
@RequestMapping("/item")
public class ItemController extends AbstractController {

	@Autowired
	ItemService		itemService;
	@Autowired
	ProviderService	providerService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Item> items;

		items = this.itemService.findAll();

		result = new ModelAndView("item/list");
		result.addObject("items", items);
		result.addObject("requestURI", "item/list.do");

		return result;
	}

	@RequestMapping(value = "/listr", method = RequestMethod.GET)
	public ModelAndView listr(@RequestParam final int providerId) {
		ModelAndView result;
		Collection<Item> items;

		items = this.providerService.findOne(providerId).getItems();

		result = new ModelAndView("item/listr");
		result.addObject("items", items);
		result.addObject("requestURI", "item/listr.do");

		return result;
	}

}
