
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PresentationRepository;
import domain.Presentation;

@Component
@Transactional
public class StringToPresentationConverter implements Converter<String, Presentation> {

	@Autowired
	PresentationRepository	activityRepository;


	@Override
	public Presentation convert(final String arg0) {
		Presentation res = null;
		int id;

		try {
			if (StringUtils.isEmpty(arg0))
				res = null;
			else {
				id = Integer.valueOf(arg0);
				System.out.println(id);

				res = this.activityRepository.findOne(id);

				System.out.println(res);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
