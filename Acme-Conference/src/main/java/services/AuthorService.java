
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AuthorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Author;
import domain.Finder;
import domain.Message;
import domain.Registration;
import domain.Submission;

@Service
@Transactional
public class AuthorService {

	//Managed repository
	@Autowired
	private AuthorRepository		authorRepository;

	//Supporting services

	@Autowired
	private ActorService			actorService;
	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private FinderService			finderService;


	// SIMPLE CRUD METHODS

	public Author create() {
		Author author;
		UserAccount userAccount;
		Authority auth;
		Finder finder;

		//Authority
		author = new Author();
		userAccount = new UserAccount();
		auth = new Authority();
		finder = this.finderService.create();
		final Finder res = this.finderService.save(finder);

		auth.setAuthority("AUTHOR");
		userAccount.addAuthority(auth);
		author.setUserAccount(userAccount);
		author.setFinder(res);

		//Relationships

		final Collection<Message> messages = new ArrayList<>();
		final Collection<Registration> registrations = new ArrayList<>();
		final Collection<Submission> submissions = new ArrayList<>();

		author.setMessages(messages);
		author.setRegistrations(registrations);
		author.setSubmissions(submissions);

		return author;
	}

	public Collection<Author> findAll() {
		Collection<Author> authors;
		authors = this.authorRepository.findAll();
		Assert.notNull(authors);
		return authors;

	}
	public Author findOne(final int authorId) {
		Assert.notNull(authorId);
		Author d;
		d = this.authorRepository.findOne(authorId);
		return d;
	}
	public Author save(final Author d) {

		Assert.notNull(d);
		Assert.isTrue(this.actorService.checkUserEmail(d.getEmail()), "email error");

		if (d.getId() == 0) {
			Assert.isTrue(this.actorService.usernameExits(d.getUserAccount().getUsername()), "username error");
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(d.getUserAccount().getPassword(), null);
			d.getUserAccount().setPassword(hash);
		}
		if (d.getPhoneNumber() != null)
			if (ConfigurationService.isNumeric(d.getPhoneNumber()) == true && !(d.getPhoneNumber().isEmpty()))
				if (d.getPhoneNumber().length() > 3 && !(d.getPhoneNumber().startsWith("+")))
					d.setPhoneNumber("+" + this.configurationService.find().getCountryCode() + " " + d.getPhoneNumber());
		Author res;
		res = this.authorRepository.save(d);
		return res;
	}

	public Author findByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);
		final Author res = this.authorRepository.findByUserAccountId(userAccountId);
		return res;
	}

	public Author findByPrincipal() {
		final UserAccount u = LoginService.getPrincipal();
		final Author res = this.authorRepository.findByUserAccountId(u.getId());
		return res;
	}

	public boolean checkPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.AUTHOR);

		return authorities.contains(auth);
	}

	public Author findAuthorBySubmissionId(final int id) {
		return this.authorRepository.findAuthorBySubmissionId(id);
	}

	public void flush() {
		this.authorRepository.flush();
	}

}
