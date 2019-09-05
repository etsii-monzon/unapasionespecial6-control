
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

import repositories.AuditorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Audit;
import domain.Auditor;
import domain.CreditCard;
import forms.AuditorForm;

@Service
@Transactional
public class AuditorService {

	//Managed repository
	@Autowired
	private AuditorRepository		auditorRepository;

	//Supporting services
	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AuditService			auditService;


	// SIMPLE CRUD METHODS

	public Auditor create() {
		Assert.notNull(this.administratorService.findByPrincipal());
		Auditor a;
		UserAccount userAccount;
		Authority auth;
		CreditCard cCard;
		Collection<Audit> audits;

		//Authority
		a = new Auditor();
		userAccount = new UserAccount();
		if (this.configurationService.find().isReBrand() == true)
			userAccount.setNotify(true);
		auth = new Authority();
		cCard = new CreditCard();
		audits = new ArrayList<Audit>();

		auth.setAuthority("AUDITOR");
		userAccount.addAuthority(auth);
		userAccount.setNotify(true);
		a.setUserAccount(userAccount);
		a.setCreditCard(cCard);
		a.setAudits(audits);

		//Relationships

		return a;
	}

	public Collection<Auditor> findAll() {
		Collection<Auditor> auditors;
		auditors = this.auditorRepository.findAll();
		Assert.notNull(auditors);
		return auditors;

	}
	public Auditor findOne(final int audiorId) {
		Assert.notNull(audiorId);
		Auditor a;
		a = this.auditorRepository.findOne(audiorId);
		return a;
	}
	public Auditor save(final Auditor a) {
		Assert.notNull(a);
		Assert.isTrue(this.actorService.checkUserEmail(a.getEmail()));

		Assert.isTrue(!a.getUserAccount().getUsername().isEmpty());
		Assert.isTrue(!a.getUserAccount().getPassword().isEmpty());

		if (a.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(a.getUserAccount().getPassword(), null);
			a.getUserAccount().setPassword(hash);
		}
		if (a.getPhoneNumber() != null)
			if (!(a.getPhoneNumber().startsWith("+")))
				a.setPhoneNumber("+" + this.configurationService.find().getCountryCode() + " " + a.getPhoneNumber());
		Auditor res;
		res = this.auditorRepository.save(a);
		return res;
	}
	public void delete(final Auditor a) {
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);
		Assert.notNull(this.auditorRepository.findOne(a.getId()));

		final Collection<Audit> toDeleteAudits = new ArrayList<>();

		//Eliminamos la creditCard asociada
		this.creditCardService.delete(a.getCreditCard());

		//Eliminamos audits
		for (final Audit au : a.getAudits())
			toDeleteAudits.add(au);
		for (final Audit au2 : toDeleteAudits)
			this.auditService.delete(au2);

		this.auditorRepository.delete(a);
	}
	public Auditor findByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);
		final Auditor res = this.auditorRepository.findByUserAccountId(userAccountId);
		return res;
	}

	public Auditor findByPrincipal() {
		final UserAccount u = LoginService.getPrincipal();
		final Auditor res = this.auditorRepository.findByUserAccountId(u.getId());
		return res;
	}

	public void checkPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.AUDITOR);

		Assert.isTrue(authorities.contains(auth));
	}
	public void flush() {
		this.auditorRepository.flush();
	}


	@Autowired
	private Validator	validator;


	public Auditor reconstruct(final AuditorForm auditorForm, final BindingResult binding) {
		Auditor res;
		if (auditorForm.getId() == 0) {
			res = this.create();
			res.setName(auditorForm.getName());
			res.setSurname(auditorForm.getSurname());
			res.setVat(auditorForm.getVat());
			res.setOptionalPhoto(auditorForm.getOptionalPhoto());
			res.setPhoneNumber(auditorForm.getPhoneNumber());
			res.setEmail(auditorForm.getEmail());
			res.setOptionalAddress(auditorForm.getOptionalAddress());
			res.getCreditCard().setHolderName(auditorForm.getCreditCard().getHolderName());
			res.getCreditCard().setBrandName(auditorForm.getCreditCard().getBrandName());
			res.getCreditCard().setNumber(auditorForm.getCreditCard().getNumber());
			res.getCreditCard().setExpMonth(auditorForm.getCreditCard().getExpMonth());
			res.getCreditCard().setExpYear(auditorForm.getCreditCard().getExpYear());
			res.getCreditCard().setCvv(auditorForm.getCreditCard().getCvv());
			res.getUserAccount().setUsername(auditorForm.getUserAccount().getUsername());
			res.getUserAccount().setPassword(auditorForm.getUserAccount().getPassword());

		} else {
			System.out.println("LLega");

			res = this.auditorRepository.findOne(auditorForm.getId());
			res.setName(auditorForm.getName());
			res.setSurname(auditorForm.getSurname());
			res.setOptionalPhoto(auditorForm.getOptionalPhoto());
			res.setPhoneNumber(auditorForm.getPhoneNumber());
			res.setEmail(auditorForm.getEmail());
			res.setVat(auditorForm.getVat());
			res.setOptionalAddress(auditorForm.getOptionalAddress());

			res.getCreditCard().setHolderName(auditorForm.getCreditCard().getHolderName());
			res.getCreditCard().setBrandName(auditorForm.getCreditCard().getBrandName());
			res.getCreditCard().setNumber(auditorForm.getCreditCard().getNumber());
			res.getCreditCard().setExpMonth(auditorForm.getCreditCard().getExpMonth());
			res.getCreditCard().setExpYear(auditorForm.getCreditCard().getExpYear());
			res.getCreditCard().setCvv(auditorForm.getCreditCard().getCvv());
			//			res.getUserAccount().setUsername(memberForm.getUserAccount().getUsername());
			//			res.getUserAccount().setPassword(memberForm.getUserAccount().getPassword());
		}

		this.validator.validate(res, binding);
		return res;
	}
	public AuditorForm deconstruct(final Auditor auditor) {
		System.out.println("LLega");
		final AuditorForm res = new AuditorForm();

		res.setId(auditor.getId());
		res.setName(auditor.getName());
		res.setSurname(auditor.getSurname());
		res.setVat(auditor.getVat());
		res.setOptionalPhoto(auditor.getOptionalPhoto());
		res.setPhoneNumber(auditor.getPhoneNumber());
		res.setEmail(auditor.getEmail());
		res.setOptionalAddress(auditor.getOptionalAddress());
		res.setVat(auditor.getVat());
		res.setOptionalAddress(auditor.getOptionalAddress());

		res.setCreditCard(auditor.getCreditCard());

		//		res.getUserAccount().setUsername(member.getUserAccount().getUsername());
		//		System.out.println("Pasa8");
		//
		//		res.getUserAccount().setPassword(member.getUserAccount().getPassword());
		//		System.out.println("Pasa9");

		//		this.validator.validate(res, binding);
		return res;
	}
}
