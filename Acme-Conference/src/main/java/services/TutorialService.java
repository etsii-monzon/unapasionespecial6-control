
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import domain.Conference;
import domain.Section;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	@Autowired
	private TutorialRepository		tutorialRepository;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private ConferenceService		confService;

	@Autowired
	private SectionService			sectService;


	public Tutorial create(final int conferenceId) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Tutorial res;
		Conference conf;

		conf = this.confService.findOne(conferenceId);
		res = new Tutorial();

		res.setConference(conf);

		return res;
	}

	public Tutorial save(final Tutorial s) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Assert.notNull(s);
		final Tutorial res;

		if (!s.getOptionalAttachments().isEmpty())
			for (final String url : s.getOptionalAttachments())
				Assert.isTrue(ConfigurationService.urlValidator(url), "url");

		res = this.tutorialRepository.save(s);
		this.tutorialRepository.flush();
		return res;
	}

	public Tutorial findOne(final int tutorialId) {
		//		Assert.isTrue(this.adminService.checkPrincipal());
		return this.tutorialRepository.findOne(tutorialId);
	}

	public Collection<Tutorial> findAll() {
		//		Assert.isTrue(this.adminService.checkPrincipal());
		return this.tutorialRepository.findAll();
	}

	public void delete(final Tutorial t) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Assert.notNull(t);
		final Collection<Section> sections = this.sectService.findAllByTutorial(t);
		for (final Section s : sections)
			this.sectService.delete(s);
		this.tutorialRepository.delete(t);

	}
	public Collection<Tutorial> findAllTutorialsByConference(final Conference conf) {
		final Collection<Tutorial> res = new ArrayList<Tutorial>();
		final Collection<Tutorial> aux = this.findAll();

		for (final Tutorial act : aux)
			if (act.getConference().equals(conf))
				res.add(act);

		return res;
	}

}
