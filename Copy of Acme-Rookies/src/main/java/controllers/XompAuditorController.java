
package controllers;

import java.util.Collection;
import java.util.Date;

import org.joda.time.DateTime;
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
	AuditService	auditService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int auditId) {
		ModelAndView result;
		Collection<Xomp> xomps;
		boolean english = true;
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		if (languaje.equals("es"))
			english = false;

		try {
			Assert.isTrue(this.auditorService.findByPrincipal().getAudits().contains(this.auditService.findOne(auditId)));
			xomps = this.auditService.findOne(auditId).getXomps();

			final Date mes = new DateTime().minusMonths(1).toDate();
			final Date dosMeses = new DateTime().minusMonths(2).toDate();

			result = new ModelAndView("xomp/list");
			result.addObject("xomps", xomps);
			result.addObject("auditId", auditId);
			result.addObject("mes", mes);
			result.addObject("dosMeses", dosMeses);
			result.addObject("positionId", this.auditService.findOne(auditId).getPosition().getId());

			result.addObject("requestURI", "xomp/auditor/list.do");

		} catch (final IllegalArgumentException e) {
			// TODO: handle exception
			result = new ModelAndView("misc/403");

		}
		result.addObject("english", english);

		return result;
	}

}
