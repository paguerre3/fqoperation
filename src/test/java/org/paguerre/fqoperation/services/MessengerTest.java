package org.paguerre.fqoperation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.paguerre.fqoperation.models.RebelSatellite;
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
		RebelSatellite rb1 = new RebelSatellite("kenobi", null, 0.0, new String[] { "este", "", "", "mensaje", "" });
		RebelSatellite rb2 = new RebelSatellite("skywalker", null, 0.0, new String[] { "", "es", "", "", "secreto" });
		RebelSatellite rb3 = new RebelSatellite("sato", null, 0.0, new String[] { "este", "", "un", "", "" });
		String message = messenger.getMessage(rb1.getMessageFromSource(), rb2.getMessageFromSource(),
				rb3.getMessageFromSource());
		assertEquals("este es un mensaje secreto", message);
	}

	@Test
	public void getMessageWithErroneousForms() {
		RebelSatellite rb1 = new RebelSatellite("kenobi", null, 0.0, new String[] { "este", "", "", "mensaje", "" });
		RebelSatellite rb2 = new RebelSatellite("skywalker", null, 0.0, new String[] { "", "es", "", "", "secreto" });
		RebelSatellite rb3 = new RebelSatellite("sato", null, 0.0, new String[] { "este", "", "un", "", "", "" });
		String message = messenger.getMessage(rb1.getMessageFromSource(), rb2.getMessageFromSource(),
				rb3.getMessageFromSource());
		assertEquals("este es un mensaje secreto", message);
	}

	@Test
	public void getMessageWithMoreErroneousFormsAndGaps() {
		RebelSatellite rb1 = new RebelSatellite("kenobi", null, 0.0,
				new String[] { "", "este", "es", "un", "mensaje" });
		RebelSatellite rb2 = new RebelSatellite("skywalker", null, 0.0, new String[] { "este", "", "un", "mensaje" });
		RebelSatellite rb3 = new RebelSatellite("sato", null, 0.0, new String[] { "", "", "es", "", "mensaje" });
		String message = messenger.getMessage(rb1.getMessageFromSource(), rb2.getMessageFromSource(),
				rb3.getMessageFromSource());
		assertEquals("este es un mensaje", message);
	}
}
