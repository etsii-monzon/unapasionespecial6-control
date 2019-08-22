/*
 * SampleTest.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Message;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	// Tests ------------------------------------------------------------------

	// The following are fictitious test cases that are intended to check that 
	// JUnit works well in this project.  Just righ-click this class and run 
	// it using JUnit.

	/*
	 * Test comprobación intercambio de mensajes entre actores.
	 * Req Funcional: 12.3
	 */
	@Test
	public void testSendMessage() {
		super.authenticate("author1");

		final Collection<Actor> recipients = new ArrayList<>();
		final Message res = this.messageService.create();
		Assert.notNull(res);

		res.setSubject("IMPORTANTE");
		res.setBody("TEST");

		final Actor recipient = this.actorService.findOne(super.getEntityId("reviewer1"));
		recipients.add(recipient);
		res.setRecipients(recipients);

		res.setTopic("OTHERS");

		final Message result = this.messageService.save(res);

		Assert.notNull(result);
		Assert.isTrue(this.messageService.findAll().contains(result));
		Assert.isTrue(this.actorService.findByPrincipal().getMessages().contains(result));
		Assert.isTrue(recipient.getMessages().contains(result));

		super.unauthenticate();

	}

	/*
	 * Test listar mensajes actores.
	 * Req Funcional: 12.4
	 */
	@Test
	public void testListMessages() {
		super.authenticate("author1");

		final Collection<Message> res = this.actorService.findByPrincipal().getMessages();

		Assert.notNull(res);

		super.unauthenticate();

	}

	/*
	 * Test eliminar un mensaje mensaje actor y que siga en la bandeja de los restantes.
	 * Req Funcional: 12.4
	 */
	@Test
	public void testDeleteMessage() {
		super.authenticate("author1");

		final Message res = this.messageService.findOne(super.getEntityId("message1"));
		Assert.notNull(res);
		this.messageService.delete(res);

		//Comprobamos que el actor que ha eliminado el mensaje ya no lo tiene
		Assert.isTrue(!this.actorService.findByPrincipal().getMessages().contains(res));
		//Comprobamos que el mensaje sigue estando en la base de datos,ya que un recipient aún lo tiene
		Assert.isTrue(this.messageService.findAll().contains(res));

		super.unauthenticate();

	}

	/*
	 * Test enviar mensaje a todos los actores como administrador(Broadcast).
	 * Req Funcional: 14.7
	 */
	@Test
	public void testBroadcastMessage() {
		super.authenticate("admin");

		final Message res = this.messageService.broadcast();
		Assert.notNull(res);

		res.setSubject("IMPORTANTE");
		res.setBody("TEST");
		res.setTopic("OTHERS");

		final Message result = this.messageService.save(res);
		Assert.notNull(result);

		Assert.isTrue(this.messageService.findAll().contains(result));
		Assert.isTrue(this.actorService.findByPrincipal().getMessages().contains(result));
		final Actor recipient = this.actorService.findOne(super.getEntityId("reviewer1"));
		Assert.isTrue(recipient.getMessages().contains(result));

		super.unauthenticate();

	}

	/*
	 * Test enviar mensaje a todos los AUTORES como administrador(Broadcast).
	 * Req Funcional: 14.7
	 */
	@Test
	public void testBroadcastAuthorsMessage() {
		super.authenticate("admin");

		final Message res = this.messageService.messAuthors();
		Assert.notNull(res);

		res.setSubject("IMPORTANTE");
		res.setBody("TEST");
		res.setTopic("OTHERS");

		final Message result = this.messageService.save(res);
		Assert.notNull(result);

		Assert.isTrue(this.messageService.findAll().contains(result));
		Assert.isTrue(this.actorService.findByPrincipal().getMessages().contains(result));
		final Actor recipient = this.actorService.findOne(super.getEntityId("author1"));
		Assert.isTrue(recipient.getMessages().contains(result));

		super.unauthenticate();

	}

	/*
	 * Test enviar mensaje a todos los AUTORES que hayan entregado en una conferencia como administrador(Broadcast).
	 * Req Funcional: 14.7
	 */
	@Test
	public void testBroadcastAuthorsSubmissionMessage() {
		super.authenticate("admin");

		final Message res = this.messageService.messAuthorsSub();
		Assert.notNull(res);

		res.setSubject("IMPORTANTE");
		res.setBody("TEST");
		res.setTopic("OTHERS");

		final Message result = this.messageService.save(res);
		Assert.notNull(result);

		Assert.isTrue(this.messageService.findAll().contains(result));
		Assert.isTrue(this.actorService.findByPrincipal().getMessages().contains(result));
		final Actor recipient = this.actorService.findOne(super.getEntityId("author1"));
		Assert.isTrue(recipient.getMessages().contains(result));

		super.unauthenticate();

	}

	/*
	 * Test enviar mensaje a todos los AUTORES que hayan registrado en una conferencia como administrador(Broadcast).
	 * Req Funcional: 14.7
	 */
	@Test
	public void testBroadcastAuthorsRegistrationMessage() {
		super.authenticate("admin");

		final Message res = this.messageService.messAuthorsRegistrations();
		Assert.notNull(res);

		res.setSubject("IMPORTANTE");
		res.setBody("TEST");
		res.setTopic("OTHERS");

		final Message result = this.messageService.save(res);
		Assert.notNull(result);

		Assert.isTrue(this.messageService.findAll().contains(result));
		Assert.isTrue(this.actorService.findByPrincipal().getMessages().contains(result));
		final Actor recipient = this.actorService.findOne(super.getEntityId("author1"));
		Assert.isTrue(recipient.getMessages().contains(result));

		super.unauthenticate();

	}

}
