
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Author extends Actor {

	private Collection<Registration>	registrations;
	private Collection<Submission>		submissions;


	@OneToMany
	@Valid
	public Collection<Registration> getRegistrations() {
		return this.registrations;
	}

	public void setRegistrations(final Collection<Registration> registrations) {
		this.registrations = registrations;
	}

	@OneToMany
	@Valid
	public Collection<Submission> getSubmissions() {
		return this.submissions;
	}

	public void setSubmissions(final Collection<Submission> submissions) {
		this.submissions = submissions;
	}

}
