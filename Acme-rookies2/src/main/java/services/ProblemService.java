
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProblemRepository;
import domain.Application;
import domain.Company;
import domain.Position;
import domain.Problem;

@Service
@Transactional
public class ProblemService {

	//Managed repository
	@Autowired
	private ProblemRepository	problemRepository;

	//Supporting services
	@Autowired
	private CompanyService		companyService;

	@Autowired
	private ApplicationService	applicationService;


	// SIMPLE CRUD METHODS

	public Problem create() {
		Assert.isTrue(this.companyService.checkPrincipal());

		Problem pro;
		pro = new Problem();

		//		pro.setDraftMode(true);

		return pro;
	}

	public Collection<Problem> findAll() {

		Collection<Problem> prosis;
		prosis = this.problemRepository.findAll();

		return prosis;

	}
	public Problem findOne(final int problemId) {

		Assert.notNull(problemId);
		Problem pro;
		pro = this.problemRepository.findOne(problemId);

		return pro;
	}
	public Problem save(final Problem a) {
		Assert.isTrue(this.companyService.checkPrincipal());

		Assert.notNull(a);
		Problem res;
		final Company b = this.companyService.findByPrincipal();
		final Collection<Problem> f = b.getProblems();
		res = this.problemRepository.save(a);
		if (!f.contains(res))
			f.add(res);

		return res;
	}

	public void delete(final Problem p) {
		Assert.isTrue(this.companyService.checkPrincipal());

		Assert.notNull(p);
		Assert.isTrue(p.getId() != 0);

		final Company comp = this.companyService.findByPrincipal();

		for (final Application a : this.applicationService.findAllApplication())
			if (a.getProblem().equals(p))
				this.applicationService.delete(a);

		comp.getProblems().remove(p);

		this.problemRepository.delete(p);
	}
	public Problem getArbitraryProblem(final Position pos) {
		final Problem prob;
		final List<Problem> total = (List<Problem>) this.findAll();
		final List<Problem> aux = new ArrayList<Problem>();
		int len = 0;
		int r = 0;

		for (final Problem p : total)
			if (p.getPosition().equals(pos) || p.getPosition() == pos)
				aux.add(p);

		len = total.size();
		r = (int) (Math.random() * len);
		prob = total.get(r);

		return prob;
	}
}
