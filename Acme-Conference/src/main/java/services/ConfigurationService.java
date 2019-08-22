
package services;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	//Managed repository
	@Autowired
	private ConfigurationRepository	configurationRepository;

	//Supporting services
	@Autowired
	private AdministratorService	administratorService;


	// SIMPLE CRUD METHODS

	public Configuration create() {

		Configuration conf;
		conf = new Configuration();

		return conf;
	}
	public Configuration save(final Configuration c) {

		Assert.notNull(c);
		Assert.isTrue(this.administratorService.checkPrincipal());

		final Configuration result = this.configurationRepository.save(c);
		Assert.notNull(result);
		System.out.println(result.getId());
		return result;

	}

	public Collection<Configuration> findAll() {
		Assert.isTrue(this.administratorService.checkPrincipal());

		final Collection<Configuration> res = this.configurationRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public String createTicker() {

		final String secuencia = "0123456789ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";

		final char c1 = secuencia.charAt((int) (Math.random() * (secuencia.length())));
		final char c2 = secuencia.charAt((int) (Math.random() * (secuencia.length())));
		final char c3 = secuencia.charAt((int) (Math.random() * (secuencia.length())));

		final char c4 = secuencia.charAt((int) (Math.random() * (secuencia.length())));

		return c1 + "" + c2 + "" + c3 + "" + c4;

	}

	public String findWelcomeSP() {
		return this.configurationRepository.findWelcomeSP();
	}

	public String findWelcomeEN() {
		return this.configurationRepository.findWelcomeEN();
	}
	public String findSystemName() {
		return this.configurationRepository.findSystemName();
	}

	public Configuration find() {
		Configuration result;

		result = this.configurationRepository.findAll().get(0);
		Assert.notNull(result);

		return result;

	}

	public static boolean isNumeric(final String cadena) {

		boolean resultado;

		try {
			Integer.parseInt(cadena);
			resultado = true;
		} catch (final NumberFormatException excepcion) {
			resultado = false;
		}

		return resultado;
	}

	public static boolean urlValidator(String url) {
		/* validación de url */
		try {
			new URL(url).toURI();
			return true;
		} catch (URISyntaxException exception) {
			return false;
		} catch (MalformedURLException exception) {
			return false;
		}
	}

}
