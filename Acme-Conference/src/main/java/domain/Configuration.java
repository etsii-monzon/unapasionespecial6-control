
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	private String				sistemName;
	private String				countryCode;
	private String				bannerURL;
	private String				welcomeEN;
	private String				welcomeSP;
	private Collection<Topic>	topics;
	private Collection<String>	makes;


	@NotBlank
	@Range(min = 1, max = 999)
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@URL
	@NotBlank
	public String getBannerURL() {
		return this.bannerURL;
	}

	public void setBannerURL(final String bannerURL) {
		this.bannerURL = bannerURL;
	}
	@NotBlank
	public String getSistemName() {
		return this.sistemName;
	}

	public void setSistemName(final String sistemName) {
		this.sistemName = sistemName;
	}
	@NotBlank
	public String getWelcomeEN() {
		return this.welcomeEN;
	}

	public void setWelcomeEN(final String welcomeEN) {
		this.welcomeEN = welcomeEN;
	}
	@NotBlank
	public String getWelcomeSP() {
		return this.welcomeSP;
	}

	public void setWelcomeSP(final String welcomeSP) {
		this.welcomeSP = welcomeSP;
	}

	@OneToMany
	@Valid
	public Collection<Topic> getTopics() {
		return this.topics;
	}

	public void setTopics(final Collection<Topic> topics) {
		this.topics = topics;
	}

	@ElementCollection
	public Collection<String> getMakes() {
		return this.makes;
	}

	public void setMakes(final Collection<String> makes) {
		this.makes = makes;
	}

}
