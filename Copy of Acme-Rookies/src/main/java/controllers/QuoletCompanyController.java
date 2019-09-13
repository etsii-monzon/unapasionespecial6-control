
package controllers;

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

import services.AuditService;
import services.CompanyService;
import services.QuoletService;
import domain.Quolet;

@Controller
@RequestMapping("/quolet/company")
public class QuoletCompanyController extends AbstractController {

	@Autowired
	QuoletService	quoletService;

	@Autowired
	CompanyService	companyService;
	@Autowired
	AuditService	auditService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int auditId) {
		ModelAndView result;
		Collection<Quolet> quolets;
		boolean english = true;
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		if (languaje.equals("es"))
			english = false;

		try {
			Assert.isTrue(this.companyService.findByPrincipal().getPositions().contains(this.auditService.findOne(auditId).getPosition()));
			quolets = this.auditService.findOne(auditId).getQuolets();

			result = new ModelAndView("quolet/list");
			result.addObject("quolets", quolets);
			result.addObject("auditId", auditId);

			result.addObject("positionId", this.auditService.findOne(auditId).getPosition().getId());

			result.addObject("requestURI", "quolet/company/list.do");

		} catch (final IllegalArgumentException e) {
			// TODO: handle exception
			result = new ModelAndView("misc/403");

		}
		result.addObject("english", english);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int quoletId) {
		ModelAndView result;
		final Quolet quolet;
		boolean english = true;
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		if (languaje.equals("es"))
			english = false;

		quolet = this.quoletService.findOne(quoletId);

		try {

			Assert.isTrue(this.quoletService.findAll().contains(quolet), "No Entity");
			Assert.isTrue(this.companyService.findByPrincipal().getPositions().contains(this.auditService.findOne(quolet.getAudit().getId()).getPosition()), "No owner");

			result = new ModelAndView("quolet/display");
			result.addObject("requestURI", "quolet/company/display.do");
			result.addObject("quolet", quolet);
			result.addObject("english", english);
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("No owner"))
				result = new ModelAndView("misc/403");

			else if (oops.getMessage().equals("No Entity"))
				result = new ModelAndView("redirect:/welcome/index.do");
			else
				result = this.createEditModelAndView(quolet, "quolet.commit.error");

		}

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int auditId) {
		ModelAndView result;
		Quolet quolet;

		try {
			Assert.isTrue(this.companyService.findByPrincipal().getPositions().contains(this.auditService.findOne(auditId).getPosition()));

			quolet = this.quoletService.create(auditId);
			System.out.println(quolet.getAudit());
			result = this.createEditModelAndView(quolet);
			result.addObject("auditId", auditId);
		} catch (final IllegalArgumentException e) {
			// TODO: handle exception
			result = new ModelAndView("misc/403");

		}

		return result;
	}
	//	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int quoletId) {
		ModelAndView result;
		final Quolet quolet;
		quolet = this.quoletService.findOne(quoletId);

		if (quolet.isDraftMode() == true)
			try {

				Assert.isTrue(this.companyService.findByPrincipal().getPositions().contains(this.auditService.findOne(quolet.getAudit().getId()).getPosition()));

				Assert.notNull(quolet);

				result = this.createEditModelAndView(quolet);
			} catch (final IllegalArgumentException e) {
				// TODO: handle exception
				result = new ModelAndView("misc/403");

			}
		else
			result = new ModelAndView("misc/403");

		return result;
	}
	protected ModelAndView createEditModelAndView(final Quolet quolet) {
		ModelAndView result;

		result = this.createEditModelAndView(quolet, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Quolet quolet, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("quolet/edit");
		result.addObject("quolet", quolet);
		result.addObject("auditId", quolet.getAudit().getId());

		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Quolet quolet, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.print(binding);
			result = this.createEditModelAndView(quolet);

		} else
			try {
				Assert.isTrue(this.companyService.findByPrincipal().getPositions().contains(this.auditService.findOne(quolet.getAudit().getId()).getPosition()));
				this.quoletService.save(quolet);

				result = new ModelAndView("redirect:list.do?auditId=" + quolet.getAudit().getId());
			} catch (final IllegalArgumentException e) {
				// TODO: handle exception
				result = new ModelAndView("misc/403");

			} catch (final Throwable oops) {
				System.out.print(oops);
				if (oops.getMessage().equals("draft mode"))
					result = new ModelAndView("redirect:/welcome/index.do");
				else
					result = this.createEditModelAndView(quolet, "quolet.commit.error");

			}
		return result;
	}

	//delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int quoletId) {
		ModelAndView result;
		try {

			final Quolet quolet = this.quoletService.findOne(quoletId);
			Assert.isTrue(quolet.isDraftMode());

			Assert.isTrue(this.companyService.findByPrincipal().getPositions().contains(this.auditService.findOne(quolet.getAudit().getId()).getPosition()));

			this.quoletService.delete(quolet);

			result = new ModelAndView("redirect:list.do?auditId=" + quolet.getAudit().getId());
		} catch (final IllegalArgumentException e) {
			// TODO: handle exception
			result = new ModelAndView("misc/403");
		}
		return result;
	}

}
