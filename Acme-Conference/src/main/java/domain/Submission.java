
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Submission extends DomainEntity {

	private String				ticker;
	private Date				moment;
	private String				status;
	private Paper				paper;
	private Conference			conference;
	public boolean				cameraReady;
	public Collection<Reviewer>	reviewers;


	@NotBlank
	@Column(unique = true)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@Pattern(regexp = "^(UNDER-REVIEW)|(ACCEPTED)|(REJECTED)$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	public Paper getPaper() {
		return this.paper;
	}

	public void setPaper(final Paper paper) {
		this.paper = paper;
	}
	@OneToOne
	@NotNull
	public Conference getConference() {
		return this.conference;
	}

	public void setConference(final Conference conference) {
		this.conference = conference;
	}

	@ManyToMany
	@Valid
	public Collection<Reviewer> getReviewers() {
		return this.reviewers;
	}

	public void setReviewers(final Collection<Reviewer> reviewers) {
		this.reviewers = reviewers;
	}

	@NotNull
	public boolean isCameraReady() {
		return this.cameraReady;
	}

	public void setCameraReady(final boolean cameraReady) {
		this.cameraReady = cameraReady;
	}

}
