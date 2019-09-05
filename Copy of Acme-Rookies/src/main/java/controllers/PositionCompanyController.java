
package controllers;

import java.util.ArrayList;
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

import services.CompanyService;
import services.PositionService;
import services.ProblemService;
import domain.Position;
import domain.Problem;

@Controller
@RequestMapping("/position/company")
public class PositionCompanyController extends AbstractController {

	@Autowired
	PositionService	positionService;
	@Autowired
	ProblemService	problemService;
	@Autowired
	CompanyService	companyService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Position> positions;

		positions = this.companyService.findByPrincipal().getPositions();

		result = new ModelAndView("position/list");
		result.addObject("positions", positions);
		result.addObject("requestURI", "position/company/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Position position;

		position = this.positionService.create();
		result = this.createEditModelAndView(position);

		return result;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int positionId) {
		ModelAndView result;
		final Position position;

		position = this.positionService.findOne(positionId);
		Assert.notNull(position);

		result = this.createEditModelAndView(position);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Position position) {
		ModelAndView result;

		result = this.createEditModelAndView(position, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Position position, final String messageCode) {
		final ModelAndView result;

		final Collection<Problem> problems = this.problemService.findAll();
		final Collection<Problem> probs = new ArrayList<>();
		for (final Problem p : problems)
			if (p.getPosition().equals(this.positionService.findOne(position.getId())))
				probs.add(p);
		final int problemSize = probs.size();

		result = new ModelAndView("position/edit");
		result.addObject("position", position);
		result.addObject("problemSize", problemSize);

		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Position position, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.print(binding);
			result = this.createEditModelAndView(position);

		} else
			try {
				System.out.print("Entra");

				this.positionService.save(position);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.print(oops);

				result = this.createEditModelAndView(position, "position.commit.error");
			}
		return result;
	}

	//delete

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Position position, final BindingResult binding) {
		ModelAndView result;
		try {
			this.positionService.delete(position);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			System.out.println(oops);
			result = this.createEditModelAndView(position, "position.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int positionId) {
		final ModelAndView result;
		final Position position;

		position = this.positionService.findOne(positionId);

		result = new ModelAndView("position/show");
		result.addObject("requestURI", "position/show.do");
		result.addObject("position", position);
		return result;
	}

}
