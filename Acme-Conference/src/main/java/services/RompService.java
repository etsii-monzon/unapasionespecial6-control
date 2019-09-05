
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RompRepository;
import domain.Romp;

@Service
@Transactional
public class RompService {

	@Autowired
	private RompRepository			rompRepository;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private ConferenceService		conferenceService;


	public Romp create() {
		Assert.isTrue(this.adminService.checkPrincipal());
		Romp res;

		res = new Romp();

		res.setTicker(this.createRompTicker());
		res.setAdministrator(this.adminService.findByPrincipal());

		return res;
	}
	public Romp findOne(final int rompId) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Romp wert;
		wert = this.rompRepository.findOne(rompId);
		return wert;
	}

	public Collection<Romp> findAll() {
		Assert.isTrue(this.adminService.checkPrincipal());
		return this.rompRepository.findAll();
	}

	public Romp save(final Romp romp) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Romp res;

		if (romp.isDraftMode() == false)
			romp.setPublicationMoment(new Date());

		res = this.rompRepository.save(romp);

		return res;

	}

	public void delete(final Romp romp) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Assert.isTrue(romp.isDraftMode(), "draft mode");
		Assert.notNull(romp);

		this.conferenceService.getConferenceByRomp(romp.getId()).getRomps().remove(romp);

		this.rompRepository.delete(romp);

	}

	public Romp checkTicker(final String ticker) {
		return this.rompRepository.checkTicker(ticker);
	}

	public String createRompTicker() {

		final String res;

		final Calendar calendar = new GregorianCalendar();
		final int year = calendar.get(Calendar.YEAR) - 2000;
		final int month = calendar.get(Calendar.MONTH) + 1;

		final int day = calendar.get(Calendar.DAY_OF_MONTH);

		final String secuencia = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz_";

		final char c1 = secuencia.charAt((int) (Math.random() * (secuencia.length() - 1)));
		final char c2 = secuencia.charAt((int) (Math.random() * (secuencia.length() - 1)));
		final char c3 = secuencia.charAt((int) (Math.random() * (secuencia.length() - 1)));
		final char c4 = secuencia.charAt((int) (Math.random() * (secuencia.length() - 1)));
		final char c5 = secuencia.charAt((int) (Math.random() * (secuencia.length() - 1)));

		if (month < 10 && day < 10)
			res = c1 + "" + c2 + "" + c3 + "-" + year + "0" + month + "-" + c4 + "" + c5 + "/" + "0" + day;
		else if (month < 10 && day > 10)
			res = c1 + "" + c2 + "" + c3 + "-" + year + "0" + month + "-" + c4 + "" + c5 + "/" + day;

		else if (month > 10 && day < 10)
			res = c1 + "" + c2 + "" + c3 + "-" + year + month + "-" + c4 + "" + c5 + "/" + "0" + day;
		else
			res = c1 + "" + c2 + "" + c3 + "-" + year + month + "-" + c4 + "" + c5 + "/" + day;

		//Comprobamos unicidad del ticker
		//Si el Ticker ya existe,hacemos una llamada recursiva al m�todo para crear otro
		if (this.checkTicker(res) != null)
			return this.createRompTicker();
		else
			//Si el ticker no existe(devuelve null) devolvemos el generado
			return res;

	}

	public Collection<Romp> getFinalRomps(final int conferenceId) {
		return this.rompRepository.getFinalRomps(conferenceId);
	}

	public Double draftVersusTotal() {
		return this.rompRepository.draftVersusTotal();
	}

	public Double finalVersusTotal() {
		return this.rompRepository.finalVersusTotal();
	}
	public Double avgRompsPerConference() {
		return this.rompRepository.avgRompsPerConference();
	}
	public Double stddevRompsPerConference() {
		return this.rompRepository.stddevRompsPerConference();
	}

}
