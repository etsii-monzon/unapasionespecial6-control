
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ItemRepository;
import domain.Item;
import domain.Provider;

@Service
@Transactional
public class ItemService {

	//Managed repository
	@Autowired
	private ItemRepository	itemRepository;

	//Supporting services
	@Autowired
	private ProviderService	providerService;


	// SIMPLE CRUD METHODS

	public Item create() {
		this.providerService.checkPrincipal();
		Item item;
		item = new Item();

		return item;
	}

	public Collection<Item> findAll() {

		Collection<Item> items;
		items = this.itemRepository.findAll();

		return items;

	}
	public Item findOne(final int itemId) {

		Assert.notNull(itemId);
		Item item;
		item = this.itemRepository.findOne(itemId);

		return item;
	}
	public Item save(final Item a) {
		this.providerService.checkPrincipal();

		Assert.notNull(a);
		final Item res;

		final Provider b = this.providerService.findByPrincipal();
		final Collection<Item> f = b.getItems();
		res = this.itemRepository.save(a);
		if (!f.contains(res))
			f.add(res);

		return res;
	}
	public void delete(final Item p) {
		this.providerService.checkPrincipal();

		Assert.notNull(p);
		Assert.isTrue(p.getId() != 0);

		final Provider prov = this.providerService.findByPrincipal();

		prov.getItems().remove(p);

		this.itemRepository.delete(p);
	}

}
