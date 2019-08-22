
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Category;
import domain.Conference;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository		categoryRepository;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private ConferenceService		conferenceService;


	public Category create() {
		Assert.isTrue(this.adminService.checkPrincipal());
		Category category;

		category = new Category();

		final Collection<Category> children = new ArrayList<>();

		category.setChildren(children);

		return category;
	}

	public Category findOne(final int categoryId) {
		//		Assert.isTrue(this.adminService.checkPrincipal());
		Category category;
		category = this.categoryRepository.findOne(categoryId);
		return category;
	}

	public Collection<Category> findAll() {
		return this.categoryRepository.findAll();
	}

	public Category save(final Category category) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Assert.notNull(category.getParent());
		Category res;

		if (category.getId() != 0)
			if (category.getParent() != this.categoryRepository.findOne(category.getId()))
				this.categoryRepository.findOne(category.getId()).getParent().getChildren().remove(category);

		res = this.categoryRepository.save(category);

		if (!category.getParent().getChildren().contains(category))
			category.getParent().getChildren().add(res);

		return res;

	}
	public void delete(final Category category) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Assert.notNull(category.getParent());
		Assert.notNull(category);

		if (!category.getChildren().isEmpty())
			for (final Category child : category.getChildren())
				child.setParent(category.getParent());

		for (final Conference c : this.conferenceService.getConferencesByCategory(category))
			c.setCategory(category.getParent());

		category.getParent().getChildren().addAll(category.getChildren());

		category.getParent().getChildren().remove(category);

		this.categoryRepository.delete(category);

	}

	public Collection<Category> getAllFamily(final Category c, final Collection<Category> tree) {
		for (final Category child : c.getChildren()) {
			tree.add(child);
			if (!child.getChildren().isEmpty())
				this.getAllFamily(child, tree);

		}
		return tree;
	}

}
