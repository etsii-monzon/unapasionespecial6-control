
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Presentation;

@Component
@Transactional
public class PresentationToStringConverter implements Converter<Presentation, String> {

	@Override
	public String convert(final Presentation activity) {
		String result;

		if (activity == null)
			result = null;
		else
			result = String.valueOf(activity.getId());

		return result;
	}

}
