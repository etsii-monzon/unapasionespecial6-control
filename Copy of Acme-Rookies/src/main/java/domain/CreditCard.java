
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class CreditCard extends DomainEntity {

	private String	holderName;
	private String	brandName;
	private String	number;
	private int		expMonth;
	private int		expYear;
	private int		cvv;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getHolderName() {
		return this.holderName;
	}

	public void setHolderName(final String holderName) {
		this.holderName = holderName;
	}
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(final String brandName) {
		this.brandName = brandName;
	}

	@CreditCardNumber
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	@Range(min = 1, max = 12)
	public int getExpMonth() {
		return this.expMonth;
	}

	public void setExpMonth(final int expMonth) {
		this.expMonth = expMonth;
	}
	@Range(max = 99)
	@Digits(integer = 2, fraction = 0)
	public int getExpYear() {
		return this.expYear;
	}

	public void setExpYear(final int expYear) {
		this.expYear = expYear;
	}

	@Range(min = 100, max = 999)
	public int getCvv() {
		return this.cvv;
	}

	public void setCvv(final int cvv) {
		this.cvv = cvv;
	}

}
