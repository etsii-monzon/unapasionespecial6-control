
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

import repositories.HackerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.CreditCard;
import domain.Hacker;
import forms.HackerForm;

@Service
@Transactional
public class HackerService {

	//Managed repository
	@Autowired
	private HackerRepository		hackerRepository;

	//Supporting services
	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;


	// SIMPLE CRUD METHODS

	public Hacker create() {

		Hacker h;
		UserAccount userAccount;
		Authority auth;
		CreditCard cCard;

		//Authority
		h = new Hacker();
		userAccount = new UserAccount();
		auth = new Authority();
		cCard = new CreditCard();

		auth.setAuthority("ROOKIE");
		userAccount.addAuthority(auth);
		if (this.configurationService.find().isReBrand() == true)
			userAccount.setNotify(true);
		h.setUserAccount(userAccount);
		h.setCreditCard(cCard);

		//Relationships

		final Collection<Application> applications = new ArrayList<Application>();

		h.setApplications(applications);

		return h;
	}

	public Collection<Hacker> findAll() {
		Collection<Hacker> hackers;
		hackers = this.hackerRepository.findAll();
		Assert.notNull(hackers);
		return hackers;

	}
	public Hacker findOne(final int hackerId) {
		Assert.notNull(hackerId);
		Hacker c;
		c = this.hackerRepository.findOne(hackerId);
		return c;
	}
	public Hacker save(final Hacker h) {
		Assert.notNull(h);

		Assert.isTrue(this.actorService.checkUserEmail(h.getEmail()));

		Assert.isTrue(!h.getUserAccount().getUsername().isEmpty());
		Assert.isTrue(!h.getUserAccount().getPassword().isEmpty());

		if (h.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(h.getUserAccount().getPassword(), null);
			h.getUserAccount().setPassword(hash);
		}
		if (h.getPhoneNumber() != null)
			if (!(h.getPhoneNumber().startsWith("+")))
				h.setPhoneNumber("+" + this.configurationService.find().getCountryCode() + " " + h.getPhoneNumber());
		Hacker res;
		res = this.hackerRepository.save(h);
		return res;
	}

	public void delete(final Hacker h) {
		Assert.notNull(h);
		Assert.isTrue(h.getId() != 0);
		Assert.notNull(this.hackerRepository.findOne(h.getId()));
		final Collection<Application> toDelete = new ArrayList<>();

		for (final Application a : h.getApplications())
			toDelete.add(a);
		for (final Application ap : toDelete)
			this.applicationService.delete(ap);

		//Eliminamos la creditCard asociada
		this.creditCardService.delete(h.getCreditCard());

		this.hackerRepository.delete(h);
	}

	public Hacker findByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);
		final Hacker res = this.hackerRepository.findByUserAccountId(userAccountId);
		return res;
	}

	public Hacker findByPrincipal() {
		final UserAccount u = LoginService.getPrincipal();
		final Hacker res = this.hackerRepository.findByUserAccountId(u.getId());
		return res;
	}

	public Boolean checkPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ROOKIE);

		return authorities.contains(auth);
	}


	@Autowired
	private Validator	validator;


	public Hacker reconstruct(final HackerForm hackerForm, final BindingResult binding) {
		Hacker res;
		if (hackerForm.getId() == 0) {
			res = this.create();
			res.setName(hackerForm.getName());
			res.setSurname(hackerForm.getSurname());
			res.setVat(hackerForm.getVat());
			res.setOptionalPhoto(hackerForm.getOptionalPhoto());
			res.setPhoneNumber(hackerForm.getPhoneNumber());
			res.setEmail(hackerForm.getEmail());
			res.setOptionalAddress(hackerForm.getOptionalAddress());
			res.getCreditCard().setHolderName(hackerForm.getCreditCard().getHolderName());
			res.getCreditCard().setBrandName(hackerForm.getCreditCard().getBrandName());
			res.getCreditCard().setNumber(hackerForm.getCreditCard().getNumber());
			res.getCreditCard().setExpMonth(hackerForm.getCreditCard().getExpMonth());
			res.getCreditCard().setExpYear(hackerForm.getCreditCard().getExpYear());
			res.getCreditCard().setCvv(hackerForm.getCreditCard().getCvv());
			res.getUserAccount().setUsername(hackerForm.getUserAccount().getUsername());
			res.getUserAccount().setPassword(hackerForm.getUserAccount().getPassword());

		} else {
			System.out.println("LLega");

			res = this.hackerRepository.findOne(hackerForm.getId());
			res.setName(hackerForm.getName());
			res.setSurname(hackerForm.getSurname());
			res.setOptionalPhoto(hackerForm.getOptionalPhoto());
			res.setPhoneNumber(hackerForm.getPhoneNumber());
			res.setEmail(hackerForm.getEmail());
			res.setVat(hackerForm.getVat());
			res.setOptionalAddress(hackerForm.getOptionalAddress());

			res.getCreditCard().setHolderName(hackerForm.getCreditCard().getHolderName());
			res.getCreditCard().setBrandName(hackerForm.getCreditCard().getBrandName());
			res.getCreditCard().setNumber(hackerForm.getCreditCard().getNumber());
			res.getCreditCard().setExpMonth(hackerForm.getCreditCard().getExpMonth());
			res.getCreditCard().setExpYear(hackerForm.getCreditCard().getExpYear());
			res.getCreditCard().setCvv(hackerForm.getCreditCard().getCvv());
			//			res.getUserAccount().setUsername(memberForm.getUserAccount().getUsername());
			//			res.getUserAccount().setPassword(memberForm.getUserAccount().getPassword());
		}

		this.validator.validate(res, binding);
		return res;
	}
	public HackerForm deconstruct(final Hacker hacker) {
		System.out.println("LLega");
		final HackerForm res = new HackerForm();

		res.setId(hacker.getId());
		res.setName(hacker.getName());
		res.setSurname(hacker.getSurname());
		res.setVat(hacker.getVat());
		res.setOptionalPhoto(hacker.getOptionalPhoto());
		res.setPhoneNumber(hacker.getPhoneNumber());
		res.setEmail(hacker.getEmail());
		res.setOptionalAddress(hacker.getOptionalAddress());
		res.setVat(hacker.getVat());
		res.setOptionalAddress(hacker.getOptionalAddress());

		res.setCreditCard(hacker.getCreditCard());

		//		res.getUserAccount().setUsername(member.getUserAccount().getUsername());
		//		System.out.println("Pasa8");
		//
		//		res.getUserAccount().setPassword(member.getUserAccount().getPassword());
		//		System.out.println("Pasa9");

		//		this.validator.validate(res, binding);
		return res;
	}

	//	The average, the minimum, the maximum, and the standard deviation of the
	//	number of applications per hacker.

	public Double averageApplicationsPerHacker() {
		this.adminService.checkPrincipal();
		return this.hackerRepository.averageApplicationsPerHacker();
	}

	public Integer minApplicationsPerHacker() {
		this.adminService.checkPrincipal();
		return this.hackerRepository.minApplicationsPerHacker();
	}

	public Integer maxApplicationsPerHacker() {
		this.adminService.checkPrincipal();
		return this.hackerRepository.maxApplicationsPerHacker();
	}

	public Double stddevApplicationsPerHacker() {
		this.adminService.checkPrincipal();
		return this.hackerRepository.stddevApplicationsPerHacker();
	}

	//The hackers who have made more applications.

	public Collection<String> findHackersMoreApplications() {
		this.adminService.checkPrincipal();
		final Collection<String> res = new ArrayList<String>();
		final Collection<Hacker> aux = this.hackerRepository.findHackersMoreApplications();

		for (final Hacker h : aux)
			if (h.getApplications().size() > 0)
				res.add(h.getName() + " " + h.getSurname());

		return res;
	}

	public void flush() {
		this.hackerRepository.flush();
	}

}
