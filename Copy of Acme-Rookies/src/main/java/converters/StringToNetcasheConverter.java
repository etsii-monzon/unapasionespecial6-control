
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.NetcasheRepository;
import domain.Netcashe;

@Component
@Transactional
public class StringToNetcasheConverter implements Converter<String, Netcashe> {

	@Autowired
	NetcasheRepository	quoletRepository;


	@Override
	public Netcashe convert(final String text) {
		Netcashe result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.quoletRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
