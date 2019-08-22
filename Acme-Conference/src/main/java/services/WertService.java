
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.WertRepository;
import domain.Wert;

@Service
@Transactional
public class WertService {

	@Autowired
	private WertRepository			wertRepository;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private ConferenceService		conferenceService;


	public Wert create() {
		Assert.isTrue(this.adminService.checkPrincipal());
		Wert res;

		res = new Wert();

		res.setTicker(this.createWertTicker());
		res.setAdministrator(this.adminService.findByPrincipal());

		return res;
	}
	public Wert findOne(final int wertId) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Wert wert;
		wert = this.wertRepository.findOne(wertId);
		return wert;
	}

	public Collection<Wert> findAll() {
		Assert.isTrue(this.adminService.checkPrincipal());
		return this.wertRepository.findAll();
	}

	public Wert save(final Wert wert) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Wert res;

		if (wert.isDraftMode() == false)
			wert.setPublicationMoment(new Date());

		res = this.wertRepository.save(wert);

		return res;

	}

	public void delete(final Wert wert) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Assert.isTrue(wert.isDraftMode(), "draft mode");
		Assert.notNull(wert);

		this.conferenceService.getConferenceByWert(wert.getId()).getWerts().remove(wert);

		this.wertRepository.delete(wert);

	}

	public Wert checkTicker(final String ticker) {
		return this.wertRepository.checkTicker(ticker);
	}

	public String createWertTicker() {

		final Calendar hoy = new GregorianCalendar();

		final String ticker = String.valueOf(hoy.get(Calendar.DAY_OF_MONTH)) + String.valueOf(hoy.get(Calendar.MONTH) + 1) + String.valueOf(hoy.get(Calendar.YEAR));

		//Comprobamos unicidad del ticker
		//Si el Ticker ya existe,hacemos una llamada recursiva al m�todo para crear otro
		//		if (this.checkTicker(ticker) != null)
		//			return this.createWertTicker();
		//		else
		//			//Si el ticker no existe(devuelve null) devolvemos el generado
		//			return ticker;
		return ticker;

	}

}
