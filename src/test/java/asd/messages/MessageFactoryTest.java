package asd.messages;

import asd.entities.Gameboard.MessageType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;



class MessageFactoryTest {
	MessageFactory testMessageFactory = new MessageFactory();
	private Object MessageTypeNotSupportedException;

	@Test
	void testGetWallMessage() throws MessageTypeNotSupportedException {
		IMessage testMessage = testMessageFactory.getMessage(MessageType.RANINTOWALL);

		assertTrue(testMessage != null);

	}

	@Test
	void testEatenTailMessage() throws MessageTypeNotSupportedException {
		IMessage testMessage = testMessageFactory.getMessage(MessageType.EATENTAIL);

		assertTrue(testMessage != null);

	}

	@Test
	void testUnsupportedException() throws MessageTypeNotSupportedException {

		 assertThrows(MessageTypeNotSupportedException.class, ()->{ testMessageFactory.getMessage(MessageType.NonExistant);});

	}

}
