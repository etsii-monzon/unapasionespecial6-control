
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Comment;

@Service
@Transactional
public class CommentService {

	@Autowired
	private CommentRepository	commentRepository;

	@Autowired
	ConferenceService			conferenceService;
	@Autowired
	PanelService				panelService;
	@Autowired
	PresentationService			presentationService;
	@Autowired
	TutorialService				tutorialService;


	public Comment create() {
		Comment comment;

		comment = new Comment();

		comment.setMoment(new Date());

		return comment;
	}

	public Comment findOne(final int commentId) {
		Comment comment;
		comment = this.commentRepository.findOne(commentId);
		return comment;
	}

	public Collection<Comment> findAll() {
		return this.commentRepository.findAll();
	}

	public Comment save(final Comment comment) {
		Comment res;

		res = this.commentRepository.save(comment);

		return res;

	}
	public void delete(final Comment comment) {
		Assert.notNull(comment.getMoment());
		Assert.notNull(comment);

		this.commentRepository.delete(comment);

	}

	public boolean checkCommentEntity(final int id) {
		if (this.conferenceService.findOne(id) != null)
			return true;
		else if (this.panelService.findOne(id) != null)
			return true;
		else if (this.presentationService.findOne(id) != null)
			return true;
		else if (this.tutorialService.findOne(id) != null)
			return true;
		else
			return false;
	}
	public Double avgNumberCommentsPerConference() {
		return this.commentRepository.avgNumberCommentsPerConference();
	}

	public Double stdNumberCommentsPerConference() {
		return this.commentRepository.stdNumberCommentsPerConference();
	}

	public Double minNumberCommentsPerConference() {
		return this.commentRepository.minNumberCommentsPerConference();
	}

	public Double maxNumberCommentsPerConference() {
		return this.commentRepository.maxNumberCommentsPerConference();
	}

	public Double avgNumberCommentsPerActivity() {
		return this.commentRepository.avgNumberCommentsPerActivity();
	}

	public Double stdNumberCommentsPerActivity() {
		return this.commentRepository.stdNumberCommentsPerActivity();
	}

	public Double minNumberCommentsPerActivity() {
		return this.commentRepository.minNumberCommentsPerActivity();
	}

	public Double maxNumberCommentsPerActivity() {
		return this.commentRepository.maxNumberCommentsPerActivity();
	}

}
