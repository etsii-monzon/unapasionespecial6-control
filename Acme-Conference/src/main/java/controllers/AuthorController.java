
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import domain.Author;

@Controller
@RequestMapping("/author")
public class AuthorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AuthorService	authorService;


	// Constructors -----------------------------------------------------------

	public AuthorController() {
		super();
	}

	// Creación: Crear nuevo Author ---------------------------------------------------------------		
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		final ModelAndView result;
		final Author author = this.authorService.create();

		result = this.createEditModelAndView(author);
		result.addObject("author", author);

		return result;
	}

	// Edición: Botón Save ---------------------------------------------------------------		
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Author author, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding);
			result = this.createEditModelAndView(author);
		} else
			try {
				this.authorService.save(author);
				result = new ModelAndView("redirect:/welcome/index.do");

			} catch (final Throwable oops) {

				if (oops.getMessage().equals("email error"))
					result = this.createEditModelAndView(author, "author.email.error");
				else if (oops.getMessage().equals("username error"))
					result = this.createEditModelAndView(author, "author.username.error");
				else
					result = this.createEditModelAndView(author, "author.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndView(final Author author) {
		ModelAndView result;
		result = this.createEditModelAndView(author, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Author author, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("author/create");

		result.addObject("author", author);
		result.addObject("message", messageCode);
		return result;
	}

}
