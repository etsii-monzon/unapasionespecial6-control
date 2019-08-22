
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	//Managed repository
	@Autowired
	private ActorRepository			actorRepository;

	//Supporting services

	@Autowired
	private UserAccountRepository	userAccountRepository;


	// SIMPLE CRUD METHODS

	public Collection<Actor> findAll() {
		final Collection<Actor> actors;
		actors = this.actorRepository.findAll();
		Assert.notNull(actors);
		return actors;

	}
	public Actor findOne(final int actorId) {
		Assert.notNull(actorId);
		Actor a;
		a = this.actorRepository.findOne(actorId);
		return a;
	}
	public Actor save(final Actor a) {
		Assert.notNull(a);
		Actor res;
		res = this.actorRepository.save(a);
		return res;
	}

	public void delete(final Actor a) {
		Assert.notNull(a);
		Assert.notNull(a.getId());

		this.actorRepository.delete(a);
	}
	// NUEVOS METODOS

	public Actor findActorByUserAccountId(final int UserAccountId) {
		return this.actorRepository.findActorByUserAccountId(UserAccountId);
	}

	public Actor findByPrincipal() {
		Actor res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findActorByUserAccountId(userAccount.getId());
		Assert.notNull(res);
		return res;
	}

	public boolean checkUserEmail(final String email) {
		//Comprobamos primero la forma alias <identifier@domain>
		String pattern[] = null;
		String aliases[];
		String identifiers[];
		String domains[];
		int i = 0;
		Boolean res = true;

		if (!email.contains("@"))
			res = false;
		else if (!email.contains("."))
			res = false;
		else if (email.contains("<") && email.contains(">")) {
			pattern = email.split("\\<");
			final String alias = pattern[0];
			aliases = alias.split("\\s+");

			final Collection<String> aux = new ArrayList<String>();
			for (final String s : aliases)
				aux.add(s);

			for (i = 0; i < aux.size(); i++)
				if (!aliases[i].matches("[A-Za-z0-9]+"))
					res = false;
			final String mail = pattern[1].substring(0, pattern[1].length() - 1);
			identifiers = mail.split("\\@");
			final String identifier = identifiers[0];
			if (identifiers.length <= 1)
				res = false;
			else {
				final String domain = identifiers[1];
				//Checking that identifier is alpha-numeric
				if (!identifier.matches("[A-Za-z0-9]+"))
					res = false;
				else {
					domains = domain.split("\\.");
					//Creating a list to know a size to end the for.
					final Collection<String> aux2 = new ArrayList<String>();
					for (final String s : domains)
						aux2.add(s);
					if (domains.length != 2)
						res = false;
					for (i = 0; i < aux2.size(); i++)
						if (!domains[i].matches("[A-Za-z0-9]+"))
							res = false;
				}
			}

		} else {
			//Comprobamos la forma "identifier@domain" 
			identifiers = email.split("\\@");
			final String identifier = identifiers[0];
			if (!identifier.matches("[A-Za-z0-9]+"))
				res = false;
			else if (identifiers.length <= 1)
				res = false;
			else {
				final String domain = identifiers[1];
				domains = domain.split("\\.");
				final Collection<String> aux3 = new ArrayList<String>();
				for (final String s : domains)
					aux3.add(s);
				if (domains.length != 2)
					res = false;
				for (i = 0; i < aux3.size(); i++)
					if (!domains[i].matches("[A-Za-z0-9]+"))
						res = false;
			}
		}
		return res;
	}

	//	public boolean checkPrincipal() {
	//		boolean res = false;
	//
	//		final UserAccount userAccount = LoginService.getPrincipal();
	//		Assert.notNull(userAccount);
	//
	//		final Collection<Authority> authorities = userAccount.getAuthorities();
	//		Assert.notNull(authorities);
	//
	//		final Authority auth = new Authority();
	//		auth.setAuthority(Authority.STUDENT);
	//		final Authority auth2 = new Authority();
	//		auth2.setAuthority(Authority.DIRECTOR);
	//		final Authority auth3 = new Authority();
	//		auth3.setAuthority(Authority.ADMIN);
	//		final Authority auth4 = new Authority();
	//		auth3.setAuthority(Authority.EMPLOYEE);
	//
	//		if (authorities.contains(auth) || authorities.contains(auth2) || authorities.contains(auth3)||authorities.contains(auth4))
	//			res = true;
	//
	//		return res;
	//	}

	public boolean usernameExits(final String username) {
		if (this.userAccountRepository.findByUsername(username) == null)

			return true;
		else
			return false;

	}
}
