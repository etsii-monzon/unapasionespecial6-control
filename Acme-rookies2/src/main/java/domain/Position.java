
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = @Index(columnList = "title,description,profile"))
public class Position extends DomainEntity {

	private String				title;
	private String				description;
	private Date				deadline;
	private String				profile;
	private Collection<String>	skills;
	private Collection<String>	technologies;
	private Double				salary;
	private String				ticker;
	private boolean				draftMode;

	private Collection<Xomp>	xomps;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(final Date deadline) {
		this.deadline = deadline;
	}
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getProfile() {
		return this.profile;
	}

	public void setProfile(final String profile) {
		this.profile = profile;
	}
	@ElementCollection
	@NotEmpty
	public Collection<String> getSkills() {
		return this.skills;
	}

	public void setSkills(final Collection<String> skills) {
		this.skills = skills;
	}
	@ElementCollection
	@NotEmpty
	public Collection<String> getTechnologies() {
		return this.technologies;
	}

	public void setTechnologies(final Collection<String> technologies) {
		this.technologies = technologies;
	}
	@NotNull
	public Double getSalary() {
		return this.salary;
	}

	public void setSalary(final Double salary) {
		this.salary = salary;
	}
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}
	@NotNull
	public boolean isDraftMode() {
		return this.draftMode;
	}

	public void setDraftMode(final boolean draftMode) {
		this.draftMode = draftMode;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Xomp> getXomps() {
		return this.xomps;
	}

	public void setXomps(final Collection<Xomp> xomps) {
		this.xomps = xomps;
	}

}
