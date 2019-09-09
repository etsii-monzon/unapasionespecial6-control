
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import services.AuditorService;
import services.PositionService;
import services.XompService;
import domain.Xomp;

@Controller
@RequestMapping("/xomp/auditor")
public class XompAuditorController extends AbstractController {

	@Autowired
	XompService		quoletService;

	@Autowired
	AuditorService	auditorService;
	@Autowired
	PositionService	positionService;
	@Autowired
	AuditService	auditService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int auditId) {
		ModelAndView result;
		Collection<Xomp> xompsAll;

		final Collection<Xomp> xomps = new ArrayList<>();
		boolean english = true;
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		if (languaje.equals("es"))
			english = false;

		try {
			Assert.isTrue(this.auditorService.findByPrincipal().getAudits().contains(this.auditService.findOne(auditId)));
			xompsAll = this.auditService.findOne(auditId).getPosition().getXomps();

			for (final Xomp x : xompsAll)
				if (x.isDraftMode() == false)
					xomps.add(x);

			result = new ModelAndView("xomp/list");
			result.addObject("xomps", xomps);
			result.addObject("auditId", auditId);

			result.addObject("auditId", this.positionService.findOne(auditId));

			result.addObject("requestURI", "xomp/auditor/list.do");

		} catch (final IllegalArgumentException e) {
			// TODO: handle exception
			result = new ModelAndView("misc/403");

		}
		result.addObject("english", english);

		return result;
	}

}
