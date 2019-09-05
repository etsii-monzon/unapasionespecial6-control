
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.LootRepository;
import domain.Loot;

@Service
@Transactional
public class LootService {

	@Autowired
	private LootRepository			lootRepository;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private ConferenceService		conferenceService;


	public Loot create() {
		Assert.isTrue(this.adminService.checkPrincipal());
		Loot res;

		res = new Loot();

		res.setTicker(this.createLootTicker());
		res.setAdministrator(this.adminService.findByPrincipal());

		return res;
	}
	public Loot findOne(final int lootId) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Loot loot;
		loot = this.lootRepository.findOne(lootId);
		return loot;
	}

	public Collection<Loot> findAll() {
		Assert.isTrue(this.adminService.checkPrincipal());
		return this.lootRepository.findAll();
	}

	public Loot save(final Loot loot) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Loot res;

		if (loot.isDraftMode() == false)
			loot.setPublicationMoment(new Date());

		res = this.lootRepository.save(loot);

		return res;

	}

	public void delete(final Loot loot) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Assert.isTrue(loot.isDraftMode(), "draft mode");
		Assert.notNull(loot);

		this.conferenceService.getConferenceByLoot(loot.getId()).getLoots().remove(loot);

		this.lootRepository.delete(loot);

	}

	public Loot checkTicker(final String ticker) {
		return this.lootRepository.checkTicker(ticker);
	}

	public String createLootTicker() {

		final String res;
		final Calendar calendar = new GregorianCalendar();
		final int año = calendar.get(Calendar.YEAR) - 2000;
		final int mes = calendar.get(Calendar.MONTH) + 1;

		final int dia = calendar.get(Calendar.DAY_OF_MONTH);

		final String secuencia = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz_";

		final char c1 = secuencia.charAt((int) (Math.random() * (secuencia.length() - 1)));
		final char c2 = secuencia.charAt((int) (Math.random() * (secuencia.length() - 1)));
		final char c3 = secuencia.charAt((int) (Math.random() * (secuencia.length() - 1)));
		final char c4 = secuencia.charAt((int) (Math.random() * (secuencia.length() - 1)));

		if (mes < 10 && dia < 10)
			res = c1 + "" + c2 + "" + "" + año + "0" + mes + "0" + dia + "" + c3 + "" + c4;
		else if (mes < 10 && dia > 10)
			res = c1 + "" + c2 + "" + "" + año + "0" + mes + "" + dia + "" + c3 + "" + c4;

		else if (mes > 10 && dia < 10)
			res = c1 + "" + c2 + "" + "" + año + "" + mes + "0" + dia + "" + c3 + "" + c4;
		else
			res = c1 + "" + c2 + "" + "" + año + "" + mes + "" + dia + "" + c3 + "" + c4;

		//Comprobamos unicidad del ticker
		//Si el Ticker ya existe,hacemos una llamada recursiva al mï¿½todo para crear otro
		if (this.checkTicker(res) != null)
			return this.createLootTicker();
		else
			//Si el ticker no existe(devuelve null) devolvemos el generado
			return res;

	}

	public Double avgNumberLootsPerConference() {
		return this.lootRepository.avgNumberLootsPerConference();
	}

	public Double stdNumberLootsPerConference() {
		return this.lootRepository.stdNumberLootsPerConference();
	}
	public Double ratioLootPublished() {
		return this.lootRepository.ratioLootPublished();
	}

	public Double ratioLootUnpublished() {
		return this.lootRepository.ratioLootUnpublished();
	}

}
