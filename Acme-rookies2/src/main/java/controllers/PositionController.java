
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import services.PositionService;
import domain.Position;

@Controller
@RequestMapping("/position")
public class PositionController extends AbstractController {

	@Autowired
	PositionService	positionService;
	@Autowired
	CompanyService	companyService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Position> positions;

		positions = this.positionService.findAll();
		final Collection<Position> newP = new ArrayList<>();

		for (final Position p : positions)
			if (p.isDraftMode() == false)
				newP.add(p);
		result = new ModelAndView("position/list");
		result.addObject("positions", newP);
		result.addObject("requestURI", "position/list.do");

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET, params = "search")
	public ModelAndView searchList(@RequestParam final String keyword) {
		ModelAndView res;
		final Collection<Position> positionsD = new ArrayList<>();
		final Collection<Position> positions = this.positionService.searchPositions(keyword);
		final Collection<Position> positionsCompanies = this.positionService.getPositionsOfCompanies(this.companyService.searchCompanyByCommercialName(keyword));
		positions.addAll(positionsCompanies);
		final Collection<Position> set = new HashSet<>(positions);
		for (final Position p : set)
			if (p.isDraftMode() == false)
				positionsD.add(p);
		res = new ModelAndView("position/list");
		res.addObject("positions", positionsD);
		return res;
	}
	@RequestMapping(value = "/listr", method = RequestMethod.GET)
	public ModelAndView listr(@RequestParam final int companyId) {
		ModelAndView result;
		Collection<Position> positions;

		positions = this.companyService.findOne(companyId).getPositions();
		final Collection<Position> newP = new ArrayList<>();

		for (final Position p : positions)
			if (p.isDraftMode() == false)
				newP.add(p);
		result = new ModelAndView("position/listr");
		result.addObject("positions", newP);
		result.addObject("requestURI", "position/listr.do");

		return result;
	}

}
