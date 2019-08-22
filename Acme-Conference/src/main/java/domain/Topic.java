
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Topic extends DomainEntity {

	private String	englishName;
	private String	spanishName;


	@NotBlank
	public String getEnglishName() {
		return this.englishName;
	}

	public void setEnglishName(final String englishName) {
		this.englishName = englishName;
	}
	@NotBlank
	public String getSpanishName() {
		return this.spanishName;
	}

	public void setSpanishName(final String spanishName) {
		this.spanishName = spanishName;
	}

}
