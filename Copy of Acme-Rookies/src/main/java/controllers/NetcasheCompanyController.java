
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
import services.NetcasheService;
import domain.Netcashe;

@Controller
@RequestMapping("/netcashe/company")
public class NetcasheCompanyController extends AbstractController {

	@Autowired
	NetcasheService	netcasheService;

	@Autowired
	CompanyService	companyService;
	@Autowired
	AuditService	auditService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int auditId) {
		ModelAndView result;
		Collection<Netcashe> netcashes;
		boolean english = true;
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		if (languaje.equals("es"))
			english = false;

		try {
			Assert.isTrue(this.companyService.findByPrincipal().getPositions().contains(this.auditService.findOne(auditId).getPosition()));
			netcashes = this.auditService.findOne(auditId).getNetcashes();

			result = new ModelAndView("netcashe/list");
			result.addObject("netcashes", netcashes);
			result.addObject("auditId", auditId);

			result.addObject("positionId", this.auditService.findOne(auditId).getPosition().getId());

			result.addObject("requestURI", "netcashe/company/list.do");

		} catch (final IllegalArgumentException e) {
			// TODO: handle exception
			result = new ModelAndView("misc/403");

		}
		result.addObject("english", english);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int netcasheId) {
		ModelAndView result;
		final Netcashe netcashe;
		boolean english = true;
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		if (languaje.equals("es"))
			english = false;

		netcashe = this.netcasheService.findOne(netcasheId);

		try {

			Assert.isTrue(this.netcasheService.findAll().contains(netcashe), "No Entity");
			Assert.isTrue(this.companyService.findByPrincipal().getPositions().contains(this.auditService.findOne(netcashe.getAudit().getId()).getPosition()), "No owner");

			result = new ModelAndView("netcashe/display");
			result.addObject("requestURI", "netcashe/company/display.do");
			result.addObject("netcashe", netcashe);
			result.addObject("english", english);
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("No owner"))
				result = new ModelAndView("misc/403");

			else if (oops.getMessage().equals("No Entity"))
				result = new ModelAndView("redirect:/welcome/index.do");
			else
				result = this.createEditModelAndView(netcashe, "netcashe.commit.error");

		}

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int auditId) {
		ModelAndView result;
		Netcashe netcashe;

		try {
			Assert.isTrue(this.companyService.findByPrincipal().getPositions().contains(this.auditService.findOne(auditId).getPosition()));

			netcashe = this.netcasheService.create(auditId);
			System.out.println(netcashe.getAudit());
			result = this.createEditModelAndView(netcashe);
			result.addObject("auditId", auditId);
		} catch (final IllegalArgumentException e) {
			// TODO: handle exception
			result = new ModelAndView("misc/403");

		}

		return result;
	}
	//	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int netcasheId) {
		ModelAndView result;
		final Netcashe netcashe;
		netcashe = this.netcasheService.findOne(netcasheId);

		if (netcashe.isDraftMode() == true)
			try {

				//			Assert.isTrue(xomp.isDraftMode());
				Assert.isTrue(this.companyService.findByPrincipal().getPositions().contains(this.auditService.findOne(netcashe.getAudit().getId()).getPosition()));

				Assert.notNull(netcashe);

				result = this.createEditModelAndView(netcashe);
			} catch (final IllegalArgumentException e) {
				// TODO: handle exception
				result = new ModelAndView("misc/403");

			}
		else
			result = new ModelAndView("misc/403");

		return result;
	}
	protected ModelAndView createEditModelAndView(final Netcashe netcashe) {
		ModelAndView result;

		result = this.createEditModelAndView(netcashe, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Netcashe netcashe, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("netcashe/edit");
		result.addObject("netcashe", netcashe);
		result.addObject("auditId", netcashe.getAudit().getId());

		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Netcashe netcashe, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.print(binding);
			result = this.createEditModelAndView(netcashe);

		} else
			try {
				Assert.isTrue(this.companyService.findByPrincipal().getPositions().contains(this.auditService.findOne(netcashe.getAudit().getId()).getPosition()));
				this.netcasheService.save(netcashe);

				result = new ModelAndView("redirect:list.do?auditId=" + netcashe.getAudit().getId());
			} catch (final IllegalArgumentException e) {
				// TODO: handle exception
				result = new ModelAndView("misc/403");

			} catch (final Throwable oops) {
				System.out.print(oops);
				if (oops.getMessage().equals("draft mode"))
					result = new ModelAndView("redirect:/welcome/index.do");
				else
					result = this.createEditModelAndView(netcashe, "netcashe.commit.error");

			}
		return result;
	}

	//delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int netcasheId) {
		ModelAndView result;
		try {

			final Netcashe netcashe = this.netcasheService.findOne(netcasheId);
			Assert.isTrue(netcashe.isDraftMode());

			Assert.isTrue(this.companyService.findByPrincipal().getPositions().contains(this.auditService.findOne(netcashe.getAudit().getId()).getPosition()));

			this.netcasheService.delete(netcashe);

			result = new ModelAndView("redirect:list.do?auditId=" + netcashe.getAudit().getId());
		} catch (final IllegalArgumentException e) {
			// TODO: handle exception
			result = new ModelAndView("misc/403");
		}
		return result;
	}

}
