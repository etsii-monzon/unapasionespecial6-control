
package controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.ConferenceService;
import services.PanelService;
import services.PresentationService;
import services.TutorialService;
import domain.Comment;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	@Autowired
	ConferenceService	conferenceService;

	@Autowired
	PanelService		panelService;
	@Autowired
	PresentationService	presentationService;
	@Autowired
	TutorialService		tutorialService;

	@Autowired
	CommentService		commentService;


	@RequestMapping(value = "/conference/list", method = RequestMethod.GET)
	public ModelAndView listConference(@RequestParam final int conferenceId) {
		ModelAndView result;
		Collection<Comment> comments;

		comments = this.conferenceService.findOne(conferenceId).getComments();

		result = new ModelAndView("comment/list");
		result.addObject("comments", comments);

		result.addObject("requestURI", "comment/conference/list.do");
		result.addObject("commentEntityId", conferenceId);
		result.addObject("type", this.conferenceService.checkType(conferenceId));

		return result;
	}

	@RequestMapping(value = "/panel/list", method = RequestMethod.GET)
	public ModelAndView listActivity(@RequestParam final int panelId) {
		ModelAndView result;
		Collection<Comment> comments;
		try {
			Assert.isTrue(this.panelService.findOne(panelId) != null, "no existe");

			comments = this.panelService.findOne(panelId).getComments();

			result = new ModelAndView("comment/list");
			result.addObject("comments", comments);

			result.addObject("requestURI", "comment/panel/list.do");
			result.addObject("commentEntityId", panelId);
			result.addObject("conferenceId", this.panelService.findOne(panelId).getConference().getId());

		} catch (final Throwable oops) {
			// TODO: handle exception
			if (oops.getMessage().equals("no existe"))
				result = new ModelAndView("redirect:/welcome/index.do");
			else
				result = new ModelAndView("redirect:/welcome/index.do");

		}

		return result;
	}
	@RequestMapping(value = "/presentation/list", method = RequestMethod.GET)
	public ModelAndView listPresentation(@RequestParam final int presentationId) {
		ModelAndView result;
		Collection<Comment> comments;
		try {
			Assert.isTrue(this.presentationService.findOne(presentationId) != null, "no existe");

			comments = this.presentationService.findOne(presentationId).getComments();

			result = new ModelAndView("comment/list");
			result.addObject("comments", comments);

			result.addObject("requestURI", "comment/presentation/list.do");
			result.addObject("commentEntityId", presentationId);
			result.addObject("conferenceId", this.presentationService.findOne(presentationId).getConference().getId());

		} catch (final Throwable oops) {
			// TODO: handle exception
			if (oops.getMessage().equals("no existe"))
				result = new ModelAndView("redirect:/welcome/index.do");
			else
				result = new ModelAndView("redirect:/welcome/index.do");

		}
		return result;
	}
	@RequestMapping(value = "/tutorial/list", method = RequestMethod.GET)
	public ModelAndView listTutorial(@RequestParam final int tutorialId) {
		ModelAndView result;
		Collection<Comment> comments;
		try {
			Assert.isTrue(this.tutorialService.findOne(tutorialId) != null, "no existe");

			comments = this.tutorialService.findOne(tutorialId).getComments();

			result = new ModelAndView("comment/list");
			result.addObject("comments", comments);

			result.addObject("requestURI", "comment/tutorial/list.do");
			result.addObject("commentEntityId", tutorialId);
			result.addObject("conferenceId", this.tutorialService.findOne(tutorialId).getConference().getId());

		} catch (final Throwable oops) {
			// TODO: handle exception
			if (oops.getMessage().equals("no existe"))
				result = new ModelAndView("redirect:/welcome/index.do");
			else
				result = new ModelAndView("redirect:/welcome/index.do");

		}

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int commentEntityId) {
		ModelAndView result;
		Comment comment;
		try {
			Assert.isTrue(this.commentService.checkCommentEntity(commentEntityId), "no existe");
			comment = this.commentService.create();

			result = this.createEditModelAndView(comment, commentEntityId);

			result.addObject("commentEntityId", commentEntityId);

		} catch (final Throwable oops) {
			// TODO: handle exception
			if (oops.getMessage().equals("no existe"))
				result = new ModelAndView("redirect:/welcome/index.do");
			else
				result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Comment comment, final BindingResult binding, final HttpServletRequest req) {
		ModelAndView result;

		final int id = Integer.parseInt(req.getParameter("commentEntityId"));

		System.out.println(id);

		if (binding.hasErrors()) {
			System.out.println(binding);
			result = this.createEditModelAndView(comment, id);
		} else
			try {
				final Comment res = this.commentService.save(comment);
				System.out.println(this.conferenceService.findOne(id));
				if (this.conferenceService.findOne(id) != null) {
					this.conferenceService.findOne(id).getComments().add(res);
					System.out.println(this.conferenceService.findOne(id).getComments());
					result = new ModelAndView("redirect:/comment/conference/list.do?conferenceId=" + id);

				} else if (this.panelService.findOne(id) != null) {
					this.panelService.findOne(id).getComments().add(res);
					System.out.println(this.panelService.findOne(id).getComments());
					result = new ModelAndView("redirect:/comment/panel/list.do?panelId=" + id);

				} else if (this.presentationService.findOne(id) != null) {
					this.presentationService.findOne(id).getComments().add(res);
					System.out.println(this.presentationService.findOne(id).getComments());
					result = new ModelAndView("redirect:/comment/presentation/list.do?presentationId=" + id);

				} else if (this.tutorialService.findOne(id) != null) {
					this.tutorialService.findOne(id).getComments().add(res);
					System.out.println(this.tutorialService.findOne(id).getComments());
					result = new ModelAndView("redirect:/comment/tutorial/list.do?tutorialId=" + id);

				} else
					result = this.createEditModelAndView(comment, "comment.commit.error", id);

			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());
				result = this.createEditModelAndView(comment, "comment.commit.error", id);
			}
		return result;
	}
	protected ModelAndView createEditModelAndView(final Comment comment, final int commentEntityId) {
		ModelAndView result;

		result = this.createEditModelAndView(comment, null, commentEntityId);

		result.addObject("commentEntityId", commentEntityId);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Comment comment, final String messageCode, final int commentEntityId) {
		final ModelAndView result;

		result = new ModelAndView("comment/edit");
		result.addObject("comment", comment);
		result.addObject("message", messageCode);
		if (this.conferenceService.findOne(commentEntityId) != null)
			result.addObject("requestURI", "comment/conference/edit.do");
		else if (this.panelService.findOne(commentEntityId) != null)
			result.addObject("requestURI", "comment/panel/edit.do");
		else if (this.presentationService.findOne(commentEntityId) != null)
			result.addObject("requestURI", "comment/presentation/edit.do");
		else if (this.tutorialService.findOne(commentEntityId) != null)
			result.addObject("requestURI", "comment/tutorial/edit.do");
		else
			result.addObject("requestURI", "comment/edit.do");

		result.addObject("commentEntityId", commentEntityId);

		return result;
	}

}
