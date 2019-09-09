
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	private String	sistemName;
	private String	countryCode;
	private String	bannerURL;
	private String	welcomeEN;
	private String	welcomeSP;
	private boolean	reBrand;


	@NotBlank
	@Range(min = 1, max = 999)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@URL
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBannerURL() {
		return this.bannerURL;
	}

	public void setBannerURL(final String bannerURL) {
		this.bannerURL = bannerURL;
	}
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSistemName() {
		return this.sistemName;
	}

	public void setSistemName(final String sistemName) {
		this.sistemName = sistemName;
	}
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getWelcomeEN() {
		return this.welcomeEN;
	}

	public void setWelcomeEN(final String welcomeEN) {
		this.welcomeEN = welcomeEN;
	}
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getWelcomeSP() {
		return this.welcomeSP;
	}

	public void setWelcomeSP(final String welcomeSP) {
		this.welcomeSP = welcomeSP;
	}

	@NotNull
	public boolean isReBrand() {
		return this.reBrand;
	}

	public void setReBrand(final boolean reBrand) {
		this.reBrand = reBrand;
	}

}
