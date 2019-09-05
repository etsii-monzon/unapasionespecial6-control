
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	//Managed repository
	@Autowired
	private ActorRepository	actorRepository;


	//Supporting services

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
		//La forma 'identifier@domain' ya es validada con la anotación @Email
		String pattern[] = null;
		String alias;
		String identifiers[];
		String identifier;
		String domain = "";
		boolean res = true;

		if (email.contains("<") && email.contains(">") && email.contains("@")) {
			//Comprobamos si es de la forma "alias <identifier@domain>" 
			pattern = email.split("//<");
			alias = pattern[0];
			alias = alias.replaceAll("//s+", "");
			alias = alias.replaceAll(" ", "");

			//Comprobamos si el alias es alfa-numérico, caracter a caracter
			if (!alias.matches("[A-Za-z0-9]+"))
				res = false;

			//Comprobamos el identifier@domain de dentro de los < >
			identifiers = pattern[1].substring(0, pattern[1].length() - 1).split("//@");
			identifier = identifiers[0];
			domain = identifiers[1];
			//Checking that identifier is alpha-numeric
			if (!identifier.matches("[A-Za-z0-9]+"))
				res = false;
			else {
				domain = domain.replaceAll("\\.", "");

				if (!domain.matches("[A-Za-z0-9]+"))
					res = false;
			}

		} else if (email.contains("@")) {
			//Comprobamos la forma "identifier@domain"
			identifiers = email.substring(0, email.length() - 1).split("\\@");
			identifier = identifiers[0];
			if (identifiers.length == 2)
				domain = identifiers[1];
			//Checking that identifier is alpha-numeric
			if (!identifier.matches("[A-Za-z0-9]+"))
				res = false;
			else {
				domain = domain.replaceAll("\\.", "");

				if (!domain.matches("[A-Za-z0-9]+"))
					res = false;
			}
		}
		return res;
	}

	public boolean checkPrincipal() {
		boolean res = false;

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.COMPANY);
		final Authority auth2 = new Authority();
		auth2.setAuthority(Authority.ROOKIE);
		final Authority auth3 = new Authority();
		auth3.setAuthority(Authority.ADMIN);

		if (authorities.contains(auth) || authorities.contains(auth2) || authorities.contains(auth3))
			res = true;

		return res;
	}

}
