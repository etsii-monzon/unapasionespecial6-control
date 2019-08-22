
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import domain.Report;
import domain.Reviewer;
import domain.Submission;

@Service
@Transactional
public class ReportService {

	//Managed repository
	@Autowired
	private ReportRepository	reportRepository;

	//Supporting services
	@Autowired
	private ReviewerService		reviewerService;
	@Autowired
	private SubmissionService	submissionService;


	// SIMPLE CRUD METHODS

	public Report create(final int subId) {
		Assert.isTrue(this.reviewerService.checkPrincipal());

		final Report rep = new Report();
		rep.setSubmission(this.submissionService.findOne(subId));
		return rep;
	}
	public Collection<Report> findAll() {

		Collection<Report> reps;
		reps = this.reportRepository.findAll();

		return reps;

	}
	public Report findOne(final int reportId) {

		Assert.notNull(reportId);
		Report rep;
		rep = this.reportRepository.findOne(reportId);

		return rep;
	}
	public Report save(final Report a) {
		Assert.isTrue(this.reviewerService.checkPrincipal());

		Assert.notNull(a);
		final Report res;

		final Reviewer b = this.reviewerService.findByPrincipal();
		final Collection<Report> f = b.getReports();
		res = this.reportRepository.save(a);
		if (!f.contains(res))
			f.add(res);

		return res;
	}
	public void delete(final Report p) {
		//this.reviewerService.checkPrincipal();

		Assert.notNull(p);
		Assert.isTrue(p.getId() != 0);

		final Reviewer dir = this.reviewerService.findByPrincipal();

		dir.getReports().remove(p);

		this.reportRepository.delete(p);
	}

	public Collection<Report> findReportsOfSubmission(final Submission s) {
		return this.reportRepository.findReportsOfSubmission(s);
	}

}
