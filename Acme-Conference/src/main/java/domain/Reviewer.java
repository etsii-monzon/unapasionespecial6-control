
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class Reviewer extends Actor {

	private Collection<Report>	reports;
	private Collection<String>	keywords;


	@OneToMany
	@Valid
	public Collection<Report> getReports() {
		return this.reports;
	}

	public void setReports(final Collection<Report> reports) {
		this.reports = reports;
	}

	@ElementCollection
	@NotEmpty
	public Collection<String> getKeywords() {
		return this.keywords;
	}

	public void setKeywords(final Collection<String> keywords) {
		this.keywords = keywords;
	}

}
