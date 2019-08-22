
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
import domain.Finder;

@Service
@Transactional
public class AdministratorService {

	//Managed repository
	@Autowired
	private AdministratorRepository	administratorRepository;

	//Supporting services

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private FinderService			finderService;


	// SIMPLE CRUD METHODS

	public Administrator create() {
		Assert.notNull(this.findByPrincipal());
		Administrator a;
		UserAccount userAccount;
		Authority auth;
		Finder finder;

		//Authority
		a = new Administrator();
		userAccount = new UserAccount();
		auth = new Authority();
		finder = this.finderService.create();
		final Finder res = this.finderService.save(finder);

		auth.setAuthority("ADMIN");
		userAccount.addAuthority(auth);
		a.setUserAccount(userAccount);
		a.setFinder(res);

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

		Assert.isTrue(!a.getUserAccount().getUsername().isEmpty());
		Assert.isTrue(!a.getUserAccount().getPassword().isEmpty());
		Assert.isTrue(this.checkAdminEmail(a.getEmail()), "email error");

		//Hasheamos la contraseña
		if (a.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(a.getUserAccount().getPassword(), null);
			a.getUserAccount().setPassword(hash);
		}

		//Comprobamos si el núemro de teléfono está vacio sino comprobamos que empieze por +
		if (a.getPhoneNumber() != null)
			if (ConfigurationService.isNumeric(a.getPhoneNumber()) == true && !(a.getPhoneNumber().isEmpty()))
				if (a.getPhoneNumber().length() > 3 && !(a.getPhoneNumber().startsWith("+")))
					a.setPhoneNumber("+" + this.configurationService.find().getCountryCode() + " " + a.getPhoneNumber());
		Administrator res;
		res = this.administratorRepository.save(a);
		return res;
	}
	public void delete(final Administrator a) {
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);
		Assert.notNull(this.administratorRepository.findOne(a.getId()));

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

	public boolean checkPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);

		return authorities.contains(auth);
	}
	public void flush() {
		this.administratorRepository.flush();
	}

	public boolean checkAdminEmail(String email) {
		String pattern[] = null;
		String alias;
		String identifier;
		Boolean res = true;

		if (email.contains("<") && email.contains(">")) {
			//Comprobamos si es de la forma "alias <identifier@>"
			email = email.replaceAll("\\s+", "");
			pattern = email.split("\\<");
			alias = pattern[0];
			//Quitamos los espacios del alias
			alias = alias.replaceAll("//s+", "");

			//Comprobamos si el alias es alfa-numï¿½rico, caracter a caracter
			if (!alias.matches("[A-Za-z0-9]+"))
				res = false;

			//Comprobamos el identifier@domain de dentro de los < >
			identifier = pattern[1].substring(0, pattern[1].length() - 1);
			final String[] aux = identifier.split("\\@");
			if (aux.length > 1)
				res = false;
			if (!identifier.contains("@"))
				res = false;

			identifier = identifier.replaceAll("@", "");
			//Checking that identifier is alpha-numeric
			if (!identifier.matches("[A-Za-z0-9]+"))
				res = false;

		} else if (!email.contains("@"))
			res = false;
		else if (email.contains("."))
			res = false;
		else if (email.contains("@")) {

			//Comprobamos la forma "identifier@"
			pattern = email.split("\\@");
			identifier = pattern[0];

			if (!identifier.matches("[A-Za-z0-9]+"))
				res = false;

			if (pattern.length > 1)
				res = false;

		}

		return res;
	}

}
