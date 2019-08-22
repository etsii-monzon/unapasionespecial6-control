
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActivityRepository;
import domain.Activity;

@Component
@Transactional
public class StringToActivityConverter implements Converter<String, Activity> {

	@Autowired
	ActivityRepository	activityRepository;


	@Override
	public Activity convert(final String arg0) {
		Activity res = null;
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
