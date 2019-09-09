
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.CreditCard;

@Service
@Transactional
public class AdministratorService {

	//Managed repository
	@Autowired
	private AdministratorRepository	administratorRepository;

	//Supporting services
	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;


	// SIMPLE CRUD METHODS

	public Administrator create() {
		Assert.notNull(this.findByPrincipal());
		Administrator a;
		UserAccount userAccount;
		Authority auth;
		CreditCard cCard;

		this.checkPrincipal();

		//Authority
		a = new Administrator();
		userAccount = new UserAccount();
		if (this.configurationService.find().isReBrand() == true)
			userAccount.setNotify(true);
		auth = new Authority();
		cCard = new CreditCard();

		auth.setAuthority("ADMIN");
		userAccount.addAuthority(auth);
		a.setUserAccount(userAccount);
		a.setCreditCard(cCard);

		//Relationships

		return a;
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> administrators;
		administrators = this.administratorRepository.findAll();
		Assert.notNull(administrators);
		return administrators;

	}
	public Administrator findOne(final int administratorId) {
		Assert.notNull(administratorId);
		Administrator a;
		a = this.administratorRepository.findOne(administratorId);
		return a;
	}
	public Administrator save(final Administrator a) {
		Assert.notNull(a);
		//		Assert.isTrue(this.checkAdminEmail(a.getEmail()));

		Assert.isTrue(!a.getUserAccount().getUsername().isEmpty());
		Assert.isTrue(!a.getUserAccount().getPassword().isEmpty());

		if (a.getId() == 0 || a.getId() != 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(a.getUserAccount().getPassword(), null);
			a.getUserAccount().setPassword(hash);
		}

		if (a.getPhoneNumber() != null)
			if (!(a.getPhoneNumber().startsWith("+")))
				a.setPhoneNumber("+" + this.configurationService.find().getCountryCode() + " " + a.getPhoneNumber());
		Administrator res;
		res = this.administratorRepository.save(a);
		return res;
	}
	public void delete(final Administrator a) {
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);
		Assert.notNull(this.administratorRepository.findOne(a.getId()));

		//Eliminamos la creditCard asociada
		this.creditCardService.delete(a.getCreditCard());

		this.administratorRepository.delete(a);
	}

	public Administrator findByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);
		final Administrator res = this.administratorRepository.findByUserAccountId(userAccountId);
		return res;
	}

	public Administrator findByPrincipal() {
		final UserAccount u = LoginService.getPrincipal();
		final Administrator res = this.administratorRepository.findByUserAccountId(u.getId());
		return res;
	}

	public void checkPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);

		Assert.isTrue(authorities.contains(auth));
	}
	public void flush() {
		this.administratorRepository.flush();
	}

	public boolean checkAdminEmail(final String email) {
		//La forma 'identifier@domain' ya es validada con la anotaci�n @Email
		String pattern[] = null;
		String alias;
		String identifier;
		Boolean res = true;

		if (email.contains("<") && email.contains(">")) {
			//Comprobamos si es de la forma "alias <identifier>" 
			pattern = email.split("\\<");
			alias = pattern[0];
			//Quitamos los espacios del alias
			alias = alias.replaceAll("//s+", "");

			//Comprobamos si el alias es alfa-num�rico, caracter a caracter
			if (!alias.matches("[A-Za-z0-9]+"))
				res = false;

			//Comprobamos el identifier@domain de dentro de los < >
			identifier = pattern[1].substring(0, pattern[1].length() - 1);

			//Checking that identifier is alpha-numeric
			if (!identifier.matches("[A-Za-z0-9]+"))
				res = false;

		} else if (email.contains("@")) {
			//Comprobamos la forma "identifier@"
			pattern = email.split("\\@");
			final int k = email.indexOf("@");
			identifier = email.substring(0, k);

			if (!identifier.matches("[A-Za-z0-9]+"))
				res = false;

			//			if (!pattern[1].isEmpty())
			//				res = false;

		}
		//Los admins tambi�n pueden tener la misma forma de email que los dem�s usuarios
		return res || this.actorService.checkUserEmail(email);
	}

}
