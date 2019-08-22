
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RegistrationRepository;
import domain.Author;
import domain.Registration;

@Service
@Transactional
public class RegistrationService {

	//Managed repository
	@Autowired
	private RegistrationRepository	registrationRepository;

	//Supporting services
	@Autowired
	private AuthorService			authorService;


	// SIMPLE CRUD METHODS

	public Registration create() {
		Assert.isTrue(this.authorService.checkPrincipal());

		Registration pro;
		pro = new Registration();

		return pro;
	}

	public Collection<Registration> findAll() {

		Collection<Registration> pros;
		pros = this.registrationRepository.findAll();

		return pros;

	}
	public Registration findOne(final int registrationId) {

		Assert.notNull(registrationId);
		Registration pro;
		pro = this.registrationRepository.findOne(registrationId);

		return pro;
	}
	public Registration save(final Registration a) {

		Assert.notNull(a);
		Registration res;
		final Author b = this.authorService.findByPrincipal();
		final Collection<Registration> f = b.getRegistrations();
		res = this.registrationRepository.save(a);
		if (!f.contains(res))
			f.add(res);

		return res;
	}

	public void delete(final Registration p) {
		Assert.notNull(p);
		Assert.isTrue(p.getId() != 0);

		final Author brotherhood = this.authorService.findByPrincipal();

		brotherhood.getRegistrations().remove(p);

		this.registrationRepository.delete(p);
	}

	public Double avgRegistrationsPerConference() {
		return this.registrationRepository.avgRegistrationsPerConference();
	}

	public Integer minRegistrationsPerConference() {
		return this.registrationRepository.minRegistrationsPerConference();
	}

	public Integer maxRegistrationsPerConference() {
		return this.registrationRepository.maxRegistrationsPerConference();
	}

	public Double stdDevRegistrationsPerConference() {
		return this.registrationRepository.stdDevRegistrationsPerConference();
	}
}
