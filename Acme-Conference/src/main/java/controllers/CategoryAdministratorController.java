
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import domain.Category;

@Controller
@RequestMapping(value = "/category/administrator")
public class CategoryAdministratorController extends AbstractController {

	@Autowired
	private CategoryService	categoryService;


	//To open the view to edit-----------------
	//Categories
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Collection<Category> categories = this.categoryService.findAll();

		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		result.addObject("languaje", languaje);

		result.addObject("requestURI", "category/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView send() {
		ModelAndView result;
		Category category;

		category = this.categoryService.create();

		result = this.createEditModelAndView(category);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {
		ModelAndView result;
		Category category;
		category = this.categoryService.findOne(categoryId);

		try {
			Assert.isTrue(category.getParent() != null, "root");
			result = this.createEditModelAndView(category);
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("root"))
				result = new ModelAndView("redirect:/");
			else
				result = this.createEditModelAndView(category, "category.commit.error");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Category category, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println(binding);
			result = this.createEditModelAndView(category);
		} else
			try {
				Assert.isTrue(category.getParent() != null, "parent vacío");
				this.categoryService.save(category);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());
				if (oops.getMessage().equals("parent vacío"))
					result = this.createEditModelAndView(category, "category.parent.error");

				else
					result = this.createEditModelAndView(category, "category.commit.error");
			}
		return result;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int categoryId) {
		ModelAndView result;
		try {
			final Category category = this.categoryService.findOne(categoryId);
			Assert.isTrue(category.getParent() != null, "root");
			this.categoryService.delete(category);
			result = new ModelAndView("redirect:list.do");
			return result;
		} catch (final Throwable oops) {
			// TODO: handle exception
			if (oops.getMessage().equals("root"))
				result = new ModelAndView("redirect:list.do");
			else
				result = new ModelAndView("redirect:/");
		}
		return result;
	}
	protected ModelAndView createEditModelAndView(final Category category) {
		ModelAndView result;

		result = this.createEditModelAndView(category, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Category category, final String messageCode) {
		final ModelAndView result;
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		final Collection<Category> parents = this.categoryService.findAll();
		final Collection<Category> tree = new ArrayList<>();

		//Si se está editando la category,no debe aparecer en el select de parents
		//NI ella misma ni sus hijas
		if (category.getId() != 0) {
			parents.remove(category);
			parents.removeAll(this.categoryService.getAllFamily(category, tree));
		}

		result = new ModelAndView("category/edit");
		result.addObject("category", category);
		result.addObject("parents", parents);
		result.addObject("message", messageCode);
		result.addObject("languaje", languaje);

		result.addObject("requestURI", "category/administrator/edit.do");

		return result;
	}
}
