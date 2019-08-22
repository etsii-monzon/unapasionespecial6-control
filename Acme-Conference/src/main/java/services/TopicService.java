
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TopicRepository;
import domain.Topic;

@Service
@Transactional
public class TopicService {

	//Managed repository
	@Autowired
	private TopicRepository			topicRepository;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConfigurationService	configurationService;


	//Supporting services

	// SIMPLE CRUD METHODS

	public Topic create() {
		Assert.isTrue(this.administratorService.checkPrincipal());

		Topic topic;
		topic = new Topic();

		return topic;
	}

	public Collection<Topic> findAll() {

		Collection<Topic> topics;
		topics = this.topicRepository.findAll();
		Assert.notEmpty(topics);

		return topics;

	}

	public Topic findOne(final int topicId) {

		Assert.notNull(topicId);
		Topic topic;
		topic = this.topicRepository.findOne(topicId);

		return topic;
	}
	public Topic save(final Topic topic) {

		Assert.notNull(topic);
		Topic res;

		res = this.topicRepository.save(topic);

		if (topic.getId() == 0) {
			this.configurationService.find().getTopics().add(res);
			this.configurationService.save(this.configurationService.find());

		}

		return res;
	}

	public void delete(final Topic topic) {

		this.configurationService.find().getTopics().remove(topic);
		this.configurationService.save(this.configurationService.find());

		this.topicRepository.delete(topic);
	}

}
