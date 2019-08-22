
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PanelRepository;
import domain.Panel;

@Component
@Transactional
public class StringToPanelConverter implements Converter<String, Panel> {

	@Autowired
	PanelRepository	activityRepository;


	@Override
	public Panel convert(final String arg0) {
		Panel res = null;
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
