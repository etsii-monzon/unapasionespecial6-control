
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	private String					englishTitle;
	private String					spanishTitle;

	private Category				parent;
	private Collection<Category>	children;


	@NotBlank
	public String getEnglishTitle() {
		return this.englishTitle;
	}

	public void setEnglishTitle(final String englishTitle) {
		this.englishTitle = englishTitle;
	}
	@NotBlank
	public String getSpanishTitle() {
		return this.spanishTitle;
	}

	public void setSpanishTitle(final String spanishTitle) {
		this.spanishTitle = spanishTitle;
	}

	@OneToMany
	@Valid
	public Collection<Category> getChildren() {
		return this.children;
	}

	public void setChildren(final Collection<Category> children) {
		this.children = children;
	}

	@ManyToOne
	public Category getParent() {
		return this.parent;
	}

	public void setParent(final Category parent) {
		this.parent = parent;
	}

}
