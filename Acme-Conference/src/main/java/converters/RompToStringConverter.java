
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Romp;

@Component
@Transactional
public class RompToStringConverter implements Converter<Romp, String> {

	@Override
	public String convert(final Romp romp) {
		String result;

		if (romp == null)
			result = null;
		else
			result = String.valueOf(romp.getId());

		return result;
	}

}
