
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import services.PositionService;
import domain.Audit;
import domain.Position;

@Controller
@RequestMapping("/audit")
public class AuditController extends AbstractController {

	@Autowired
	PositionService	positionService;
	@Autowired
	AuditService	auditService;


	@RequestMapping(value = "/listr", method = RequestMethod.GET)
	public ModelAndView listr(@RequestParam final int positionId) {
		ModelAndView result;

		Position pos;

		final Collection<Audit> audits = new ArrayList<>();
		pos = this.positionService.findOne(positionId);
		final Collection<Audit> aus = this.auditService.findAll();
		for (final Audit a : aus)
			if (a.getPosition().equals(pos))
				audits.add(a);

		result = new ModelAndView("audit/listr");
		result.addObject("audits", audits);
		result.addObject("positionId", positionId);

		result.addObject("requestURI", "audit/listr.do");

		return result;
	}

}
