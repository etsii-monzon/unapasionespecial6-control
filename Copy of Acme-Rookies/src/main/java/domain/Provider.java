
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Provider extends Actor {

	private Collection<Item>	items;
	private String				make;


	@OneToMany
	public Collection<Item> getItems() {
		return this.items;
	}

	public void setItems(final Collection<Item> items) {
		this.items = items;
	}
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}

}
