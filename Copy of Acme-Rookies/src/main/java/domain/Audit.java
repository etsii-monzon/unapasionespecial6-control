
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Audit extends DomainEntity {

	private Date				moment;
	private String				text;
	private Integer				score;
	private Position			position;
	private Boolean				draftMode;

	//Relaciones

	private Collection<Xomp>	xomps;


	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Range(min = 0, max = 10)
	@NotNull
	public Integer getScore() {
		return this.score;
	}

	public void setScore(final Integer score) {
		this.score = score;
	}

	@OneToOne
	@Valid
	@NotNull
	public Position getPosition() {
		return this.position;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

	@NotNull
	public Boolean getDraftMode() {
		return this.draftMode;
	}

	public void setDraftMode(final Boolean draftMode) {
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
