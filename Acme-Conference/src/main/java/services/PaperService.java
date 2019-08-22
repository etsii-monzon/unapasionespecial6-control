
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PaperRepository;
import domain.Author;
import domain.Paper;

@Service
@Transactional
public class PaperService {

	//Managed repository
	@Autowired
	private PaperRepository	paperRepository;

	@Autowired
	private AuthorService	authorService;


	//Supporting services

	// SIMPLE CRUD METHODS

	public Paper create() {
		Assert.isTrue(this.authorService.checkPrincipal());

		Paper pap;
		pap = new Paper();

		final Collection<Author> authors = new ArrayList<>();

		pap.setAuthors(authors);

		return pap;
	}

	public Collection<Paper> findAll() {

		Collection<Paper> pos;
		pos = this.paperRepository.findAll();
		Assert.notEmpty(pos);

		return pos;

	}

	public Paper findOne(final int paperId) {

		Assert.notNull(paperId);
		Paper pos;
		pos = this.paperRepository.findOne(paperId);

		return pos;
	}
	public Paper save(final Paper a) {

		Assert.notNull(a);
		Paper res;

		res = this.paperRepository.save(a);

		return res;
	}

	public void delete(final Paper a) {

		this.paperRepository.delete(a);
	}

}
