/*
 * SampleTest.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Comment;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CommentServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private CommentService		commentService;

	@Autowired
	private ConferenceService	conferenceService;
	@Autowired
	private PanelService		panelService;
	@Autowired
	private PresentationService	presentationService;
	@Autowired
	private TutorialService		tutorialService;


	// Tests ------------------------------------------------------------------

	// The following are fictitious test cases that are intended to check that 
	// JUnit works well in this project.  Just righ-click this class and run 
	// it using JUnit.

	/*
	 * Test comprobación un autor lista los comments de una conference.
	 * Req Funcional: 22.1
	 */
	@Test
	public void testListCommentsConfe() {
		super.authenticate("author1");

		final Collection<Comment> res = this.conferenceService.findOne(this.getEntityId("conference1")).getComments();

		Assert.notNull(res);

		super.unauthenticate();

	}
	/*
	 * Test comprobación un autor lista los comments de una activity panel.
	 * Req Funcional: 22.1
	 */
	@Test
	public void testListCommentsActPan() {
		super.authenticate("author1");

		final Collection<Comment> res = this.panelService.findOne(this.getEntityId("panel1")).getComments();

		Assert.notNull(res);

		super.unauthenticate();

	}
	/*
	 * Test comprobación un autor lista los comments de una activity tutorial.
	 * Req Funcional: 22.1
	 */
	@Test
	public void testListCommentsActTut() {
		super.authenticate("author1");

		final Collection<Comment> res = this.tutorialService.findOne(this.getEntityId("tutorial1")).getComments();

		Assert.notNull(res);

		super.unauthenticate();

	}
	/*
	 * Test comprobación un autor lista los comments de una activity presentation.
	 * Req Funcional: 22.1
	 */
	@Test
	public void testListCommentsActPres() {
		super.authenticate("author1");

		final Collection<Comment> res = this.presentationService.findOne(this.getEntityId("presentation1")).getComments();

		Assert.notNull(res);

		super.unauthenticate();

	}
	/*
	 * Test comprobación un author crea un comment.
	 * Req Funcional 22.2:
	 */
	@Test
	public void testCreateComment() {
		super.authenticate("author1");

		final Comment res = this.commentService.create();
		Assert.notNull(res);

		res.setAuthor("Juan");
		res.setMoment(new Date());
		res.setText("Prueba");
		res.setTitle("title");

		final Comment result = this.commentService.save(res);

		Assert.notNull(result);
		Assert.isTrue(this.commentService.findAll().contains(result));

		super.unauthenticate();

	}

}
