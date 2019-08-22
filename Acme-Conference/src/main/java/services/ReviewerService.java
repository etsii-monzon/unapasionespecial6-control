
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ReviewerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Finder;
import domain.Message;
import domain.Report;
import domain.Reviewer;

;

@Service
@Transactional
public class ReviewerService {

	//Managed repository
	@Autowired
	private ReviewerRepository		reviewerRepository;

	//	//Supporting services

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private FinderService			finderService;


	//	// SIMPLE CRUD METHODS

	public Reviewer create() {

		Reviewer reviewer;
		UserAccount userAccount;
		Authority auth;
		Finder finder;

		//Authority
		reviewer = new Reviewer();
		userAccount = new UserAccount();
		auth = new Authority();
		finder = this.finderService.create();
		final Finder res = this.finderService.save(finder);

		auth.setAuthority("REVIEWER");
		userAccount.addAuthority(auth);
		reviewer.setUserAccount(userAccount);
		reviewer.setFinder(res);

		//Relationships
		final Collection<Report> reports = new ArrayList<Report>();
		final Collection<Message> messages = new ArrayList<Message>();

		reviewer.setReports(reports);
		reviewer.setMessages(messages);

		return reviewer;
	}
	public Collection<Reviewer> findAll() {
		Collection<Reviewer> reviewers;
		reviewers = this.reviewerRepository.findAll();
		Assert.notNull(reviewers);
		return reviewers;

	}
	public Reviewer findOne(final int reviewerId) {
		Assert.notNull(reviewerId);
		Reviewer d;
		d = this.reviewerRepository.findOne(reviewerId);
		return d;
	}
	public Reviewer save(final Reviewer e) {
		Assert.notNull(e);
		//		Assert.isTrue(this.actorService.checkUserEmail(e.getEmail()));

		Assert.isTrue(!e.getUserAccount().getUsername().isEmpty());
		Assert.isTrue(!e.getUserAccount().getPassword().isEmpty());
		Assert.isTrue(this.actorService.checkUserEmail(e.getEmail()), "email error");

		if (e.getId() == 0) {
			Assert.isTrue(this.actorService.usernameExits(e.getUserAccount().getUsername()), "username error");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(e.getUserAccount().getPassword(), null);
			e.getUserAccount().setPassword(hash);
		}
		if (e.getPhoneNumber() != null)
			if (ConfigurationService.isNumeric(e.getPhoneNumber()) == true && !(e.getPhoneNumber().isEmpty()))
				if (e.getPhoneNumber().length() > 3 && !(e.getPhoneNumber().startsWith("+")))
					e.setPhoneNumber("+" + this.configurationService.find().getCountryCode() + " " + e.getPhoneNumber());
		Reviewer res;
		res = this.reviewerRepository.save(e);
		return res;
	}
	//	public void delete(final Employee e) {
	//		Assert.notNull(e);
	//		Assert.isTrue(e.getId() != 0);
	//		Assert.notNull(this.reviewerRepository.findOne(e.getId()));
	//
	//		//Eliminamos la creditCard asociada
	//		this.creditCardService.delete(e.getCreditCard());
	//
	//		//Eliminamos MessageBoxes
	//		for (final MessageBox mB : e.getMessageBoxes())
	//			this.messageBoxService.deleteGDPR(mB);
	//
	//		//Eliminamos Incidences
	//		final Collection<Incidence> toDelete = new ArrayList<>();
	//		for (final Incidence i : e.getIncidences())
	//			toDelete.add(i);
	//		for (final Incidence d : toDelete)
	//			this.incidenceService.delete(d);
	//
	//		//Eliminamos Enrolments
	//		final Collection<Enrolment> toDeleteEnrol = new ArrayList<>();
	//
	//		for (final Enrolment en : e.getEnrolments())
	//			toDeleteEnrol.add(en);
	//		for (final Enrolment en2 : toDeleteEnrol)
	//			this.enrolmentService.delete(en2);
	//
	//		this.reviewerRepository.delete(e);
	//	}
	public Reviewer findByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);
		final Reviewer res = this.reviewerRepository.findByUserAccountId(userAccountId);
		return res;
	}

	public Reviewer findByPrincipal() {
		final UserAccount u = LoginService.getPrincipal();
		final Reviewer res = this.reviewerRepository.findByUserAccountId(u.getId());
		return res;
	}

	public boolean checkPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.REVIEWER);

		return authorities.contains(auth);
	}

	public void flush() {
		this.reviewerRepository.flush();
	}
}
