
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.QuoletRepository;
import domain.Quolet;

@Service
@Transactional
public class QuoletService {

	//Managed repository
	@Autowired
	private QuoletRepository	quoletRepository;

	//Supporting services
	@Autowired
	private CompanyService		companyService;

	@Autowired
	private AuditService		auditService;


	// SIMPLE CRUD METHODS

	public Quolet create(final int auditId) {

		Assert.isTrue(this.companyService.checkPrincipal());

		Quolet quolet;
		quolet = new Quolet();

		quolet.setName(this.createTicker());
		quolet.setAudit(this.auditService.findOne(auditId));

		return quolet;
	}

	public Collection<Quolet> findAll() {

		Collection<Quolet> quolets;
		quolets = this.quoletRepository.findAll();

		return quolets;

	}

	public Quolet findOne(final int quoletId) {

		Assert.notNull(quoletId);
		Quolet audit;
		audit = this.quoletRepository.findOne(quoletId);

		return audit;
	}

	public Quolet save(final Quolet quolet) {
		Assert.isTrue(this.companyService.checkPrincipal());
		Assert.notNull(quolet);
		final Quolet res;

		res = this.quoletRepository.save(quolet);

		if (!res.getAudit().getQuolets().contains(res))
			res.getAudit().getQuolets().add(res);

		return res;
	}
	public void delete(final Quolet quolet) {
		Assert.isTrue(this.companyService.checkPrincipal());

		Assert.notNull(quolet);
		Assert.isTrue(quolet.getId() != 0);
		//Este delete solo sirve para cuando está en draftMode
		Assert.isTrue(quolet.isDraftMode());

		Assert.notNull(quolet.getAudit());

		quolet.getAudit().getQuolets().remove(quolet);

		this.quoletRepository.delete(quolet);
	}

	public void flush() {
		this.quoletRepository.flush();
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

		final char c1 = secuencia.charAt((int) (Math.random() * (secuencia.length() - 1)));
		final char c2 = secuencia.charAt((int) (Math.random() * (secuencia.length() - 1)));
		final char c3 = secuencia.charAt((int) (Math.random() * (secuencia.length() - 1)));

		if (month < 10 && day < 10)
			res = year + month + "0" + day + "#" + c1 + "" + c2 + "" + c3;
		else if (month < 10 && day > 10)
			res = year + "0" + month + "" + day + "#" + c1 + "" + c2 + "" + c3;
		else if (month > 10 && day < 10)
			res = year + month + "0" + day + "#" + c1 + "" + c2 + "" + c3;
		else
			res = year + month + "" + day + "-" + c1 + "" + c2 + "" + c3;

		return res;

	}

	public Double avgNumberQuoletsPerAudit() {
		return this.quoletRepository.avgNumberQuoletsPerAudit();
	}

	public Double stdNumberQuoletsPerAudit() {
		return this.quoletRepository.stdNumberQuoletsPerAudit();
	}
	public Double ratioQuoletsPublished() {
		return this.quoletRepository.ratioQuoletPublished();
	}

	public Double ratioQuoletsUnpublished() {
		return this.quoletRepository.ratioQuoletUnpublished();
	}

}
