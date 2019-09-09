
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

import services.AuditService;
import services.AuditorService;
import services.PositionService;
import domain.Audit;
import domain.Position;

@Controller
@RequestMapping("/audit/auditor")
public class AuditAuditorController extends AbstractController {

	@Autowired
	AuditService	auditService;

	@Autowired
	AuditorService	auditorService;
	@Autowired
	PositionService	positionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Audit> audits;

		audits = this.auditorService.findByPrincipal().getAudits();

		result = new ModelAndView("audit/list");
		result.addObject("audits", audits);
		result.addObject("requestURI", "audit/auditor/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Audit audit;

		audit = this.auditService.create();
		result = this.createEditModelAndView(audit);

		return result;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int auditId) {
		ModelAndView result;
		final Audit audit;

		audit = this.auditService.findOne(auditId);
		Assert.notNull(audit);

		result = this.createEditModelAndView(audit);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Audit audit) {
		ModelAndView result;

		result = this.createEditModelAndView(audit, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Audit audit, final String messageCode) {
		final ModelAndView result;

		final Collection<Audit> audits = this.auditorService.findByPrincipal().getAudits();
		final Collection<Position> positions = this.positionService.findAll();
		for (final Audit a : audits)
			if (positions.contains(a.getPosition()))
				positions.remove(a.getPosition());

		result = new ModelAndView("audit/edit");
		result.addObject("audit", audit);
		result.addObject("positions", positions);

		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Audit audit, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.print(binding);
			result = this.createEditModelAndView(audit);

		} else
			try {
				System.out.print("Entra");

				this.auditService.save(audit);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.print(oops);

				result = this.createEditModelAndView(audit, "audit.commit.error");
			}
		return result;
	}

	//delete

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Audit audit, final BindingResult binding) {
		ModelAndView result;
		try {
			this.auditService.delete(audit);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			System.out.println(oops);
			result = this.createEditModelAndView(audit, "audit.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int auditId) {
		final ModelAndView result;
		final Audit audit;

		audit = this.auditService.findOne(auditId);

		result = new ModelAndView("audit/show");
		result.addObject("requestURI", "audit/show.do");
		result.addObject("audit", audit);
		return result;
	}

}
