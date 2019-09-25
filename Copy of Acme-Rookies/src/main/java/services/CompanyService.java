
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CompanyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Company;
import domain.CreditCard;
import domain.Position;
import domain.Problem;
import forms.CompanyForm;

@Service
@Transactional
public class CompanyService {

	//Managed repository
	@Autowired
	private CompanyRepository		companyRepository;

	//Supporting services
	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private ProblemService			problemService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;


	// SIMPLE CRUD METHODS

	public Company create() {

		Company c;
		UserAccount userAccount;
		Authority auth;
		CreditCard cCard;

		//Authority
		c = new Company();
		userAccount = new UserAccount();
		auth = new Authority();
		cCard = new CreditCard();

		auth.setAuthority("COMPANY");
		userAccount.addAuthority(auth);
		if (this.configurationService.find().isReBrand() == true)
			userAccount.setNotify(true);
		c.setUserAccount(userAccount);
		c.setCreditCard(cCard);

		//Relationships

		final Collection<Problem> problems = new ArrayList<Problem>();
		final Collection<Position> positions = new ArrayList<Position>();

		c.setProblems(problems);
		c.setPositions(positions);

		return c;
	}

	public Collection<Company> findAll() {
		Collection<Company> companies;
		companies = this.companyRepository.findAll();
		//Assert.notNull(companies);
		return companies;

	}
	public Company findOne(final int companyId) {
		//Assert.notNull(companyId);
		Company c;
		c = this.companyRepository.findOne(companyId);
		return c;
	}
	public Company save(final Company c) {
		Assert.notNull(c);

		//		Assert.isTrue(this.actorService.checkUserEmail(c.getEmail()));
		//		Assert.isTrue(this.creditCardService.checkCreditCard(c.getCreditCard()));

		Assert.isTrue(!c.getUserAccount().getUsername().isEmpty());
		Assert.isTrue(!c.getUserAccount().getPassword().isEmpty());

		if (c.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(c.getUserAccount().getPassword(), null);
			c.getUserAccount().setPassword(hash);
		}
		if (c.getPhoneNumber() != null)
			if (!(c.getPhoneNumber().startsWith("+")))
				c.setPhoneNumber("+" + this.configurationService.find().getCountryCode() + " " + c.getPhoneNumber());

		Company res;
		res = this.companyRepository.save(c);

		return res;
	}
	public void delete(final Company c) {
		Assert.notNull(c);
		Assert.isTrue(c.getId() != 0);
		Assert.notNull(this.companyRepository.findOne(c.getId()));

		final Collection<Position> toDeletePositions = new ArrayList<>();
		final Collection<Problem> toDeleteProblems = new ArrayList<>();

		//Se eliminan los problems asociados a esta company
		for (final Problem pro : c.getProblems())
			toDeleteProblems.add(pro);
		for (final Problem pro2 : toDeleteProblems)
			this.problemService.delete(pro2);
		//Se eleiminan las positions acosicadas a esta company
		for (final Position p : c.getPositions())
			toDeletePositions.add(p);
		for (final Position p2 : toDeletePositions)
			this.positionService.delete(p2);

		//Eliminamos la creditCard asociada
		this.creditCardService.delete(c.getCreditCard());

		//Elimianmos al company
		this.companyRepository.delete(c);
	}
	public Company findByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);
		final Company res = this.companyRepository.findByUserAccountId(userAccountId);
		return res;
	}

	public Company findByPrincipal() {
		final UserAccount u = LoginService.getPrincipal();
		final Company res = this.companyRepository.findByUserAccountId(u.getId());
		return res;
	}

	public boolean checkPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.COMPANY);

		return authorities.contains(auth);
	}


	@Autowired
	private Validator	validator;


	public Company reconstruct(final CompanyForm companyForm, final BindingResult binding) {
		Company res;
		if (companyForm.getId() == 0) {

			res = this.create();
			res.setName(companyForm.getName());
			res.setSurname(companyForm.getSurname());
			res.setVat(companyForm.getVat());
			res.setOptionalPhoto(companyForm.getOptionalPhoto());
			res.setPhoneNumber(companyForm.getPhoneNumber());
			res.setEmail(companyForm.getEmail());
			res.setCommercialName(companyForm.getCommercialName());
			res.setOptionalAddress(companyForm.getOptionalAddress());

			res.getCreditCard().setHolderName(companyForm.getCreditCard().getHolderName());
			res.getCreditCard().setBrandName(companyForm.getCreditCard().getBrandName());
			res.getCreditCard().setNumber(companyForm.getCreditCard().getNumber());
			res.getCreditCard().setExpMonth(companyForm.getCreditCard().getExpMonth());
			res.getCreditCard().setExpYear(companyForm.getCreditCard().getExpYear());
			res.getCreditCard().setCvv(companyForm.getCreditCard().getCvv());
			res.getUserAccount().setUsername(companyForm.getUserAccount().getUsername());
			res.getUserAccount().setPassword(companyForm.getUserAccount().getPassword());

		} else {

			res = this.companyRepository.findOne(companyForm.getId());
			res.setName(companyForm.getName());
			res.setSurname(companyForm.getSurname());
			res.setOptionalPhoto(companyForm.getOptionalPhoto());
			res.setPhoneNumber(companyForm.getPhoneNumber());
			res.setEmail(companyForm.getEmail());
			res.setVat(companyForm.getVat());
			res.setOptionalAddress(companyForm.getOptionalAddress());
			res.setCommercialName(companyForm.getCommercialName());

			res.getCreditCard().setHolderName(companyForm.getCreditCard().getHolderName());
			res.getCreditCard().setBrandName(companyForm.getCreditCard().getBrandName());
			res.getCreditCard().setNumber(companyForm.getCreditCard().getNumber());
			res.getCreditCard().setExpMonth(companyForm.getCreditCard().getExpMonth());
			res.getCreditCard().setExpYear(companyForm.getCreditCard().getExpYear());
			res.getCreditCard().setCvv(companyForm.getCreditCard().getCvv());

		}

		this.validator.validate(res, binding);
		return res;
	}
	public CompanyForm deconstruct(final Company company) {
		System.out.println("LLega");
		final CompanyForm res = new CompanyForm();

		res.setId(company.getId());
		res.setName(company.getName());
		res.setSurname(company.getSurname());
		res.setVat(company.getVat());
		res.setOptionalPhoto(company.getOptionalPhoto());
		res.setPhoneNumber(company.getPhoneNumber());
		res.setEmail(company.getEmail());
		res.setOptionalAddress(company.getOptionalAddress());
		res.setVat(company.getVat());
		res.setOptionalAddress(company.getOptionalAddress());
		res.setCommercialName(company.getCommercialName());

		res.setCreditCard(company.getCreditCard());

		return res;
	}
	public Collection<Position> findPosistionsOfCompany() {
		return this.companyRepository.findPosistionsOfCompany();
	}

	//	The average, the minimum, the maximum, and the standard deviation of the
	//	number of positions per company.

	public Double averagePositionsPerCompany() {
		this.adminService.checkPrincipal();
		return this.companyRepository.averagePositionsPerCompany();
	}

	public Integer minPositionsPerCompany() {
		this.adminService.checkPrincipal();
		return this.companyRepository.minPositionsPerCompany();
	}

	public Integer maxPositionsPerCompany() {
		this.adminService.checkPrincipal();
		return this.companyRepository.maxPositionsPerCompany();
	}

	public Double stdDevPositionsPerCompany() {
		this.adminService.checkPrincipal();
		return this.companyRepository.stddevPositionsPerCompany();
	}

	//The companies that have offered more positions.

	public Collection<String> findCompaniesMorePositions() {
		this.adminService.checkPrincipal();
		final Collection<String> res = new ArrayList<String>();
		final Collection<Company> aux = this.companyRepository.findCompaniesMorePositions();
		for (final Company c : aux)
			if (c.getPositions().size() > 0)
				res.add(c.getCommercialName());
		return res;
	}
	public Collection<Company> searchCompanyByCommercialName(final String keyword) {
		return this.companyRepository.searchCompanyByCommercialName(keyword);
	}
	public void flush() {
		this.companyRepository.flush();
	}
}
