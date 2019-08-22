
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PresentationRepository;
import domain.Conference;
import domain.Presentation;

@Service
@Transactional
public class PresentationService {

	@Autowired
	private PresentationRepository	presentRepository;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private ConferenceService		confService;


	public Presentation create(final int conferenceId) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Presentation res;
		Conference conf;

		conf = this.confService.findOne(conferenceId);
		res = new Presentation();
		res.setConference(conf);

		return res;
	}

	public Presentation save(final Presentation s) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Assert.notNull(s);
		final Presentation res;

		if (!s.getOptionalAttachments().isEmpty())
			for (final String url : s.getOptionalAttachments())
				Assert.isTrue(ConfigurationService.urlValidator(url), "url");

		res = this.presentRepository.save(s);
		return res;
	}

	public Presentation findOne(final int presentationId) {
		//		Assert.isTrue(this.adminService.checkPrincipal());
		return this.presentRepository.findOne(presentationId);
	}

	public Collection<Presentation> findAll() {
		return this.presentRepository.findAll();
	}

	public void delete(final Presentation t) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Assert.notNull(t);
		this.presentRepository.delete(t);

	}

	public Collection<Presentation> findAllPresentationsByConference(final Conference conf) {
		final Collection<Presentation> res = new ArrayList<Presentation>();
		final Collection<Presentation> aux = this.findAll();

		for (final Presentation act : aux)
			if (act.getConference().equals(conf))
				res.add(act);

		return res;
	}

}
