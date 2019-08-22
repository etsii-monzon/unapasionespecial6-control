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

import java.util.ArrayList;
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
import domain.Author;
import domain.Conference;
import domain.Paper;
import domain.Reviewer;
import domain.Submission;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SubmissionServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private SubmissionService	submissionService;

	@Autowired
	private AuthorService		authorService;

	@Autowired
	private ConferenceService	conferenceService;
	@Autowired
	private PaperService		paperService;
	@Autowired
	private ReviewerService		revService;


	// Tests ------------------------------------------------------------------

	// The following are fictitious test cases that are intended to check that 
	// JUnit works well in this project.  Just righ-click this class and run 
	// it using JUnit.

	/*
	 * Test comprobaci�n un author lista sus submissions.
	 * Req Funcional: 13.1
	 */
	@Test
	public void testListSubmissions() {
		super.authenticate("author1");

		final Collection<Submission> res = this.authorService.findByPrincipal().getSubmissions();

		Assert.notNull(res);

		super.unauthenticate();

	}
	/*
	 * Test comprobaci�n un author crea un submission.
	 * Req Funcional: 13.1
	 */
	@Test
	public void testCreateSubmission() {
		super.authenticate("author1");

		final Submission res = this.submissionService.create();
		Assert.notNull(res);
		final Conference conference = this.conferenceService.findOne(this.getEntityId("conference1"));
		final Paper paper = this.paperService.create();
		paper.setDocument("http://document.com");
		paper.setSummary("summary");
		paper.setTitle("title");
		final Collection<Author> authors = new ArrayList<>();
		authors.add(this.authorService.findByPrincipal());
		paper.setAuthors(authors);

		res.setConference(conference);
		res.setCameraReady(false);
		res.setMoment(new Date());
		res.setPaper(paper);
		//	res.setReviewers(reviewers);
		res.setStatus("UNDER-REVIEW");
		res.setTicker("MSE-1231");

		final Submission result = this.submissionService.save(res);

		Assert.notNull(result);
		Assert.isTrue(this.submissionService.findAll().contains(result));
		Assert.isTrue(this.authorService.findByPrincipal().getSubmissions().contains(result));

		super.unauthenticate();

	}

	/*
	 * Test comprobaci�n un author sube la versi�n definitiva de su submission aceptada antes de que se acabe el plazo.
	 * Req Funcional: 13.2
	 */

	@Test
	public void testUploadCameraReadyVersion() {
		super.authenticate("author1");

		final int id = super.getEntityId("submission2");

		final Submission subm = this.submissionService.findOne(id);

		//SOLO HAY QUE REVISAR LOS DATOS Y VER SI HAY ALGO A CAMBIAR.
		subm.getPaper().setSummary("Summary ymmary summar");
		final Submission res = this.submissionService.save(subm);
		Assert.isTrue(res.isCameraReady(), "No se pone la camera ready version");
		super.unauthenticate();
	}

	/*
	 * Test comprobaci�n un admin asigna manualmente reviewers a una submission concreta estando �sta UNDER-REVIEW.
	 * Req Funcional: 14.3
	 */
	@Test
	public void testAssignReviewersManually() {
		super.authenticate("admin");

		final int id = super.getEntityId("submission1");
		final int revId = super.getEntityId("reviewer1");

		final Reviewer rev = this.revService.findOne(revId);
		final Submission subm = this.submissionService.findOne(id);
		subm.getReviewers().add(rev);
		final Submission res = this.submissionService.save(subm);
		Assert.isTrue(!res.getReviewers().isEmpty(), "No tiene reviewers asignados");

		super.unauthenticate();
	}

	/*
	 * Test comprobaci�n un admin asigna automaticamente reviewers a todas las submissions que est�n UNDER-REVIEW .
	 * Req Funcional: 14.3
	 */
	@Test
	public void testAssignReviewersAutomatically() {
		super.authenticate("admin");
		Boolean aux = false;

		final Collection<Submission> subms = this.submissionService.findAll();
		this.submissionService.assignReviewers();

		for (final Submission s : subms)
			if (!s.getReviewers().isEmpty())
				aux = true;
		//En el PopulateDatabase.xml, hay reviewers con keywords coincidentes con algunas submissions existentes.
		Assert.isTrue(aux);
	}
	/*
	 * Test comprobaci�n un admin hace el procedure de la toma de decisi�n.
	 * Req Funcional: 14.4
	 */

	@Test
	public void testDecisionMaking() {
		super.authenticate("admin");

		final Submission sub = this.submissionService.findOne(this.getEntityId("submission1"));
		Assert.isTrue(sub.getStatus().equals("UNDER-REVIEW"));
		this.submissionService.submissionStatus(sub.getId());
		Assert.isTrue(sub.getStatus().equals("ACCEPTED"));
		super.unauthenticate();

	}
}
