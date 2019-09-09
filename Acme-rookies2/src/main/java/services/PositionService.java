
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PositionRepository;
import domain.Audit;
import domain.Company;
import domain.Position;
import domain.Problem;
import domain.Xomp;

@Service
@Transactional
public class PositionService {

	//Managed repository
	@Autowired
	private PositionRepository		positionRepository;

	//Supporting services
	@Autowired
	private CompanyService			companyService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private AdministratorService	adminService;
	@Autowired
	private ProblemService			problemService;

	@Autowired
	private AuditService			auditService;
	@Autowired
	private XompService				xompService;


	// SIMPLE CRUD METHODS

	public Position create() {
		Assert.isTrue(this.companyService.checkPrincipal());
		String tick = new String();
		Position pos;
		pos = new Position();
		final Company comp = this.companyService.findByPrincipal();
		final String comercialName = comp.getCommercialName();
		if (comercialName.length() == 1)
			tick = comercialName.charAt(0) + "" + "XXX";
		else if (comercialName.length() == 2)
			tick = comercialName.charAt(0) + "" + comercialName.charAt(1) + "" + "XX";
		else if (comercialName.length() == 3)
			tick = comercialName.charAt(0) + "" + comercialName.charAt(1) + "" + comercialName.charAt(2) + "" + "X";
		else if (comercialName.length() > 3)
			tick = comercialName.charAt(0) + "" + comercialName.charAt(1) + "" + comercialName.charAt(2) + "" + comercialName.charAt(3);

		pos.setTicker(tick + "-" + this.configurationService.createTicker());

		//		pro.setDraftMode(true);

		return pos;
	}

	public Collection<Position> findAll() {

		Collection<Position> posis;
		posis = this.positionRepository.findAll();

		return posis;

	}
	public Position findOne(final int positionId) {

		Assert.notNull(positionId);
		Position pos;
		pos = this.positionRepository.findOne(positionId);

		return pos;
	}
	public Position save(final Position a) {
		Assert.isTrue(this.companyService.checkPrincipal());

		Assert.notNull(a);
		final Position res;

		//		Collection<Problem> problems = this.problemService.findAll();
		//		Collection<Problem> probs=new ArrayList<>();
		//		for(Problem p:problems){
		//			if(p.getPosition().equals(a)){
		//				probs.add(p);
		//			}
		//		}
		//		if(probs.size()<2){
		//			a.isDraftMode()==true;
		//		}

		final Company b = this.companyService.findByPrincipal();
		final Collection<Position> f = b.getPositions();
		res = this.positionRepository.save(a);
		if (!f.contains(res))
			f.add(res);

		return res;
	}
	public void delete(final Position p) {
		Assert.isTrue(this.companyService.checkPrincipal());
		Assert.notNull(p);
		Assert.isTrue(p.getId() != 0);
		final Collection<Problem> toDelete = new ArrayList<>();
		final Collection<Audit> toDeleteAud = new ArrayList<>();
		final Collection<Audit> audits = this.auditService.findAll();
		final Collection<Xomp> xomps = this.xompService.findAll();

		final Company comp = this.companyService.findByPrincipal();
		for (final Problem p2 : comp.getProblems())
			if (p2.getPosition().equals(p))
				toDelete.add(p2);
		for (final Problem p3 : toDelete)
			this.problemService.delete(p3);

		for (final Audit a2 : audits)
			if (a2.getPosition().equals(p))
				toDeleteAud.add(a2);
		for (final Audit a3 : toDeleteAud)
			this.auditService.deleteAuditPosition(a3);
		for (final Xomp x : xomps)
			if (x.getPosition().equals(p))
				this.xompService.delete(x);

		comp.getPositions().remove(p);

		this.positionRepository.delete(p);
	}

	public Collection<Position> searchPositions(final String keyword) {
		return this.positionRepository.searchPositions(keyword);
	}

	//	The average, the minimum, the maximum, and the standard deviation of the
	//	salaries offered.

	public Double averageSalaries() {
		this.adminService.checkPrincipal();
		return this.positionRepository.averageSalaries();
	}

	public Double minSalary() {
		this.adminService.checkPrincipal();
		return this.positionRepository.minSalary();
	}

	public Double maxSalary() {
		this.adminService.checkPrincipal();
		return this.positionRepository.maxSalary();
	}

	public Double stddevSalary() {
		this.adminService.checkPrincipal();
		return this.positionRepository.stddevSalary();
	}

	//The best and the worst position in terms of salary.

	public String findBestPosition() {
		this.adminService.checkPrincipal();
		return this.positionRepository.findBestPosition();
	}

	public String findWorstPosition() {
		this.adminService.checkPrincipal();
		return this.positionRepository.findWorstPosition();
	}
	public Collection<Position> getPositionsOfCompanies(final Collection<Company> companies) {
		final Collection<Position> res = new ArrayList<>();
		for (final Company c : companies)
			res.addAll(c.getPositions());

		return res;
	}

}
