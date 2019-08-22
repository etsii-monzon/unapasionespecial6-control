
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Conference;
import domain.Finder;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository	finderRepository;

	@Autowired
	private ConferenceService	confService;


	public Finder create() {
		Finder res;

		res = new Finder();

		final Collection<Conference> conferences = new ArrayList<>();

		res.setConferences(conferences);

		return res;
	}

	public Finder findOne(final int finderId) {
		Finder finder;
		finder = this.finderRepository.findOne(finderId);
		return finder;
	}

	public Collection<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	public Finder save(final Finder finder) {
		Finder res;

		res = this.finderRepository.save(finder);

		return res;

	}

	public void delete(final Finder finder) {

		Assert.notNull(finder);
		this.finderRepository.delete(finder);

	}
	public Finder find(final Finder finder) {
		Finder res;

		final Collection<Conference> conferencesFinal = this.confService.getFinalConferences();
		final Collection<Conference> conferences = conferencesFinal;

		if (finder.getKeyword() != null)
			conferences.retainAll(this.confService.searchConferenceByKeyword2(finder.getKeyword()));
		if (finder.getCategory() != null)
			conferences.retainAll(this.confService.getConferencesByCategory(finder.getCategory()));
		if (finder.getFee() != null)
			conferences.retainAll(this.confService.searchConferenceByMaxFee(finder.getFee()));
		if (finder.getStartDate() != null)
			conferences.retainAll(this.confService.filterConferenceByStartDate(finder.getStartDate()));
		if (finder.getEndDate() != null)
			conferences.retainAll(this.confService.filterConferenceByEndDate(finder.getEndDate()));

		finder.setConferences(conferences);

		res = this.finderRepository.save(finder);

		return res;

	}
}
