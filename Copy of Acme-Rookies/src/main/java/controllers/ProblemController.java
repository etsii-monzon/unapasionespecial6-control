/*
 * MemberController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PositionService;
import services.ProblemService;
import domain.Problem;

@Controller
@RequestMapping("/problem")
public class ProblemController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PositionService	positionService;

	@Autowired
	private ProblemService	problemService;


	// Constructors -----------------------------------------------------------

	public ProblemController() {
		super();
	}

	//Controller list para un actor no autenticado

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int positionId) {
		ModelAndView result;
		final Collection<Problem> problems;

		problems = this.problemService.findAll();
		final Collection<Problem> newP = new ArrayList<>();
		for (final Problem p : problems)
			if (p.getPosition().equals(this.positionService.findOne(positionId)))
				newP.add(p);

		result = new ModelAndView("problem/list");
		result.addObject("requestURI", "problem/list.do");
		result.addObject("problems", newP);

		return result;
	}

}
