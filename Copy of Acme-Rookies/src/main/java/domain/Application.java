
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	private Date		moment;
	private Date		momentSumbit;
	private String		status;
	private String		explanations;
	private String		link;
	private Position	position;
	private Problem		problem;


	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMomentSumbit() {
		return this.momentSumbit;
	}

	public void setMomentSumbit(final Date momentSumbit) {
		this.momentSumbit = momentSumbit;
	}
	@NotBlank
	@Pattern(regexp = "^(PENDING)|(ACCEPTED)|(SUBMITTED)|(REJECTED)$")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getExplanations() {
		return this.explanations;
	}

	public void setExplanations(final String explanations) {
		this.explanations = explanations;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getLink() {
		return this.link;
	}

	public void setLink(final String link) {
		this.link = link;
	}
	@Valid
	@OneToOne
	public Position getPosition() {
		return this.position;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}
	@Valid
	@OneToOne
	public Problem getProblem() {
		return this.problem;
	}

	public void setProblem(final Problem problem) {
		this.problem = problem;
	}

}
