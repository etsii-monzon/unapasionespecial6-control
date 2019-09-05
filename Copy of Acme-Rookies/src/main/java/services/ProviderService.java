
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProviderRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.CreditCard;
import domain.Item;
import domain.Provider;
import forms.ProviderForm;

@Service
@Transactional
public class ProviderService {

	//Managed repository
	@Autowired
	private ProviderRepository		providerRepository;

	//Supporting services
	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ItemService				itemService;


	// SIMPLE CRUD METHODS

	public Provider create() {

		Provider p;
		UserAccount userAccount;
		Authority auth;
		CreditCard cCard;

		//Authority
		p = new Provider();
		userAccount = new UserAccount();
		auth = new Authority();
		cCard = new CreditCard();

		auth.setAuthority("PROVIDER");
		userAccount.addAuthority(auth);
		if (this.configurationService.find().isReBrand() == true)
			userAccount.setNotify(true);
		p.setUserAccount(userAccount);
		p.setCreditCard(cCard);

		//Relationships

		final Collection<Item> items = new ArrayList<Item>();
		p.setItems(items);

		return p;
	}
	public Collection<Provider> findAll() {
		Collection<Provider> pros;
		pros = this.providerRepository.findAll();
		Assert.notNull(pros);
		return pros;

	}
	public Provider findOne(final int providerId) {
		Assert.notNull(providerId);
		Provider a;
		a = this.providerRepository.findOne(providerId);
		return a;
	}
	public Provider save(final Provider p) {
		Provider res;

		Assert.notNull(p);
		Assert.isTrue(this.actorService.checkUserEmail(p.getEmail()));

		Assert.isTrue(!p.getUserAccount().getUsername().isEmpty());
		Assert.isTrue(!p.getUserAccount().getPassword().isEmpty());

		if (p.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(p.getUserAccount().getPassword(), null);
			p.getUserAccount().setPassword(hash);
		}
		if (p.getPhoneNumber() != null)
			if (!(p.getPhoneNumber().startsWith("+")))
				p.setPhoneNumber("+" + this.configurationService.find().getCountryCode() + " " + p.getPhoneNumber());

		res = this.providerRepository.save(p);

		return res;
	}
	public void delete(final Provider p) {
		Assert.notNull(p);
		Assert.isTrue(p.getId() != 0);
		Assert.notNull(this.providerRepository.findOne(p.getId()));

		final Collection<Item> toDeleteItems = new ArrayList<Item>();

		//Eliminamos la creditCard asociada
		this.creditCardService.delete(p.getCreditCard());

		//Eliminamos Items

		for (final Item au : p.getItems())
			toDeleteItems.add(au);
		for (final Item au2 : toDeleteItems)
			this.itemService.delete(au2);

		this.providerRepository.delete(p);
	}

	public Provider findByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);
		final Provider res = this.providerRepository.findByUserAccountId(userAccountId);
		return res;
	}

	public Provider findByPrincipal() {
		final UserAccount u = LoginService.getPrincipal();
		final Provider res = this.providerRepository.findByUserAccountId(u.getId());
		return res;
	}

	public void checkPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.PROVIDER);

		Assert.isTrue(authorities.contains(auth));
	}
	public void flush() {
		this.providerRepository.flush();
	}

	public Integer minItemsPerProvider() {
		return this.providerRepository.minItemsPerProvider();
	}

	public Integer maxItemsPerProvider() {
		return this.providerRepository.maxItemsPerProvider();
	}

	public Double avgItemsPerProvider() {
		return this.providerRepository.avgItemsPerProvider();
	}

	public Double stdDevItemsPerProvider() {
		return this.providerRepository.stdDevItemsPerProvider();
	}

	public Collection<String> findTopProviders() {
		final Collection<String> res = new ArrayList<String>();
		final Collection<Provider> top = this.providerRepository.findTopProviders();

		for (final Provider pr : top)
			if (res.size() < 5 && pr.getItems().size() > 0) {
				final String aux = pr.getName() + " " + pr.getSurname();
				res.add(aux);
			}

		return res;

	}


	@Autowired
	private Validator	validator;


	public Provider reconstruct(final ProviderForm providerForm, final BindingResult binding) {
		Provider res;
		if (providerForm.getId() == 0) {
			res = this.create();
			res.setName(providerForm.getName());
			res.setSurname(providerForm.getSurname());
			res.setVat(providerForm.getVat());
			res.setOptionalPhoto(providerForm.getOptionalPhoto());
			res.setPhoneNumber(providerForm.getPhoneNumber());
			res.setEmail(providerForm.getEmail());
			res.setOptionalAddress(providerForm.getOptionalAddress());
			res.setMake(providerForm.getMake());

			res.getCreditCard().setHolderName(providerForm.getCreditCard().getHolderName());
			res.getCreditCard().setBrandName(providerForm.getCreditCard().getBrandName());
			res.getCreditCard().setNumber(providerForm.getCreditCard().getNumber());
			res.getCreditCard().setExpMonth(providerForm.getCreditCard().getExpMonth());
			res.getCreditCard().setExpYear(providerForm.getCreditCard().getExpYear());
			res.getCreditCard().setCvv(providerForm.getCreditCard().getCvv());
			res.getUserAccount().setUsername(providerForm.getUserAccount().getUsername());
			res.getUserAccount().setPassword(providerForm.getUserAccount().getPassword());

		} else {

			res = this.providerRepository.findOne(providerForm.getId());
			res.setName(providerForm.getName());
			res.setSurname(providerForm.getSurname());
			res.setOptionalPhoto(providerForm.getOptionalPhoto());
			res.setPhoneNumber(providerForm.getPhoneNumber());
			res.setEmail(providerForm.getEmail());
			res.setVat(providerForm.getVat());
			res.setOptionalAddress(providerForm.getOptionalAddress());
			res.setMake(providerForm.getMake());

			res.getCreditCard().setHolderName(providerForm.getCreditCard().getHolderName());
			res.getCreditCard().setBrandName(providerForm.getCreditCard().getBrandName());
			res.getCreditCard().setNumber(providerForm.getCreditCard().getNumber());
			res.getCreditCard().setExpMonth(providerForm.getCreditCard().getExpMonth());
			res.getCreditCard().setExpYear(providerForm.getCreditCard().getExpYear());
			res.getCreditCard().setCvv(providerForm.getCreditCard().getCvv());

		}

		this.validator.validate(res, binding);
		return res;
	}
	public ProviderForm deconstruct(final Provider provider) {
		final ProviderForm res = new ProviderForm();

		res.setId(provider.getId());
		res.setName(provider.getName());
		res.setSurname(provider.getSurname());
		res.setVat(provider.getVat());
		res.setOptionalPhoto(provider.getOptionalPhoto());
		res.setPhoneNumber(provider.getPhoneNumber());
		res.setEmail(provider.getEmail());
		res.setOptionalAddress(provider.getOptionalAddress());
		res.setVat(provider.getVat());
		res.setOptionalAddress(provider.getOptionalAddress());
		res.setMake(provider.getMake());

		res.setCreditCard(provider.getCreditCard());

		return res;

	}
}
