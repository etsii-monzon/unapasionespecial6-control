
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import domain.Section;
import domain.Tutorial;

@Service
@Transactional
public class SectionService {

	@Autowired
	private SectionRepository		sectionRepository;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private TutorialService			tutorialService;


	public Section create(final int tutorialId) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Section res;
		final Tutorial tut;

		res = new Section();
		tut = this.tutorialService.findOne(tutorialId);
		res.setTutorial(tut);

		return res;
	}

	public Section save(final Section s) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Assert.notNull(s);
		if (!s.getOptionalPictures().isEmpty())
			for (final String url : s.getOptionalPictures())
				Assert.isTrue(ConfigurationService.urlValidator(url), "url");
		final Section res;
		res = this.sectionRepository.save(s);

		return res;
	}
	public void delete(final Section s) {
		Assert.notNull(s);
		this.sectionRepository.delete(s);
	}

	public Section findOne(final int sectionId) {
		Assert.isTrue(this.adminService.checkPrincipal());
		return this.sectionRepository.findOne(sectionId);
	}

	public Collection<Section> findAll() {
		return this.sectionRepository.findAll();
	}

	public Collection<Section> findAllByTutorial(final Tutorial tut) {
		final Collection<Section> aux = this.findAll();
		final Collection<Section> res = new ArrayList<Section>();

		for (final Section s : aux)
			if (s.getTutorial().equals(tut))
				res.add(s);

		return res;
	}
}
