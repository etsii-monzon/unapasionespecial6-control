
package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;
import domain.CreditCard;
import domain.DomainEntity;

@Access(AccessType.PROPERTY)
public class CompanyForm extends DomainEntity {

	private String		name;
	private String		surname;
	private String		optionalPhoto;
	private String		email;
	private String		phoneNumber;
	private String		optionalAddress;
	private UserAccount	userAccount;
	private Double		vat;
	private CreditCard	creditCard;
	private String		confirmPassword;

	private String		commercialName;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}
	@URL
	public String getOptionalPhoto() {
		return this.optionalPhoto;
	}

	public void setOptionalPhoto(final String optionalPhoto) {
		this.optionalPhoto = optionalPhoto;
	}
	@Email
	@NotBlank
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getOptionalAddress() {
		return this.optionalAddress;
	}

	public void setOptionalAddress(final String optionalAddress) {
		this.optionalAddress = optionalAddress;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	@NotNull
	public Double getVat() {
		return this.vat;
	}

	public void setVat(final Double vat) {
		this.vat = vat;
	}
	@NotNull
	@Valid
	@OneToOne(optional = false)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@NotBlank
	public String getCommercialName() {
		return this.commercialName;
	}

	public void setCommercialName(final String commercialName) {
		this.commercialName = commercialName;
	}

}
