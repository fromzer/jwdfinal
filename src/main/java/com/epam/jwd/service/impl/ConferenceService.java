package com.epam.jwd.service.impl;

import com.epam.jwd.dao.ConferenceDAO;
import com.epam.jwd.domain.impl.Conference;
import com.epam.jwd.exception.DaoException;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConferenceService implements Service<Conference> {
    private static final Logger logger = LoggerFactory.getLogger(ConferenceService.class);
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static ConferenceService instance;
    private ConferenceDAO conferenceDAO = ConferenceDAO.getInstance();

    private ConferenceService() {
    }

    public static ConferenceService getInstance() {
        if (!isCreated.get()) {
            reentrantLock.lock();
            try {
                if (instance == null) {
                    instance = new ConferenceService();
                    isCreated.set(true);
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        return instance;
    }

    public List<Conference> findAllConference() throws ServiceException {
        try {
            return conferenceDAO.findAll();
        } catch (DaoException e) {
            logger.warn("Service findAllConference error");
            throw new ServiceException("Service findAllConference error: " + e);
        }
    }

    public Conference findConferenceById(Long id) throws ServiceException {
        try {
            return conferenceDAO.findEntityById(id).orElseThrow();
        } catch (DaoException e) {
            logger.warn("Service findConferenceById error");
            throw new ServiceException("Service findConferenceById error: " + e);
        }
    }

    public Long getNumberOfConferences() throws ServiceException {
        try {
            return conferenceDAO.findAmountOfConferences();
        } catch (DaoException e) {
            logger.warn("Service getNumberOfConferences error");
            throw new ServiceException("Service getNumberOfConferences error: " + e);
        }
    }

    public List<Conference> findConferenceByLimit(Long currentPage, Long recordsPerPage) throws ServiceException {
        Long position = currentPage * recordsPerPage - recordsPerPage;
        try {
            return conferenceDAO.findConferenceByLimit(position, recordsPerPage);
        } catch (DaoException e) {
            logger.warn("Service findConferenceByLimit error");
            throw new ServiceException("Service findConferenceByLimit error: " + e);
        }
    }

    @Override
    public boolean create(Conference conference) throws ServiceException {
        try {
            return conferenceDAO.create(conference);
        } catch (DaoException e) {
            logger.warn("Service createConference error");
            throw new ServiceException("Service createConference error: " + e);
        }
    }

    @Override
    public boolean update(Conference conference) throws ServiceException {
        try {
            return conferenceDAO.update(conference);
        } catch (DaoException e) {
            logger.warn("Service updateConference error");
            throw new ServiceException("Service updateConference error: " + e);
        }
    }

    @Override
    public boolean delete(Conference conference) throws ServiceException {
        try {
            return conferenceDAO.delete(conference);
        } catch (DaoException e) {
            logger.warn("Service deleteConference error");
            throw new ServiceException("Service deleteConference error: " + e);
        }
    }


}
