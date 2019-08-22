
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Panel;

@Component
@Transactional
public class PanelToStringConverter implements Converter<Panel, String> {

	@Override
	public String convert(final Panel activity) {
		String result;

		if (activity == null)
			result = null;
		else
			result = String.valueOf(activity.getId());

		return result;
	}

}
