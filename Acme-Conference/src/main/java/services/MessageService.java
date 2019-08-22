
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Author;
import domain.Message;
import domain.Submission;

@Service
@Transactional
public class MessageService {

	//Managed repository
	@Autowired
	private MessageRepository		messageRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AuthorService			authorService;


	//Supporting services

	// SIMPLE CRUD METHODS

	public Message create() {
		Message m;

		m = new Message();

		final java.util.Date fechaActual = new java.util.Date();

		m.setDate(fechaActual);
		m.setSender(this.actorService.findByPrincipal());

		return m;
	}
	public Collection<Message> findAll() {
		Collection<Message> messages;
		messages = this.messageRepository.findAll();
		Assert.notNull(messages);
		return messages;

	}
	public Message findOne(final int messageId) {
		Assert.notNull(messageId);
		Message c;
		c = this.messageRepository.findOne(messageId);
		return c;
	}
	public Message save(final Message a) {

		Assert.notNull(a);
		final Message res;

		res = this.messageRepository.save(a);

		//Mensaje se guarada en vensajes de sender
		a.getSender().getMessages().add(res);

		//Mensaje se guarda en mensajes de recipients
		for (final Actor actor : a.getRecipients())
			actor.getMessages().add(res);

		return res;
	}
	public void delete(final Message message) {

		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);

		final Collection<Actor> actoresSendReci = new ArrayList<Actor>();
		final Collection<Actor> actoresConMensaje = new ArrayList<Actor>();

		actoresSendReci.add(message.getSender());
		actoresSendReci.addAll(message.getRecipients());

		for (final Actor ac : actoresSendReci)
			if (ac.getMessages().contains(message))
				actoresConMensaje.add(ac);

		for (final Actor a : actoresConMensaje)
			if (a.equals(this.actorService.findByPrincipal()))
				a.getMessages().remove(message);
		actoresConMensaje.remove(this.actorService.findByPrincipal());

		if (actoresConMensaje.isEmpty())
			this.messageRepository.delete(message);

	}
	public Message broadcast() {
		Assert.isTrue(this.administratorService.checkPrincipal());
		Message m;

		m = new Message();

		final java.util.Date fechaActual = new java.util.Date();

		m.setDate(fechaActual);
		m.setSender(this.actorService.findByPrincipal());
		final Collection<Actor> recipients = this.actorService.findAll();
		recipients.remove(this.actorService.findByPrincipal());
		m.setRecipients(recipients);

		return m;
	}

	public Message messAuthors() {
		Assert.isTrue(this.administratorService.checkPrincipal());
		Message m;

		m = this.create();
		final Collection<Actor> recipients = new ArrayList<>();
		for (final Author au : this.authorService.findAll())
			recipients.add(au);

		m.setRecipients(recipients);
		return m;
	}
	public Message messAuthorsSub() {
		Assert.isTrue(this.administratorService.checkPrincipal());
		final Message m;

		m = this.create();

		final Collection<Actor> recipients = new ArrayList<>();
		//Cogemos todos los autores que tienen submisions
		for (final Author au : this.authorService.findAll())
			if (!au.getSubmissions().isEmpty())
				recipients.add(au);
		m.setRecipients(recipients);

		return m;
	}

	public Message messAuthorsRegistrations() {
		Assert.isTrue(this.administratorService.checkPrincipal());
		final Message m;

		m = this.create();

		final Collection<Actor> recipients = new ArrayList<>();
		//Cogemos todos los autores que tienen registrations
		for (final Author au : this.authorService.findAll())
			if (!au.getRegistrations().isEmpty())
				recipients.add(au);
		m.setRecipients(recipients);

		return m;
	}

	public void notificationDecision(final Submission submission) {
		Assert.isTrue(this.administratorService.checkPrincipal());
		final Message notificacion;

		notificacion = this.create();

		final Actor recipient = this.authorService.findAuthorBySubmissionId(submission.getId());
		final Collection<Actor> recipients = new ArrayList<>();
		recipients.add(recipient);
		notificacion.setRecipients(recipients);
		notificacion.setSubject("SUBMISSION TO " + submission.getConference().getTitle() + " IS: " + submission.getStatus());
		notificacion.setBody("This message is a simple notification of the decision of your submission. You can consult the report.");
		notificacion.setTopic("OTHER");

		this.save(notificacion);

	}
}
