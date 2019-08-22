
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Conference extends CommentEntity {

	private String				title;
	private String				acronym;
	private String				venue;
	private String				summary;
	private Date				submissionDeadline;
	private Date				notificationDeadline;
	private Date				cameraDeadline;
	private Date				startDate;
	private Date				endDate;
	private Double				fee;
	private boolean				draftMode;
	private Category			category;

	//Control
	private Collection<Wert>	werts;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getAcronym() {
		return this.acronym;
	}

	public void setAcronym(final String acronym) {
		this.acronym = acronym;
	}
	@NotBlank
	public String getVenue() {
		return this.venue;
	}

	public void setVenue(final String venue) {
		this.venue = venue;
	}

	@NotBlank
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getSubmissionDeadline() {
		return this.submissionDeadline;
	}

	public void setSubmissionDeadline(final Date submissionDeadline) {
		this.submissionDeadline = submissionDeadline;
	}
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getNotificationDeadline() {
		return this.notificationDeadline;
	}

	public void setNotificationDeadline(final Date notificationDeadline) {
		this.notificationDeadline = notificationDeadline;
	}
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCameraDeadline() {
		return this.cameraDeadline;
	}

	public void setCameraDeadline(final Date cameraDeadline) {
		this.cameraDeadline = cameraDeadline;
	}
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}
	@NotNull
	public Double getFee() {
		return this.fee;
	}

	public void setFee(final Double fee) {
		this.fee = fee;
	}

	@NotNull
	public boolean isDraftMode() {
		return this.draftMode;
	}

	public void setDraftMode(final boolean draftMode) {
		this.draftMode = draftMode;
	}

	@NotNull
	@ManyToOne
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	@OneToMany
	@Valid
	public Collection<Wert> getWerts() {
		return this.werts;
	}

	public void setWerts(final Collection<Wert> werts) {
		this.werts = werts;
	}

}
