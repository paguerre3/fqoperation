package org.paguerre.fqoperation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
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
				"Expected getMessage() to throw IllegalArgumentException -> 'All messagers are null or empty', but it didn't");
		assertEquals("All messagers are null or empty.", thrown.getMessage());
	}
}
