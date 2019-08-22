
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.MessageService;
import domain.Actor;
import domain.Message;

@Controller
@RequestMapping("/message/administrator")
public class MessageAdministratorController extends AbstractController {

	@Autowired
	MessageService			messageService;

	@Autowired
	ActorService			actorService;

	@Autowired
	ConfigurationService	configurationService;


	@RequestMapping(value = "/broadcast", method = RequestMethod.GET)
	public ModelAndView broadcast() {
		ModelAndView result;

		Message m;

		m = this.messageService.broadcast();
		result = this.createEditModelAndView(m);
		result.addObject("requestURI", "message/administrator/broadcast.do");

		return result;
	}
	@RequestMapping(value = "/messageAuthors", method = RequestMethod.GET)
	public ModelAndView messageAuthors() {
		ModelAndView result;

		Message m;

		m = this.messageService.messAuthors();
		result = this.createEditModelAndView(m);
		result.addObject("requestURI", "message/administrator/messageAuthors.do");

		return result;
	}

	@RequestMapping(value = "/messageAuthorsSub", method = RequestMethod.GET)
	public ModelAndView messageAuthorsSub() {
		ModelAndView result;

		Message m;

		m = this.messageService.messAuthorsSub();
		result = this.createEditModelAndView(m);
		result.addObject("requestURI", "message/administrator/messageAuthorsSub.do");

		return result;
	}
	@RequestMapping(value = "/messageAuthorsRegistration", method = RequestMethod.GET)
	public ModelAndView messageAuthorsSubRegistration() {
		ModelAndView result;

		Message m;

		m = this.messageService.messAuthorsRegistrations();
		result = this.createEditModelAndView(m);
		result.addObject("requestURI", "message/administrator/messageAuthorsRegistration.do");

		return result;
	}

	@RequestMapping(value = {
		"/broadcast", "/messageAuthors", "/messageAuthorsSub", "/messageAuthorsRegistration"
	}, method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("m") @Valid final Message m, final BindingResult binding) {
		ModelAndView result;
		if (m.getRecipients().isEmpty())
			result = this.createEditModelAndView(m, "message.recipients.error");
		else if (binding.hasErrors()) {
			System.out.print(binding);
			result = this.createEditModelAndView(m);

		} else
			try {
				this.messageService.save(m);
				result = new ModelAndView("redirect:/message/actor/list.do");
			} catch (final Throwable oops) {
				System.out.print(oops);
				result = this.createEditModelAndView(m, "message.commit.error");
			}
		return result;
	}
	//Functions ModelAndView
	protected ModelAndView createEditModelAndView(final Message m) {
		ModelAndView result;

		result = this.createEditModelAndView(m, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message m, final String messageCode) {
		final ModelAndView result;

		final Collection<Actor> recipients = this.actorService.findAll();
		recipients.remove(this.actorService.findByPrincipal());
		final String languaje = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("message/create");
		result.addObject("topics", this.configurationService.find().getTopics());
		result.addObject("m", m);
		result.addObject("message", messageCode);
		result.addObject("languaje", languaje);

		return result;
	}
}
