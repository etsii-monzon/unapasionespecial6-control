
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Quolet extends DomainEntity {

	private String	name;
	private String	status;
	private String	body;
	private String	optionalPicture;
	private boolean	draftMode;

	private Audit	audit;


	//	@Pattern(regexp = "^d{6}#\\w{3}$")
	@NotBlank
	@Column(unique = true)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	//	@DateTimeFormat(pattern = "yy/MM/dd HH:mm")
	//	//	@NotNull
	//	public Date getPublicationMoment() {
	//		return this.publicationMoment;
	//	}
	//
	//	public void setPublicationMoment(final Date publicationMoment) {
	//		this.publicationMoment = publicationMoment;
	//	}

	@NotBlank
	@Size(max = 100)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getOptionalPicture() {
		return this.optionalPicture;
	}

	public void setOptionalPicture(final String optionalPicture) {
		this.optionalPicture = optionalPicture;
	}

	@NotNull
	public boolean isDraftMode() {
		return this.draftMode;
	}

	public void setDraftMode(final boolean draftMode) {
		this.draftMode = draftMode;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = true)
	public Audit getAudit() {
		return this.audit;
	}

	public void setAudit(final Audit audit) {
		this.audit = audit;
	}

	@Pattern(regexp = "^(TROME)|(GORK)|(STIM)|(ZURK)$")
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

}
