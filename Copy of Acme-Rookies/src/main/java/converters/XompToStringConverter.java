
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Xomp;

@Component
@Transactional
public class XompToStringConverter implements Converter<Xomp, String> {

	@Override
	public String convert(final Xomp quolet) {
		String result;

		if (quolet == null)
			result = null;
		else
			result = String.valueOf(quolet.getId());

		return result;
	}

}
