
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
import domain.Panel;
import domain.Presentation;
import domain.Section;
import domain.Submission;
import domain.Tutorial;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ActivityServiceTest extends AbstractTest {

	@Autowired
	private ActivityService		activityService;

	@Autowired
	private PresentationService	presentationService;

	@Autowired
	private PanelService		panelService;

	@Autowired
	private ConferenceService	confService;

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private SectionService		secService;

	@Autowired
	private SubmissionService	submissionService;


	/*
	 * Test comprobación un admin lista todas las activities de una conference.
	 * Req Funcional: 14.6
	 */
	@Test
	public void testListActivities() {
		super.authenticate("admin");

		final int id = this.getEntityId("conference7");
		Assert.notNull(this.activityService.findAllActivitiesByConference(this.confService.findOne(id)));

		super.unauthenticate();
	}

	/*
	 * Test comprobación un admin crea un panel para una conference.
	 * Req Funcional: 14.6
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testCreatePanel() {
		super.authenticate("admin");
		final Date end = new Date();
		end.setHours(21);
		final Collection<String> speakers = new ArrayList<String>();
		speakers.add("migue");
		final Collection<String> attachs = new ArrayList<String>();
		attachs.add("https://www.github.com/");

		final int id = this.getEntityId("conference7");
		final Panel pa = this.panelService.create(id);
		pa.setStartMoment(new Date());
		pa.setEndMoment(end);
		pa.setRoom("la sala");
		pa.setSpeakers(speakers);
		pa.setSummary("summa");
		pa.setTitle("title");
		pa.setOptionalAttachments(attachs);

		final Panel res = this.panelService.save(pa);
		Assert.notNull(res);

		super.unauthenticate();
	}

	/*
	 * Test comprobación un admin borra un panel de una conference.
	 * Req Funcional: 14.6
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testDeletePanel() {
		super.authenticate("admin");
		final Date end = new Date();
		end.setHours(21);
		final Collection<String> speakers = new ArrayList<String>();
		speakers.add("migue");
		final Collection<String> attachs = new ArrayList<String>();
		attachs.add("https://www.github.com/");

		final int id = this.getEntityId("conference7");
		final Panel pa = this.panelService.create(id);
		pa.setStartMoment(new Date());
		pa.setEndMoment(end);
		pa.setRoom("la sala");
		pa.setSpeakers(speakers);
		pa.setSummary("summa");
		pa.setTitle("title");
		pa.setOptionalAttachments(attachs);

		final Panel res = this.panelService.save(pa);
		Assert.notNull(res);

		final int panId = res.getId();
		this.panelService.delete(res);
		Assert.isNull(this.panelService.findOne(panId));

		super.unauthenticate();
	}

	/*
	 * Test comprobación un admin actualiza un panel de una conference.
	 * Req Funcional: 14.6
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testUpdatePanel() {
		super.authenticate("admin");
		final Date end = new Date();
		end.setHours(21);
		final Collection<String> speakers = new ArrayList<String>();
		speakers.add("migue");
		final Collection<String> attachs = new ArrayList<String>();
		attachs.add("https://www.github.com/");

		final int id = this.getEntityId("conference7");
		final Panel pa = this.panelService.create(id);
		pa.setStartMoment(new Date());
		pa.setEndMoment(end);
		pa.setRoom("la sala");
		pa.setSpeakers(speakers);
		pa.setSummary("summa");
		pa.setTitle("title");
		pa.setOptionalAttachments(attachs);

		final Panel res = this.panelService.save(pa);
		Assert.notNull(res);

		res.setTitle("El titulo");
		Assert.notNull(this.panelService.save(res));

		super.unauthenticate();
	}

	/*
	 * Test comprobación un admin crea un tutorial, y una section para él, para una conference.
	 * Req Funcional: 14.6
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testCreateTutorialAndSection() {
		super.authenticate("admin");
		final Date end = new Date();
		end.setHours(21);
		final Collection<String> speakers = new ArrayList<String>();
		speakers.add("migue");
		final Collection<String> attachs = new ArrayList<String>();
		attachs.add("https://www.github.com/");

		final int id = this.getEntityId("conference7");
		final Tutorial tu = this.tutorialService.create(id);
		tu.setStartMoment(new Date());
		tu.setEndMoment(end);
		tu.setRoom("la sala");
		tu.setSpeakers(speakers);
		tu.setSummary("summa");
		tu.setTitle("title");
		tu.setOptionalAttachments(attachs);

		final Tutorial res = this.tutorialService.save(tu);
		Assert.notNull(res);

		final Section sec = this.secService.create(res.getId());
		final Collection<String> pictures = new ArrayList<String>();
		pictures.add("https://www.github.com/dodp.jpg");

		sec.setTitle("title");
		sec.setSummary("summardd");
		sec.setOptionalPictures(pictures);
		final Section aux = this.secService.save(sec);

		Assert.notNull(aux);

		super.unauthenticate();
	}

	/*
	 * Test comprobación un admin actualiza un tutorial para una conference.
	 * Req Funcional: 14.6
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testUpdateTutorial() {
		super.authenticate("admin");
		final Date end = new Date();
		end.setHours(21);
		final Collection<String> speakers = new ArrayList<String>();
		speakers.add("migue");
		final Collection<String> attachs = new ArrayList<String>();
		attachs.add("https://www.github.com/");

		final int id = this.getEntityId("conference7");
		final Tutorial tu = this.tutorialService.create(id);
		tu.setStartMoment(new Date());
		tu.setEndMoment(end);
		tu.setRoom("la sala");
		tu.setSpeakers(speakers);
		tu.setSummary("summa");
		tu.setTitle("title");
		tu.setOptionalAttachments(attachs);

		final Tutorial res = this.tutorialService.save(tu);
		Assert.notNull(res);

		final Section sec = this.secService.create(res.getId());
		final Collection<String> pictures = new ArrayList<String>();
		pictures.add("https://www.github.com/dodp.jpg");

		sec.setTitle("title");
		sec.setSummary("summardd");
		sec.setOptionalPictures(pictures);
		final Section aux = this.secService.save(sec);

		Assert.notNull(aux);
		res.setRoom("El salon");
		final Tutorial tot = this.tutorialService.save(res);
		Assert.notNull(tot);

		super.unauthenticate();
	}

	/*
	 * Test comprobación un admin borra un tutorial, y sus sections, de una conference.
	 * Req Funcional: 14.6
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testDeleteTutorial() {
		super.authenticate("admin");
		final Date end = new Date();
		end.setHours(21);
		final Collection<String> speakers = new ArrayList<String>();
		speakers.add("migue");
		final Collection<String> attachs = new ArrayList<String>();
		attachs.add("https://www.github.com/");

		final int id = this.getEntityId("conference7");
		final Tutorial tu = this.tutorialService.create(id);
		tu.setStartMoment(new Date());
		tu.setEndMoment(end);
		tu.setRoom("la sala");
		tu.setSpeakers(speakers);
		tu.setSummary("summa");
		tu.setTitle("title");
		tu.setOptionalAttachments(attachs);

		final Tutorial res = this.tutorialService.save(tu);
		Assert.notNull(res);

		final Section sec = this.secService.create(res.getId());
		final Collection<String> pictures = new ArrayList<String>();
		pictures.add("https://www.github.com/dodp.jpg");
		sec.setTitle("title");
		sec.setSummary("summardd");
		sec.setOptionalPictures(pictures);
		final Section aux = this.secService.save(sec);

		Assert.notNull(aux);

		final int tutId = res.getId();
		final int secId = aux.getId();

		this.tutorialService.delete(res);
		Assert.isNull(this.tutorialService.findOne(tutId));
		Assert.isNull(this.secService.findOne(secId));

		super.unauthenticate();
	}

	/*
	 * Test comprobación un admin crea una presentation para una conference.
	 * Req Funcional: 14.6
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testCreatePresentation() {
		super.authenticate("admin");

		final int subId = super.getEntityId("submission1");
		final Submission sub = this.submissionService.findOne(subId);
		sub.setCameraReady(true);
		final Submission aux = this.submissionService.save(sub);
		final Date end = new Date();
		end.setHours(21);
		final Collection<String> speakers = new ArrayList<String>();
		final Collection<String> attachs = new ArrayList<String>();
		attachs.add("https://www.github.com/");
		speakers.add("migue");

		final int id = this.getEntityId("conference7");
		final Presentation pr = this.presentationService.create(id);
		pr.setStartMoment(new Date());
		pr.setEndMoment(end);
		pr.setRoom("la sala");
		pr.setSpeakers(speakers);
		pr.setSummary("summa");
		pr.setTitle("title");
		pr.setSubmission(aux);
		pr.setOptionalAttachments(attachs);

		final Presentation res = this.presentationService.save(pr);
		Assert.notNull(res);

		super.unauthenticate();
	}

	/*
	 * Test comprobación un admin actualiza una presentation de una conference.
	 * Req Funcional: 14.6
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testUpdatePresentation() {
		super.authenticate("admin");

		final int subId = super.getEntityId("submission1");
		final Submission sub = this.submissionService.findOne(subId);
		sub.setCameraReady(true);
		final Submission aux = this.submissionService.save(sub);
		final Date end = new Date();
		end.setHours(21);
		final Collection<String> speakers = new ArrayList<String>();
		final Collection<String> attachs = new ArrayList<String>();
		speakers.add("migue");

		final int id = this.getEntityId("conference7");
		final Presentation pr = this.presentationService.create(id);
		pr.setStartMoment(new Date());
		pr.setEndMoment(end);
		pr.setRoom("la sala");
		pr.setSpeakers(speakers);
		pr.setSummary("summa");
		pr.setTitle("title");
		pr.setSubmission(aux);
		pr.setOptionalAttachments(attachs);

		final Presentation res = this.presentationService.save(pr);
		Assert.notNull(res);

		res.setSummary("Cambia el summari");
		Assert.notNull(this.presentationService.save(res));

		super.unauthenticate();
	}

	/*
	 * Test comprobación un admin borra una presentation para una conference.
	 * Req Funcional: 14.6
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testDeletePresentation() {
		super.authenticate("admin");

		final int subId = super.getEntityId("submission1");
		final Submission sub = this.submissionService.findOne(subId);
		sub.setCameraReady(true);
		final Submission aux = this.submissionService.save(sub);
		final Date end = new Date();
		end.setHours(21);
		final Collection<String> speakers = new ArrayList<String>();
		speakers.add("migue");
		final Collection<String> attachs = new ArrayList<String>();
		attachs.add("https://www.github.com/");

		final int id = this.getEntityId("conference7");
		final Presentation pr = this.presentationService.create(id);
		pr.setStartMoment(new Date());
		pr.setEndMoment(end);
		pr.setRoom("la sala");
		pr.setSpeakers(speakers);
		pr.setSummary("summa");
		pr.setTitle("title");
		pr.setSubmission(aux);
		pr.setOptionalAttachments(attachs);

		final Presentation res = this.presentationService.save(pr);
		Assert.notNull(res);

		final int presId = res.getId();
		this.presentationService.delete(res);
		Assert.isNull(this.presentationService.findOne(presId));

		super.unauthenticate();
	}
}
