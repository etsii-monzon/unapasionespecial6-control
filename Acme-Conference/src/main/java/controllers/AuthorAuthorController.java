/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import domain.Author;

@Controller
@RequestMapping("/author/author")
public class AuthorAuthorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AuthorService	authorService;


	// Constructors -----------------------------------------------------------

	public AuthorAuthorController() {
		super();
	}

	//Edición: Obtener los datos del author-----------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		final Author author;

		author = this.authorService.findByPrincipal();
		result = this.createEditModelAndView(author);

		return result;
	}
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
			} catch (final DataIntegrityViolationException oops) {
				result = this.createEditModelAndView(author, "author.username.error");
			} catch (final Throwable oops) {
				System.out.println(oops);
				if (oops.getMessage() == "email error")
					result = this.createEditModelAndView(author, "author.email.error");
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

		result = new ModelAndView("author/edit");

		result.addObject("author", author);
		result.addObject("message", messageCode);
		return result;
	}

}
