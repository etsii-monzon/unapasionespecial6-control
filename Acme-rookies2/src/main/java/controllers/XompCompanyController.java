
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

import services.CompanyService;
import services.PositionService;
import services.XompService;
import domain.Xomp;

@Controller
@RequestMapping("/xomp/company")
public class XompCompanyController extends AbstractController {

	@Autowired
	XompService		xompService;

	@Autowired
	CompanyService	companyService;
	@Autowired
	PositionService	positionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int positionId) {
		ModelAndView result;
		Collection<Xomp> xomps;
		boolean english = true;
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		if (languaje.equals("es"))
			english = false;

		try {
			Assert.isTrue(this.companyService.findByPrincipal().getPositions().contains(this.positionService.findOne(positionId)));
			xomps = this.positionService.findOne(positionId).getXomps();

			result = new ModelAndView("xomp/list");
			result.addObject("xomps", xomps);
			result.addObject("positionId", positionId);

			result.addObject("positionId", this.positionService.findOne(positionId).getId());

			result.addObject("requestURI", "xomp/company/list.do");

		} catch (final IllegalArgumentException e) {
			// TODO: handle exception
			result = new ModelAndView("misc/403");

		}
		result.addObject("english", english);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int xompId) {
		ModelAndView result;
		final Xomp xomp;
		boolean english = true;
		final String languaje = LocaleContextHolder.getLocale().getLanguage();
		if (languaje.equals("es"))
			english = false;

		xomp = this.xompService.findOne(xompId);

		try {

			Assert.isTrue(this.xompService.findAll().contains(xomp), "No Entity");
			Assert.isTrue(this.companyService.findByPrincipal().getPositions().contains(this.positionService.findOne(xomp.getPosition().getId())), "No owner");

			result = new ModelAndView("xomp/display");
			result.addObject("requestURI", "xomp/company/display.do");
			result.addObject("xomp", xomp);
			result.addObject("english", english);
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("No owner"))
				result = new ModelAndView("misc/403");

			else if (oops.getMessage().equals("No Entity"))
				result = new ModelAndView("redirect:/welcome/index.do");
			else
				result = this.createEditModelAndView(xomp, "xomp.commit.error");

		}

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int positionId) {
		ModelAndView result;
		Xomp xomp;

		try {
			Assert.isTrue(this.companyService.findByPrincipal().getPositions().contains(this.positionService.findOne(positionId)));

			xomp = this.xompService.create(positionId);
			System.out.println(xomp.getPosition());
			result = this.createEditModelAndView(xomp);
			result.addObject("positionId", positionId);
		} catch (final IllegalArgumentException e) {
			// TODO: handle exception
			result = new ModelAndView("misc/403");

		}

		return result;
	}
	//	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int xompId) {
		ModelAndView result;
		final Xomp xomp;
		xomp = this.xompService.findOne(xompId);

		if (xomp.isDraftMode() == true)
			try {

				//			Assert.isTrue(xomp.isDraftMode());
				Assert.isTrue(this.companyService.findByPrincipal().getPositions().contains(this.positionService.findOne(xomp.getPosition().getId())));

				Assert.notNull(xomp);

				result = this.createEditModelAndView(xomp);
			} catch (final IllegalArgumentException e) {
				// TODO: handle exception
				result = new ModelAndView("misc/403");

			}
		else
			result = new ModelAndView("misc/403");

		return result;
	}
	protected ModelAndView createEditModelAndView(final Xomp xomp) {
		ModelAndView result;

		result = this.createEditModelAndView(xomp, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Xomp xomp, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("xomp/edit");
		result.addObject("xomp", xomp);
		result.addObject("positionId", xomp.getPosition().getId());

		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Xomp xomp, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.print(binding);
			result = this.createEditModelAndView(xomp);

		} else
			try {
				Assert.isTrue(this.companyService.findByPrincipal().getPositions().contains(this.positionService.findOne(xomp.getPosition().getId())));
				this.xompService.save(xomp);

				result = new ModelAndView("redirect:list.do?positionId=" + xomp.getPosition().getId());
			} catch (final IllegalArgumentException e) {
				// TODO: handle exception
				result = new ModelAndView("misc/403");

			} catch (final Throwable oops) {
				System.out.print(oops);
				if (oops.getMessage().equals("draft mode"))
					result = new ModelAndView("redirect:/welcome/index.do");
				else
					result = this.createEditModelAndView(xomp, "xomp.commit.error");

			}
		return result;
	}

	//delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int xompId) {
		ModelAndView result;
		try {

			final Xomp xomp = this.xompService.findOne(xompId);
			Assert.isTrue(xomp.isDraftMode());

			Assert.isTrue(this.companyService.findByPrincipal().getPositions().contains(this.positionService.findOne(xomp.getPosition().getId())));

			this.xompService.delete(xomp);

			result = new ModelAndView("redirect:list.do?positionId=" + xomp.getPosition().getId());
		} catch (final IllegalArgumentException e) {
			// TODO: handle exception
			result = new ModelAndView("misc/403");
		}
		return result;
	}

}
