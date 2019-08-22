
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Report extends DomainEntity {

	private Integer		originality;
	private Integer		quality;
	private Integer		readability;
	private String		decision;
	private String		comment;
	public Submission	submission;


	@NotNull
	@Range(min = 0, max = 10)
	public Integer getOriginality() {
		return this.originality;
	}

	public void setOriginality(final Integer originality) {
		this.originality = originality;
	}
	@NotNull
	@Range(min = 0, max = 10)
	public Integer getQuality() {
		return this.quality;
	}

	public void setQuality(final Integer quality) {
		this.quality = quality;
	}
	@NotNull
	@Range(min = 0, max = 10)
	public Integer getReadability() {
		return this.readability;
	}

	public void setReadability(final Integer readability) {
		this.readability = readability;
	}
	@NotNull
	@Pattern(regexp = "^(REJECT)|(BORDER-LINE)|(ACCEPT)$")
	public String getDecision() {
		return this.decision;
	}

	public void setDecision(final String decision) {
		this.decision = decision;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	@NotNull
	@OneToOne
	@Valid
	public Submission getSubmission() {
		return this.submission;
	}

	public void setSubmission(final Submission submission) {
		this.submission = submission;
	}

}
