
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Loot;

@Component
@Transactional
public class LootToStringConverter implements Converter<Loot, String> {

	@Override
	public String convert(final Loot wert) {
		String result;

		if (wert == null)
			result = null;
		else
			result = String.valueOf(wert.getId());

		return result;
	}

}
