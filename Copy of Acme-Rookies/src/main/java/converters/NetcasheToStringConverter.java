
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Netcashe;

@Component
@Transactional
public class NetcasheToStringConverter implements Converter<Netcashe, String> {

	@Override
	public String convert(final Netcashe quolet) {
		String result;

		if (quolet == null)
			result = null;
		else
			result = String.valueOf(quolet.getId());

		return result;
	}

}
