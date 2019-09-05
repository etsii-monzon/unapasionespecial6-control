
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

import services.CompanyService;
import services.PositionService;
import services.ProblemService;
import domain.Position;
import domain.Problem;

@Controller
@RequestMapping("/problem/company")
public class ProblemCompanyController extends AbstractController {

	@Autowired
	ProblemService	problemService;

	@Autowired
	PositionService	positionService;

	@Autowired
	CompanyService	companyService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Problem> problems;

		problems = this.companyService.findByPrincipal().getProblems();

		result = new ModelAndView("problem/list");
		result.addObject("problems", problems);
		result.addObject("requestURI", "problem/company/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Problem problem;

		problem = this.problemService.create();
		result = this.createEditModelAndView(problem);

		return result;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int problemId) {
		ModelAndView result;
		final Problem problem;

		problem = this.problemService.findOne(problemId);
		Assert.notNull(problem);
		result = this.createEditModelAndView(problem);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Problem problem) {
		ModelAndView result;

		result = this.createEditModelAndView(problem, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Problem problem, final String messageCode) {
		final ModelAndView result;

		final Collection<Position> positions = this.companyService.findByPrincipal().getPositions();

		result = new ModelAndView("problem/edit");
		result.addObject("problem", problem);
		result.addObject("positions", positions);
		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Problem problem, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.print(binding);
			result = this.createEditModelAndView(problem);

		} else
			try {
				System.out.print("Entra");

				this.problemService.save(problem);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.print(oops);

				result = this.createEditModelAndView(problem, "problem.commit.error");
			}
		return result;
	}

	//delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int problemId) {
		ModelAndView result;
		this.problemService.delete(this.problemService.findOne(problemId));
		result = new ModelAndView("redirect:list.do");

		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int problemId) {
		final ModelAndView result;
		final Problem problem;

		problem = this.problemService.findOne(problemId);

		result = new ModelAndView("problem/show");
		result.addObject("requestURI", "problem/show.do");
		result.addObject("problem", problem);
		return result;
	}
}
