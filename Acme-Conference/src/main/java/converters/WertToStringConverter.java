
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Wert;

@Component
@Transactional
public class WertToStringConverter implements Converter<Wert, String> {

	@Override
	public String convert(final Wert wert) {
		String result;

		if (wert == null)
			result = null;
		else
			result = String.valueOf(wert.getId());

		return result;
	}

}
