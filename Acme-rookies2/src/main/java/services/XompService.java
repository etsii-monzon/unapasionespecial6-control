
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.XompRepository;
import domain.Xomp;

@Service
@Transactional
public class XompService {

	//Managed repository
	@Autowired
	private XompRepository	xompRepository;

	//Supporting services
	@Autowired
	private CompanyService	companyService;

	@Autowired
	private PositionService	positionService;


	// SIMPLE CRUD METHODS

	public Xomp create(final int positionId) {

		Assert.isTrue(this.companyService.checkPrincipal());

		Xomp quolet;
		quolet = new Xomp();

		quolet.setTicker(this.createTicker());
		quolet.setPosition(this.positionService.findOne(positionId));

		return quolet;
	}

	public Collection<Xomp> findAll() {

		Collection<Xomp> quolets;
		quolets = this.xompRepository.findAll();

		return quolets;

	}

	public Xomp findOne(final int quoletId) {

		Assert.notNull(quoletId);
		Xomp audit;
		audit = this.xompRepository.findOne(quoletId);

		return audit;
	}

	public Xomp save(final Xomp quolet) {
		Assert.isTrue(this.companyService.checkPrincipal());
		Assert.notNull(quolet);
		final Xomp res;

		res = this.xompRepository.save(quolet);

		if (!res.getPosition().getXomps().contains(res))
			res.getPosition().getXomps().add(res);

		return res;
	}
	public void delete(final Xomp quolet) {
		Assert.isTrue(this.companyService.checkPrincipal());

		Assert.notNull(quolet);
		Assert.isTrue(quolet.getId() != 0);
		//Este delete solo sirve para cuando está en draftMode
		//Assert.isTrue(quolet.isDraftMode());

		Assert.notNull(quolet.getPosition());

		quolet.getPosition().getXomps().remove(quolet);

		this.xompRepository.delete(quolet);
	}

	public void flush() {
		this.xompRepository.flush();
	}

	//Método para crear ticker
	public String createTicker() {
		final String res;

		final Calendar calendar = new GregorianCalendar();
		final int year = calendar.get(Calendar.YEAR) - 2000;
		final int month = calendar.get(Calendar.MONTH) + 1;
		final int day = calendar.get(Calendar.DAY_OF_MONTH);

		//		year = new SimpleDateFormat("yy").format(Calendar.getInstance().getTime());
		//		month = new SimpleDateFormat("MM").format(Calendar.getInstance().getTime());
		//		day = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());

		final String secuencia = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		final String secuencia2 = "0123456789";

		final char c1 = secuencia.charAt((int) (Math.random() * (secuencia.length() - 1)));
		final char c2 = secuencia.charAt((int) (Math.random() * (secuencia.length() - 1)));
		final char c5 = secuencia2.charAt((int) (Math.random() * (secuencia2.length() - 1)));
		final char c6 = secuencia2.charAt((int) (Math.random() * (secuencia2.length() - 1)));

		if (month < 10 && day < 10)
			res = "0" + day + "-" + c1 + "" + c2 + "" + c5 + "" + c6 + "" + "-" + "0" + month + year;
		else if (month < 10 && day > 10)
			res = "" + day + "-" + c1 + "" + c2 + "" + c5 + "" + c6 + "" + "-" + "0" + month + year;
		else if (month > 10 && day < 10)
			res = "0" + day + "-" + c1 + "" + c2 + "" + c5 + "" + c6 + "" + "-" + "" + month + year;
		else
			res = "" + day + "-" + c1 + "" + c2 + "" + c5 + "" + c6 + "" + "-" + "" + month + year;

		return res;

	}

	public Double avgNumberXompsPerAudit() {
		return this.xompRepository.avgNumberXompsPerAudit();
	}

	public Double stdNumberXompsPerAudit() {
		return this.xompRepository.stdNumberXompsPerAudit();
	}
	public Double ratioXompsPublished() {
		return this.xompRepository.ratioXompPublished();
	}

	public Double ratioXompsUnpublished() {
		return this.xompRepository.ratioXompUnpublished();
	}

}
