
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NetcasheRepository;
import domain.Netcashe;

@Service
@Transactional
public class NetcasheService {

	//Managed repository
	@Autowired
	private NetcasheRepository	netcasheRepository;

	//Supporting services
	@Autowired
	private CompanyService		companyService;

	@Autowired
	private AuditService		auditService;


	// SIMPLE CRUD METHODS

	public Netcashe create(final int auditId) {

		Assert.isTrue(this.companyService.checkPrincipal());

		Netcashe quolet;
		quolet = new Netcashe();

		quolet.setTrack_id(this.createTicker());
		quolet.setAudit(this.auditService.findOne(auditId));

		return quolet;
	}

	public Collection<Netcashe> findAll() {

		Collection<Netcashe> quolets;
		quolets = this.netcasheRepository.findAll();

		return quolets;

	}

	public Netcashe findOne(final int quoletId) {

		Assert.notNull(quoletId);
		Netcashe audit;
		audit = this.netcasheRepository.findOne(quoletId);

		return audit;
	}

	public Netcashe save(final Netcashe quolet) {
		Assert.isTrue(this.companyService.checkPrincipal());
		Assert.notNull(quolet);
		final Netcashe res;

		res = this.netcasheRepository.save(quolet);

		if (!res.getAudit().getNetcashes().contains(res))
			res.getAudit().getNetcashes().add(res);

		return res;
	}
	public void delete(final Netcashe quolet) {
		Assert.isTrue(this.companyService.checkPrincipal());

		Assert.notNull(quolet);
		Assert.isTrue(quolet.getId() != 0);
		//Este delete solo sirve para cuando está en draftMode
		Assert.isTrue(quolet.isDraftMode());

		Assert.notNull(quolet.getAudit());

		quolet.getAudit().getNetcashes().remove(quolet);

		this.netcasheRepository.delete(quolet);
	}

	public void flush() {
		this.netcasheRepository.flush();
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

		final String secuencia = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz_";
		final String secuencia2 = "0123456789";

		final char c1 = secuencia.charAt((int) (Math.random() * (secuencia.length() - 1)));
		final char c2 = secuencia.charAt((int) (Math.random() * (secuencia.length() - 1)));
		final char c5 = secuencia2.charAt((int) (Math.random() * (secuencia2.length() - 1)));
		final char c6 = secuencia2.charAt((int) (Math.random() * (secuencia2.length() - 1)));

		if (month < 10 && day < 10)
			res = year + "" + "0" + month + "" + "0" + day + "/" + c5 + "" + c6 + "/" + c1 + "" + c2;
		else if (month < 10 && day > 10)
			res = year + "" + "0" + month + "" + day + "/" + c5 + "" + c6 + "/" + c1 + "" + c2;
		else if (month > 10 && day < 10)
			res = year + "" + month + "" + "0" + day + "/" + c5 + "" + c6 + "/" + c1 + "" + c2;
		else
			res = year + "" + month + "" + day + "/" + c5 + "" + c6 + "/" + c1 + "" + c2;

		return res;

	}

	public Double avgNumberNetcashesPerAudit() {
		return this.netcasheRepository.avgNumberNetcashesPerAudit();
	}

	public Double stdNumberNetcashesPerAudit() {
		return this.netcasheRepository.stdNumberNetcashesPerAudit();
	}
	public Double ratioNetcashesPublished() {
		return this.netcasheRepository.ratioNetcashesPublished();
	}

	public Double ratioNetcashesUnpublished() {
		return this.netcasheRepository.ratioNetcashesUnpublished();
	}

}
