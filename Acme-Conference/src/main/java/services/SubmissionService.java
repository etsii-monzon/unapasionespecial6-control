
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SubmissionRepository;
import domain.Author;
import domain.Conference;
import domain.Paper;
import domain.Report;
import domain.Reviewer;
import domain.Submission;

@Service
@Transactional
public class SubmissionService {

	//Managed repository
	@Autowired
	private SubmissionRepository	submissionRepository;

	//Supporting services

	@Autowired
	private AuthorService			authorService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private ReportService			reportService;
	@Autowired
	private PaperService			paperService;
	@Autowired
	private MessageService			messageService;
	@Autowired
	private ReviewerService			revService;
	@Autowired
	private ConferenceService		confService;


	// SIMPLE CRUD METHODS

	public Submission create() {
		Assert.isTrue(this.authorService.checkPrincipal());

		Submission sub;
		sub = new Submission();

		final Date actualMoment = new java.util.Date();
		//Creamos paper y a�adimos al actor principal como autor
		final Paper paper = this.paperService.create();

		sub.setTicker(this.createSubmissionTicker());
		sub.setMoment(actualMoment);
		sub.setStatus("UNDER-REVIEW");
		sub.setPaper(paper);
		sub.setCameraReady(false);
		return sub;
	}
	public Collection<Submission> findAll() {

		Collection<Submission> fms;
		fms = this.submissionRepository.findAll();

		return fms;

	}
	public Submission findOne(final int submissionId) {

		Assert.notNull(submissionId);
		Submission fm;
		fm = this.submissionRepository.findOne(submissionId);

		return fm;
	}
	public Submission save(final Submission a) {
		Assert.notNull(a);
		Submission res;
		final Author b = this.authorService.findByPrincipal();
		if (b != null) {
			final Collection<Submission> f = b.getSubmissions();

			if (a.getStatus().equals("ACCEPTED") && a.getId() != 0)
				a.setCameraReady(true);
			res = this.submissionRepository.save(a);
			if (!f.contains(res))
				f.add(res);
		} else {
			Assert.isTrue(!a.getReviewers().isEmpty(), "no revs");
			Assert.notNull(a.getReviewers(), "no revs");
			res = this.submissionRepository.save(a);
		}
		return res;
	}

	public void delete(final Submission a) {
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);

		final Author adm = this.authorService.findByPrincipal();
		adm.getSubmissions().remove(a);
		final Collection<Report> reports = this.reportService.findAll();
		for (final Report r : reports)
			if (r.getSubmission().equals(a))
				this.reportService.delete(r);

		this.paperService.delete(a.getPaper());

		this.submissionRepository.delete(a);

	}

	public void submissionStatus(final int submissionId) {
		final Submission res;

		final Collection<Report> reports = new ArrayList<>();
		final Collection<Report> reportsAc = new ArrayList<>();
		final Collection<Report> reportsRe = new ArrayList<>();

		Collection<Report> reportsAll;

		reportsAll = this.reportService.findAll();
		for (final Report r : reportsAll)
			if (r.getSubmission().equals(this.findOne(submissionId)))
				reports.add(r);

		for (final Report rep : reports)
			if (rep.getDecision().equals("ACCEPT"))
				reportsAc.add(rep);
			else if (rep.getDecision().equals("REJECT"))
				reportsRe.add(rep);

		if (reportsRe.size() > reportsAc.size()) {
			this.findOne(submissionId).setStatus("REJECTED");
			res = this.submissionRepository.save(this.findOne(submissionId));
		} else {
			this.findOne(submissionId).setStatus("ACCEPTED");
			res = this.submissionRepository.save(this.findOne(submissionId));

		}

		//Notificaci�n Decisi�n
		this.messageService.notificationDecision(res);

		//		System.out.println("reportsAll" + reportsAll.size());
		//		System.out.println("reports" + reports.size());
		//
		//		System.out.println("acepted" + reportsAc.size());
		//		System.out.println("rejected" + reportsRe.size());

	}

	public Collection<Reviewer> findAllReviewersNotSubmission(final int submissionId) {
		final Collection<Reviewer> total = this.revService.findAll();
		final Submission sub = this.findOne(submissionId);

		for (final Reviewer rev : sub.getReviewers()) {
			final Collection<Report> repots = rev.getReports();

			for (final Report rep : repots)
				if (rep.getSubmission().equals(sub))
					total.remove(rev);
		}

		return total;
	}
	public void assignReviewers() {
		final Collection<Submission> submissions = new ArrayList<Submission>();

		for (final Submission sub : this.findAll())
			if (sub.getStatus().equals("UNDER-REVIEW"))
				submissions.add(sub);

		for (final Submission s : submissions) {
			final Collection<Reviewer> reviewers = this.revService.findAll();

			for (final Reviewer r : s.getReviewers())
				reviewers.remove(r);
			final String p = s.getConference().getTitle() + " " + s.getConference().getSummary();

			for (final Reviewer r : reviewers) {
				if (s.getReviewers().size() == 3)
					break;

				for (final String key : r.getKeywords())
					if (p.contains(key)) {
						s.getReviewers().add(r);
						this.submissionRepository.save(s);
						break;
					}

			}

		}

	}

	public Collection<Submission> findAllCameraReadyVersion(final int conferenceId) {
		final Collection<Submission> res = new ArrayList<Submission>();
		final Collection<Submission> aux = this.submissionRepository.findAll();
		final Conference conf = this.confService.findOne(conferenceId);

		for (final Submission s : aux)
			if (s.getConference().equals(conf))
				if (s.isCameraReady())
					res.add(s);

		return res;
	}

	public Double avgSubmissionsPerConference() {
		return this.submissionRepository.avgSubmissionsPerConference();
	}

	public Integer minSubmissionsPerConference() {
		return this.submissionRepository.minSubmissionsPerConference();
	}

	public Integer maxSubmissionsPerConference() {
		return this.submissionRepository.maxSubmissionsPerConference();
	}

	public Double stdDevSubmissionsPerConference() {
		return this.submissionRepository.stdDevSubmissionsPerConference();
	}

	public Submission checkTicker(final String ticker) {
		return this.submissionRepository.checkTicker(ticker);
	}
	public String createSubmissionTicker() {

		final char a = this.authorService.findByPrincipal().getName().charAt(0);
		final char b = this.authorService.findByPrincipal().getSurname().charAt(0);
		final String x = "XX";
		char c = x.charAt(0);
		if (this.authorService.findByPrincipal().getMiddleName().length() > 1)
			c = this.authorService.findByPrincipal().getMiddleName().charAt(0);
		else
			c = x.charAt(0);

		final String ticker = a + "" + c + "" + b + "-" + this.configurationService.createTicker();

		//Comprobamos unicidad del ticker
		//Si el Ticker ya existe,hacemos una llamada recursiva al m�todo para crear otro
		if (this.checkTicker(ticker) != null)
			return this.createSubmissionTicker();
		else
			//Si el ticker no existe(devuelve null) devolvemos el generado
			return ticker;

	}
	public Collection<Submission> findSubmissionsOfReviewer(final Reviewer r) {
		return this.submissionRepository.findSubmissionsOfReviewer(r);
	}

	public Collection<Submission> getSubmissionGroupedByStatus() {
		return this.submissionRepository.getSubmissionGroupedByStatus();
	}
}
