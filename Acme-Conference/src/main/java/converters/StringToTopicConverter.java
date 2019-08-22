
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.TopicRepository;
import domain.Topic;

@Component
@Transactional
public class StringToTopicConverter implements Converter<String, Topic> {

	@Autowired
	private TopicRepository	topicRepository;


	@Override
	public Topic convert(final String text) {
		Topic result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.topicRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
