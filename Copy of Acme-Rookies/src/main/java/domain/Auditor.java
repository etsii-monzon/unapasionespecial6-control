
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Auditor extends Actor {

	private Collection<Audit>	audits;


	@OneToMany
	public Collection<Audit> getAudits() {
		return this.audits;
	}

	public void setAudits(final Collection<Audit> audits) {
		this.audits = audits;
	}

}
