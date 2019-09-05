
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConferenceRepository;
import domain.Activity;
import domain.Administrator;
import domain.Category;
import domain.Conference;
import domain.Registration;
import domain.Submission;

@Service
@Transactional
public class ConferenceService {

	//Managed repository
	@Autowired
	private ConferenceRepository	conferenceRepository;

	//Supporting services

	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private AuthorService			authorService;

	@Autowired
	private ActivityService			activityService;


	// SIMPLE CRUD METHODS

	public Conference create() {
		Assert.isTrue(this.administratorService.checkPrincipal());

		Conference con;
		con = new Conference();

		return con;
	}

	public Collection<Conference> findAll() {

		Collection<Conference> fms;
		fms = this.conferenceRepository.findAll();

		return fms;

	}
	public Conference findOne(final int conferenceId) {

		Assert.notNull(conferenceId);
		Conference fm;
		fm = this.conferenceRepository.findOne(conferenceId);

		return fm;
	}
	public Conference save(final Conference a) {

		Assert.notNull(a);
		Conference res;
		final Administrator b = this.administratorService.findByPrincipal();
		final Collection<Conference> f = b.getConferences();
		res = this.conferenceRepository.save(a);
		if (!f.contains(res))
			f.add(res);

		return res;
	}

	public void delete(final Conference a) {
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);

		final Administrator adm = this.administratorService.findByPrincipal();
		adm.getConferences().remove(a);

		for (final Activity act : this.activityService.findAllActivitiesByConference(a))
			this.activityService.delete(act);

		this.conferenceRepository.delete(a);

	}
	public Date variarFecha(final Date fecha, final int campo, final int valor) {
		if (valor == 0)
			return fecha;
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(campo, valor);
		return calendar.getTime();
	}

	public Collection<Conference> conferencesToRegist() {

		final Collection<Conference> conferences = new ArrayList<>();
		final Collection<Conference> conferencesAll = this.findAll();
		final Collection<Registration> registrations = this.authorService.findByPrincipal().getRegistrations();

		for (final Conference c : conferencesAll)
			if (c.isDraftMode() == false && c.getStartDate().after(new java.util.Date()))
				conferences.add(c);
		for (final Registration r : registrations)
			if (conferences.contains(r.getConference()))
				conferences.remove(r.getConference());

		return conferences;
	}
	public Collection<Conference> conferencesToSubmission() {

		final Collection<Conference> conferences = new ArrayList<>();
		final Collection<Conference> conferencesAll = this.findAll();
		final Collection<Submission> submissions = this.authorService.findByPrincipal().getSubmissions();

		for (final Conference c : conferencesAll)
			if (c.isDraftMode() == false && c.getSubmissionDeadline().after(new java.util.Date()))
				conferences.add(c);
		for (final Submission s : submissions)
			if (conferences.contains(s.getConference()))
				conferences.remove(s.getConference());

		return conferences;
	}

	public Collection<Conference> searchConferenceByKeyword(final String keyword) {
		return this.conferenceRepository.searchConferenceByKeyword(keyword);
	}

	@SuppressWarnings("deprecation")
	public Collection<Integer> daysPerConference() {
		final List<Date> endDates = (List<Date>) this.conferenceRepository.findEndDateFromConferencs();
		final List<Date> startDates = (List<Date>) this.conferenceRepository.findStartDateFromConferencs();
		final Collection<Integer> res = new ArrayList<Integer>();

		for (int i = 0; i < endDates.size(); i++) {
			final Date end = endDates.get(i);
			final Date start = startDates.get(i);

			final int days = end.getDate() - start.getDate();
			int months = end.getMonth() - start.getMonth();
			final int years = end.getYear() - start.getYear();

			Integer duracion = 0;
			if (days >= 0 && months >= 0)
				duracion = days + 30 * months;
			else if (days <= 0 && months < 0 && years > 0) {
				months = (12 - months) * 30;
				duracion = months + days;
			}
			res.add(duracion);

		}

		return res;
	}

	public Double avgDaysPerConference() {
		Double res = 0.0;
		Double total = 0.0;
		final Collection<Integer> aux = this.daysPerConference();

		for (final Integer d : aux)
			total += d;

		res = total / aux.size();
		return res;
	}

	public Integer minDaysPerConference() {
		Integer res = 0;
		final Collection<Integer> aux = this.daysPerConference();

		for (final Integer d : aux)
			if (res == 0)
				res = d;
			else if (d < res)
				res = d;

		return res;
	}

	public Integer maxDaysPerConference() {
		Integer res = 0;
		final Collection<Integer> aux = this.daysPerConference();

		for (final Integer d : aux)
			if (res == 0)
				res = d;
			else if (d > res)
				res = d;

		return res;
	}

	public Double stdDevDaysPerConference() {
		Double res = 0.0;
		final Collection<Integer> aux = this.daysPerConference();
		Integer sum = 0;
		final Double avg = this.avgDaysPerConference();
		final Double count = (double) aux.size();
		final Double avgPot = avg * avg;
		if ((count == avgPot))
			res = 0.0;
		else {
			for (final Integer d : aux)
				sum += d * d;
			res = Math.sqrt(sum / count - avgPot);
		}
		return res;
	}

	public Double avgConferenceFees() {
		return this.conferenceRepository.avgFeesPerConference();
	}

	public Double minConferenceFees() {
		return this.conferenceRepository.minFeesPerConference();
	}

	public Double maxConferenceFees() {
		return this.conferenceRepository.maxFeesPerConference();
	}

	public Double stdConferenceFees() {
		return this.conferenceRepository.stdDevFeesPerConference();
	}
	public Collection<Conference> getConferencesByCategory(final Category category) {
		return this.conferenceRepository.getConferencesByCategory(category);
	}
	public Collection<Conference> getFinalConferences() {
		return this.conferenceRepository.getFinalConferences();
	}
	public Collection<Conference> searchConferenceByMaxFee(final double fee) {
		return this.conferenceRepository.searchConferenceByMaxFee(fee);
	}
	public Collection<Conference> searchConferenceByKeyword2(final String keyword) {
		return this.searchConferenceByKeyword(keyword);
	}
	public Collection<Conference> filterConferenceByStartDate(final Date date) {
		final Collection<Conference> conferences = this.getFinalConferences();
		final Collection<Conference> toReturn = new ArrayList<>();

		for (final Conference c : conferences)
			if (c.getStartDate().after(date))
				toReturn.add(c);
		return toReturn;
	}

	public Collection<Conference> filterConferenceByEndDate(final Date date) {
		final Collection<Conference> conferences = this.getFinalConferences();
		final Collection<Conference> toReturn = new ArrayList<>();

		for (final Conference c : conferences)
			if (c.getEndDate().before(date))
				toReturn.add(c);
		return toReturn;
	}
	public String checkType(final int conferenceId) {
		final Conference res = this.findOne(conferenceId);
		final Date hoy = new Date();
		if (res.getEndDate().before(hoy))
			return "PAST";
		else if (res.getStartDate().after(hoy))
			return "FUTURE";
		else
			return "RUNNING";
	}
	public Double avgConferencePerCategory() {
		return this.conferenceRepository.avgConferencePerCategory();
	}
	public Integer minConferencePerCategory() {
		return this.conferenceRepository.minConferencePerCategory();
	}
	public Integer maxConferencePerCategory() {
		return this.conferenceRepository.maxConferencePerCategory();
	}
	public Double stdDevConferencePerCategory() {
		return this.conferenceRepository.stdDevConferencePerCategory();
	}

	public Conference getConferenceByLoot(final int lootId) {
		return this.conferenceRepository.getConferenceByLoot(lootId);
	}

}
