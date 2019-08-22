
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Wert extends DomainEntity {

	private String			ticker;
	private Date			publicationMoment;
	private String			body;
	private String			atrib1;
	private String			atrib2;

	private boolean			draftMode;

	private Administrator	administrator;


	//	@Column(unique = true)
	@NotBlank
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getPublicationMoment() {
		return this.publicationMoment;
	}

	public void setPublicationMoment(final Date publicationMoment) {
		this.publicationMoment = publicationMoment;
	}

	@NotBlank
	@Size(min = 0, max = 100)
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}
	@NotBlank
	public String getAtrib1() {
		return this.atrib1;
	}

	public void setAtrib1(final String atrib1) {
		this.atrib1 = atrib1;
	}
	@NotBlank
	public String getAtrib2() {
		return this.atrib2;
	}

	public void setAtrib2(final String atrib2) {
		this.atrib2 = atrib2;
	}

	@NotNull
	public boolean isDraftMode() {
		return this.draftMode;
	}

	public void setDraftMode(final boolean draftMode) {
		this.draftMode = draftMode;
	}

	@ManyToOne
	@NotNull
	public Administrator getAdministrator() {
		return this.administrator;
	}

	public void setAdministrator(final Administrator administrator) {
		this.administrator = administrator;
	}

}
