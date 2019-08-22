
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PaperRepository;
import domain.Paper;

@Component
@Transactional
public class StringToPaperConverter implements Converter<String, Paper> {

	@Autowired
	private PaperRepository	paperRepository;


	@Override
	public Paper convert(final String text) {
		Paper result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.paperRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
