package com.epam.jwd.dao;

import com.epam.jwd.domain.impl.Conference;
import com.epam.jwd.domain.impl.Section;
import com.epam.jwd.exception.DaoException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;

import static org.testng.Assert.*;

public class SectionDAOTest {
    private SectionDAO sectionDAO = SectionDAO.getInstance();
    private ConferenceDAO conferenceDAO = ConferenceDAO.getInstance();
    private Conference conference = new Conference.Builder()
            .setTitle("Test conference")
            .setDescription("The test conference")
            .setDateStart(LocalDate.of(2022, 01, 20))
            .setDateEnd(LocalDate.of(2022, 02, 20))
            .build();
    private List<Conference> conferences;
    private Conference conferenceTest;
    private Section section;
    @Test
    public void testFindEntityByConferenceId() {
    }

    @Test
    public void testCreate() throws DaoException {
        conferenceDAO.create(conference);
        conferences = conferenceDAO.findAll();
        conferenceTest = conferences.stream()
                .filter(c -> c.getTitle().equals(conference.getTitle()))
                .findFirst()
                .get();
        section = new Section.Builder()
                .setTitle("Test section")
                .setDescription("The test Section")
                .setConferenceId(conferenceTest.getId())
                .build();
        boolean isCreated = sectionDAO.create(section);
        assertEquals(isCreated, true);
    }

    @Test
    public void testUpdate() throws DaoException {
        List<Section> entityByConferenceId = sectionDAO.findEntityByConferenceId(conferenceTest.getId());
        Section sectionUpd = new Section.Builder()
                .setId(entityByConferenceId.get(0).getId())
                .setTitle("Update section")
                .setDescription("Hello test!")
                .build();
        boolean isUpdated = sectionDAO.update(sectionUpd);
        assertEquals(isUpdated, true);
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