
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
import services.QuoletService;
import domain.Quolet;

@Controller
@RequestMapping("/quolet/auditor")
public class QuoletAuditorController extends AbstractController {

	@Autowired
	QuoletService	quoletService;

	@Autowired
	AuditorService	auditorService;
	@Autowired
	AuditService	auditService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int auditId) {
		ModelAndView result;
		Collection<Quolet> quoletsAll;

		final Collection<Quolet> quolets = new ArrayList<>();
		boolean english = true;
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		if (languaje.equals("es"))
			english = false;

		try {
			Assert.isTrue(this.auditorService.findByPrincipal().getAudits().contains(this.auditService.findOne(auditId)));
			quoletsAll = this.auditService.findOne(auditId).getQuolets();

			for (final Quolet x : quoletsAll)
				if (x.isDraftMode() == false)
					quolets.add(x);

			result = new ModelAndView("quolet/list");
			result.addObject("quolets", quolets);
			result.addObject("auditId", auditId);

			result.addObject("positionId", this.auditService.findOne(auditId).getPosition().getId());

			result.addObject("requestURI", "quolet/auditor/list.do");

		} catch (final IllegalArgumentException e) {
			// TODO: handle exception
			result = new ModelAndView("misc/403");

		}
		result.addObject("english", english);

		return result;
	}

}
