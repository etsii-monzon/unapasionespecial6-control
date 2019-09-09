
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CreditCardService;
import services.ItemService;
import services.ProviderService;
import domain.Item;
import domain.Provider;
import forms.ProviderForm;

@Controller
@RequestMapping("/provider")
public class ProviderController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ProviderService		providerService;
	@Autowired
	private ItemService			itemService;

	//	@Autowired
	//	private PositionService		positionService;
	//
	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private ActorService		actorService;


	//	@Autowired
	//	private ActorService		actorService;

	// Constructors -----------------------------------------------------------

	public ProviderController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final ProviderForm providerForm = new ProviderForm();

		result = this.createEditModelAndView(providerForm);

		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("provider") final ProviderForm providerForm, final BindingResult binding) {
		ModelAndView result;
		Provider res;
		res = this.providerService.reconstruct(providerForm, binding);

		if (binding.hasErrors()) {
			System.out.println(binding);
			result = this.createEditModelAndView(providerForm);
		} else if (!(providerForm.getConfirmPassword().equals(res.getUserAccount().getPassword())))
			result = this.createEditModelAndView(providerForm, "provider.password.error");
		else if (this.actorService.checkUserEmail(res.getEmail()) == false)
			result = this.createEditModelAndView(providerForm, "provider.email.error");
		else if (res.getPhoneNumber().length() < 4)
			result = this.createEditModelAndView(providerForm, "provider.phone.error");
		else if (this.creditCardService.checkCreditCard(res.getCreditCard()) == false)
			result = this.createEditModelAndView(providerForm, "provider.creditCard.error");
		else
			try {
				this.providerService.save(res);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final DataIntegrityViolationException oops) {
				result = this.createEditModelAndView(providerForm, "provider.username.error");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(providerForm, "provider.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndView(final ProviderForm providerForm) {
		ModelAndView result;
		result = this.createEditModelAndView(providerForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final ProviderForm providerForm, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("provider/create");

		result.addObject("provider", providerForm);
		result.addObject("message", messageCode);
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int itemId) {
		ModelAndView result;
		Provider provider;
		Item item;

		provider = new Provider();
		item = this.itemService.findOne(itemId);
		final Collection<Provider> pros = this.providerService.findAll();

		for (final Provider c : pros)
			if (c.getItems().contains(item))
				provider = c;

		result = new ModelAndView("provider/show");
		result.addObject("requestURI", "provider/show.do");
		result.addObject("provider", provider);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Provider> providers;

		providers = this.providerService.findAll();

		result = new ModelAndView("provider/list");
		result.addObject("providers", providers);
		result.addObject("requestURI", "provider/list.do");

		return result;
	}

	//	@RequestMapping(value = "/list", method = RequestMethod.GET)
	//	public ModelAndView list() {
	//		ModelAndView result;
	//		Collection<Company> companies;
	//
	//		companies = this.providerService.findAll();
	//
	//		result = new ModelAndView("company/list");
	//		result.addObject("companies", companies);
	//		result.addObject("requestURI", "company/list.do");
	//
	//		return result;
	//	}

}
