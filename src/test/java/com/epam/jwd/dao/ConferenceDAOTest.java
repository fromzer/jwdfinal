package com.epam.jwd.dao;

import com.epam.jwd.domain.impl.Conference;
import com.epam.jwd.exception.DaoException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class ConferenceDAOTest {
    private ConferenceDAO conferenceDAO = ConferenceDAO.getInstance();
    private Conference conference = new Conference.Builder()
            .setTitle("Test conference")
            .setDescription("The test conference")
            .setDateStart(LocalDate.of(2022, 01, 20))
            .setDateEnd(LocalDate.of(2022, 02, 20))
            .build();
    private List<Conference> conferences;

    @Test
    public void testCreate() throws DaoException {
        boolean isCreated = conferenceDAO.create(conference);
        conferences = conferenceDAO.findAll();
        assertEquals(isCreated, true);
    }

    @Test
    public void testFindAmountOfConferences() throws DaoException {
        Long count = conferenceDAO.findAmountOfConferences();
        Long size = Long.valueOf(conferences.size());
        assertEquals(count, size);
    }

    @Test
    public void testFindConferenceByLimit() throws DaoException {
        List<Conference> conferenceList = conferenceDAO.findConferenceByLimit(2L, 2L);
        int size = conferenceList.size();
        assertEquals(size, 2);
    }

    @AfterTest
    public void deleteConference() throws DaoException {
        Conference conferenceDelete = conferences.stream()
                .filter(c -> c.getTitle().equals(conference.getTitle()))
                .findFirst()
                .get();
        conferenceDAO.delete(conferenceDelete);
    }
}