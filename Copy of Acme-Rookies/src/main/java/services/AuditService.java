
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AuditRepository;
import domain.Audit;
import domain.Auditor;
import domain.Company;
import domain.Position;

@Service
@Transactional
public class AuditService {

	//Managed repository
	@Autowired
	private AuditRepository			auditRepository;

	//Supporting services
	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private AdministratorService	adminService;


	// SIMPLE CRUD METHODS

	public Audit create() {
		this.auditorService.checkPrincipal();
		Audit aud;
		aud = new Audit();

		final java.util.Date fechaActual = new java.util.Date();

		aud.setMoment(fechaActual);

		return aud;
	}

	public Collection<Audit> findAll() {

		Collection<Audit> audits;
		audits = this.auditRepository.findAll();

		return audits;

	}
	public Audit findOne(final int auditId) {

		Assert.notNull(auditId);
		Audit audit;
		audit = this.auditRepository.findOne(auditId);

		return audit;
	}
	public Audit save(final Audit a) {
		this.auditorService.checkPrincipal();

		Assert.notNull(a);
		final Audit res;

		final Auditor b = this.auditorService.findByPrincipal();
		final Collection<Audit> f = b.getAudits();
		res = this.auditRepository.save(a);
		if (!f.contains(res))
			f.add(res);

		return res;
	}
	public void delete(final Audit p) {
		this.auditorService.checkPrincipal();

		Assert.notNull(p);
		Assert.isTrue(p.getId() != 0);
		Audit toDelete = new Audit();

		//ELiminamos audits del auditor
		for (final Auditor a : this.auditorService.findAll()) {
			for (final Audit au : a.getAudits())
				if (au.equals(p))
					toDelete = au;
			a.getAudits().remove(toDelete);
		}
		this.auditRepository.delete(p);
	}

	public void deleteAuditPosition(final Audit p) {

		Assert.notNull(p);
		Assert.isTrue(p.getId() != 0);
		Audit toDelete = new Audit();

		//ELiminamos audits del auditor
		for (final Auditor a : this.auditorService.findAll()) {
			for (final Audit au : a.getAudits())
				if (au.equals(p))
					toDelete = au;
			a.getAudits().remove(toDelete);
		}
		this.auditRepository.delete(p);
	}

	public Audit getAuditByPosition(final Position pos) {
		Audit res = new Audit();
		final Collection<Audit> aux = this.findAll();

		for (final Audit a : aux)
			if (a.getPosition().equals(pos)) {
				res = a;
				break;
			}
		return res;
	}

	public Map<Company, Double> auditScoresCompany() {
		//this.adminService.checkPrincipal();
		final Map<Company, Double> rest = new HashMap<Company, Double>();
		final Collection<Company> companies = this.companyService.findAll();

		for (final Company c : companies) {
			Double r = 0.0;
			for (final Position p : c.getPositions()) {
				final Integer n = this.getAuditByPosition(p).getScore();
				if (n == null || n.equals(null)) {
					r = 0.0;
					break;
				} else
					r += n;

			}

			r = r / c.getPositions().size();
			r = r / 10;
			r = rest.put(c, r);

		}

		return rest;

	}

	public Map<Company, Double> processAuditScores() {
		this.adminService.checkPrincipal();
		return this.auditScoresCompany();
	}
	public Double avgAuditScorePerCompany() {
		Double res = 0.0;
		final Map<Company, Double> compute = this.auditScoresCompany();
		final int num = compute.keySet().size();

		for (final Company c : compute.keySet())
			if (compute.get(c) != null || !compute.get(c).equals(null))
				res += compute.get(c);
		res = res / num;
		return res;
	}

	public Double minAuditScorePerCompany() {
		Double res = 0.0;
		Double aux = 0.0;
		final Map<Company, Double> compute = this.auditScoresCompany();

		for (final Company c : compute.keySet()) {
			aux = compute.get(c);

			if (c == compute.keySet().toArray()[0])
				res = aux;

			if (aux < res)
				res = aux;
		}

		return res;

	}
	public Double maxAuditScorePerCompany() {
		Double res = 0.0;
		Double aux = 0.0;
		final Map<Company, Double> compute = this.auditScoresCompany();

		for (final Company c : compute.keySet()) {
			aux = compute.get(c);

			if (c == compute.keySet().toArray()[0])
				res = aux;

			if (aux > res)
				res = aux;
		}

		return res;

	}

	public Double stdDevAuditScorePerCompany() {
		final Map<Company, Double> auditScores = this.auditScoresCompany();
		final Collection<Double> aux = auditScores.values();
		Double res = 0.0;
		Double sum = 0.0;
		final Double len = 1.0 * aux.size();
		final Double avg = this.avgAuditScorePerCompany() * this.avgAuditScorePerCompany();

		for (final Double d : aux)
			sum += d * d;

		res = Math.sqrt(sum / Math.abs(len - avg));

		return res;
	}

	public Collection<String> highestScore() {
		final Collection<String> res = new ArrayList<String>();
		final Map<Company, Double> auditScore = this.auditScoresCompany();
		final Double highest = this.maxAuditScorePerCompany();

		for (final Company c : auditScore.keySet())
			if (auditScore.get(c) == highest || auditScore.get(c).equals(highest))
				res.add(c.getCommercialName());
		return res;
	}

	public Double avgAuditScoresPerPosition() {
		return this.auditRepository.avgAuditScoresPerPosition();
	}

	public Double minAuditScoresPerPosition() {
		return this.auditRepository.minAuditScoresPerPosition();
	}

	public Double maxAuditScoresPerPosition() {
		return this.auditRepository.maxAuditScoresPerPosition();
	}

	public Double stdDevAuditScoresPerPosition() {
		return this.auditRepository.stdDevAuditScoresPerPosition();
	}

	public Double avgSalaryFromHighestAuditScorePositions() {
		return this.auditRepository.avgSalaryFromHighestAuditScorePositions();
	}

}
