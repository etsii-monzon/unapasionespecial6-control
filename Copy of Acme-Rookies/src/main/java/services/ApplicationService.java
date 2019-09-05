
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import repositories.HackerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Hacker;
import domain.Position;
import domain.Problem;

@Service
@Transactional
public class ApplicationService {

	//Managed repository
	@Autowired
	private ApplicationRepository	appRepository;

	//Supporting services
	@Autowired
	private PositionService			posService;

	@Autowired
	private ProblemService			problemService;

	@Autowired
	private HackerRepository		hackerRepository;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CompanyService			companyService;


	public Application create(final int positionId) {
		Assert.isTrue(this.hackerService.checkPrincipal());
		Application app;
		Position pos;
		Problem prob;
		String status;
		Date moment;

		app = new Application();
		status = "PENDING";
		moment = new Date();
		pos = this.posService.findOne(positionId);
		prob = this.problemService.getArbitraryProblem(pos);
		//pro = this.problemService.save(prob);

		app.setPosition(pos);
		app.setMoment(moment);
		app.setStatus(status);
		app.setProblem(prob);

		return app;

	}

	public Application save(final Application app) {
		Assert.notNull(app);
		Application res;
		//		Boolean stat = false;
		//
		//		if (app.getStatus() == "ACCEPTED" || app.getStatus() == "REJECTED")
		//			stat = true;

		if (app.getExplanations() != null && app.getLink() != null && app.getStatus().equals("PENDING")) {
			this.companyService.checkPrincipal();
			app.setStatus("SUBMITTED");
			final Date mom = new Date();
			app.setMomentSumbit(mom);
		}

		if (app.getExplanations() != null)
			if (app.getId() != 0)
				Assert.isTrue(app.getStatus() != "PENDING" || !app.getStatus().equals("PENDING"));

		res = this.appRepository.save(app);

		if (app.getId() == 0) {
			final Hacker h = this.hackerService.findByPrincipal();
			h.getApplications().add(res);
			this.hackerService.save(h);

		}

		return res;
	}
	public Application findOne(final int appId) {
		Assert.isTrue(this.companyService.checkPrincipal() || this.hackerService.checkPrincipal());
		return this.appRepository.findOne(appId);
	}
	public void delete(final Application c) {
		Assert.notNull(c);
		Assert.isTrue(c.getId() != 0);
		Assert.notNull(this.appRepository.findOne(c.getId()));
		final Collection<Application> todelete = new ArrayList<>();

		for (final Hacker h : this.hackerService.findAll()) {
			for (final Application a : h.getApplications())
				if (a.equals(c))
					todelete.add(a);
			h.getApplications().removeAll(todelete);
		}
		//		this.problemService.delete(c.getProblem());

		this.appRepository.delete(c);
	}

	public Collection<Application> findAll() {
		final Collection<Application> res = new ArrayList<Application>();
		final Collection<Application> total = this.hackerService.findByPrincipal().getApplications();
		final Collection<Application> appPend = new ArrayList<Application>();
		final Collection<Application> appSubm = new ArrayList<Application>();
		final Collection<Application> appAcpt = new ArrayList<Application>();
		final Collection<Application> appRejc = new ArrayList<Application>();

		for (final Application a : total) {
			final String sta = a.getStatus();
			if (sta == "PENDING" || sta.equals("PENDING"))
				appPend.add(a);
			else if (sta == "SUBMITTED" || sta.equals("SUBMITTED"))
				appSubm.add(a);
			else if (sta == "ACCEPTED" || sta.equals("ACCEPTED"))
				appAcpt.add(a);
			else if (sta == "REJECTED" || sta.equals("REJECTED"))
				appRejc.add(a);

		}
		res.addAll(appPend);
		res.addAll(appSubm);
		res.addAll(appAcpt);
		res.addAll(appRejc);

		return res;
	}

	public Collection<Application> findAllByPosition(final Position pos) {
		Collection<Application> total;
		final Collection<Application> res = new ArrayList<Application>();

		final UserAccount u = LoginService.getPrincipal();
		final Collection<Authority> authorities = u.getAuthorities();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ROOKIE);
		final Hacker h = this.hackerRepository.findByUserAccountId(u.getId());

		if (authorities.contains(auth)) {
			total = new ArrayList<Application>();
			total.addAll(h.getApplications());
		} else
			total = this.appRepository.findAll();

		for (final Application a : total)
			if (a.getPosition().equals(pos) || a.getPosition() == pos)
				res.add(a);
		return res;

	}
	public Collection<Application> findAllGrouped(final Position pos) {
		final Collection<Application> res = new ArrayList<Application>();
		final Collection<Application> total = this.findAllByPosition(pos);
		final Collection<Application> appPend = new ArrayList<Application>();
		final Collection<Application> appSubm = new ArrayList<Application>();
		final Collection<Application> appAcpt = new ArrayList<Application>();
		final Collection<Application> appRejc = new ArrayList<Application>();

		for (final Application a : total) {
			final String sta = a.getStatus();
			if (sta == "PENDING" || sta.equals("PENDING"))
				appPend.add(a);
			else if (sta == "SUBMITTED" || sta.equals("SUBMITTED"))
				appSubm.add(a);
			else if (sta == "ACCEPTED" || sta.equals("ACCEPTED"))
				appAcpt.add(a);
			else if (sta == "REJECTED" || sta.equals("REJECTED"))
				appRejc.add(a);

		}
		res.addAll(appPend);
		res.addAll(appSubm);
		res.addAll(appAcpt);
		res.addAll(appRejc);

		return res;

	}
	public Collection<Application> findAllApplication() {

		Collection<Application> prosis;
		prosis = this.appRepository.findAll();

		return prosis;

	}

	public void flush() {
		this.appRepository.flush();
	}

}
