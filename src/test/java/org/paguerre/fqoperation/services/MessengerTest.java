package org.paguerre.fqoperation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.paguerre.fqoperation.models.Satellite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MessengerTest {

	@Autowired
	Messenger messenger;

	@Test
	public void getMessagesNullOrEmpty() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> messenger.getMessage((String[][]) null),
				"Expected getMessage() to throw IllegalArgumentException -> 'All messagers are null', but it didn't");
		assertEquals("All messagers are null.", thrown.getMessage());
	}

	@Test
	public void getMessageFromFQOperation() {
		Satellite rb1 = new Satellite("kenobi", null, 0.0, new String[] { "este", "", "", "mensaje", "" });
		Satellite rb2 = new Satellite("skywalker", null, 0.0, new String[] { "", "es", "", "", "secreto" });
		Satellite rb3 = new Satellite("sato", null, 0.0, new String[] { "este", "", "un", "", "" });
		String message = messenger.getMessage(rb1.getMessage(), rb2.getMessage(), rb3.getMessage());
		assertEquals("este es un mensaje secreto", message);
	}

	@Test
	public void getMessageWithErroneousForms() {
		Satellite rb1 = new Satellite("kenobi", null, 0.0, new String[] { "este", "", "", "mensaje", "" });
		Satellite rb2 = new Satellite("skywalker", null, 0.0, new String[] { "", "es", "", "", "secreto" });
		Satellite rb3 = new Satellite("sato", null, 0.0, new String[] { "este", "", "un", "", "" });
		String message = messenger.getMessage(rb1.getMessage(), rb2.getMessage(), rb3.getMessage());
		assertEquals("este es un mensaje secreto", message);
	}

	@Test
	public void getMessageWithMoreErroneousFormsAndLowerArrayGaps() {
		Satellite rb1 = new Satellite("kenobi", null, 0.0, new String[] { "", "este", "es", "un", "mensaje" });
		Satellite rb2 = new Satellite("skywalker", null, 0.0, new String[] { "este", "", "un", "mensaje" });
		Satellite rb3 = new Satellite("sato", null, 0.0, new String[] { "", "", "es", "", "mensaje" });
		String message = messenger.getMessage(rb1.getMessage(), rb2.getMessage(), rb3.getMessage());
		assertEquals("este es un mensaje", message);
	}

	@Test
	public void getMessageWithMoreErroneousFormsAndLargerArrayGaps() {
		Satellite rb1 = new Satellite("kenobi", null, 0.0, new String[] { "este", "", "", "mensaje", "" });
		Satellite rb2 = new Satellite("skywalker", null, 0.0, new String[] { "", "es", "", "", "secreto" });
		Satellite rb3 = new Satellite("sato", null, 0.0, new String[] { "este", "", "un", "", "", "" });
		String message = messenger.getMessage(rb1.getMessage(), rb2.getMessage(), rb3.getMessage());
		assertEquals("este es un mensaje secreto", message);
	}
}
