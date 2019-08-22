
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {

	//Managed repository
	@Autowired
	private CreditCardRepository	creditCardRepository;


	//Supporting services

	// SIMPLE CRUD METHODS

	public CreditCard create() {

		CreditCard creditCard;
		creditCard = new CreditCard();

		return creditCard;
	}

	public CreditCard save(final CreditCard c) {
		Assert.notNull(c);

		final CreditCard result = this.creditCardRepository.save(c);
		Assert.notNull(result);

		return result;

	}

	public Collection<CreditCard> findall() {

		final Collection<CreditCard> res = this.creditCardRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public void delete(final CreditCard c) {
		Assert.notNull(c);
		Assert.isTrue(c.getId() != 0);
		Assert.notNull(this.creditCardRepository.findOne(c.getId()));

		this.creditCardRepository.delete(c);
	}

	public boolean checkCreditCard(final CreditCard c) {
		boolean res = false;
		final int mes = c.getExpMonth();
		final int año = c.getExpYear();

		final GregorianCalendar fechaActual = new GregorianCalendar();
		if ((año >= fechaActual.get(Calendar.YEAR) - 1999) || (mes >= fechaActual.get(Calendar.MONTH) + 1 && año == fechaActual.get(Calendar.YEAR) - 2000))
			res = true;
		return res;
	}
}
