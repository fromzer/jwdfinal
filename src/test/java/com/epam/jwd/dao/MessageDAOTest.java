package com.epam.jwd.dao;

import com.epam.jwd.domain.impl.Message;
import com.epam.jwd.exception.DaoException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class MessageDAOTest {
    MessageDAO messageDAO = MessageDAO.getInstance();
    private Message message = new Message.Builder()
            .setTopic("Hello")
            .setDescription("Hello world!")
            .setUserId(2l)
            .build();
    private Message messageSelect;
    private List<Message> messages;

    @Test
    public void testCreate() throws DaoException {
        boolean isCreated = messageDAO.create(message);
        assertEquals(isCreated, true);
    }

    @Test
    public void testFindAllMessageByUserId() throws DaoException {
        messages = messageDAO.findAllMessageByUserId(2l);
        messageSelect = messages.stream()
                .filter(m -> m.getTopic().equals(message.getTopic()))
                .findFirst()
                .get();
        assertEquals(messageSelect.getUserId(), message.getUserId());
    }

    @AfterTest
    public void deleteMessage() throws DaoException {
        messageDAO.delete(messageSelect);
    }
}