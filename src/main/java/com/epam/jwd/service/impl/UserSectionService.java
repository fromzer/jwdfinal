package com.epam.jwd.service.impl;

import com.epam.jwd.dao.UserSectionDAO;
import com.epam.jwd.domain.impl.RequestSection;
import com.epam.jwd.domain.impl.Section;
import com.epam.jwd.domain.impl.UserSection;
import com.epam.jwd.exception.DaoException;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class UserSectionService implements Service<UserSection> {
    private static final Logger logger = LoggerFactory.getLogger(UserSectionService.class);
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static UserSectionService instance;
    private UserSectionDAO userSectionDAO = UserSectionDAO.getInstance();
    private SectionService sectionService = SectionService.getInstance();
    private ConferenceService conferenceService = ConferenceService.getInstance();
    private UserService userService = UserService.getInstance();

    private UserSectionService() {
    }

    public static UserSectionService getInstance() {
        if (!isCreated.get()) {
            reentrantLock.lock();
            try {
                if (instance == null) {
                    instance = new UserSectionService();
                    isCreated.set(true);
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        return instance;
    }

    public List<UserSection> findUserBySectionId(Long id) throws ServiceException {
        try {
            return userSectionDAO.findBySectionId(id);
        } catch (DaoException e) {
            logger.warn("Service findUserBySectionId error");
            throw new ServiceException("Service findUserBySectionId error: " + e);
        }
    }

    public List<UserSection> findSectionByUserId(Long id) throws ServiceException {
        try {
            return userSectionDAO.findByUserId(id);
        } catch (DaoException e) {
            logger.warn("Service findSectionByUserId error");
            throw new ServiceException("Service findSectionByUserId error: " + e);
        }
    }

    public UserSection findUserSectionById(Long id) throws ServiceException {
        try {
            return userSectionDAO.findEntityById(id).orElseThrow();
        } catch (DaoException e) {
            logger.warn("Service findUserSectionById error");
            throw new ServiceException("Service findUserSectionById error: " + e);
        }
    }

    public List<RequestSection> findAllUserRequests(Long id) throws ServiceException {
        List<RequestSection> requestSectionList = new ArrayList<>();
        List<UserSection> userSectionList;
        try {
            userSectionList = userSectionDAO.findByUserId(id);
        } catch (DaoException e) {
            logger.warn("Service findAllUserRequests error");
            throw new ServiceException("Service findAllUserRequests error: " + e);
        }
        for (UserSection userSection : userSectionList) {
            Section section = sectionService.findSectionById(userSection.getSectionId());
            RequestSection requestSection = new RequestSection.Builder()
                    .withConference(conferenceService.findConferenceById(section.getConferenceId()))
                    .withSection(section)
                    .withUserSection(userSection)
                    .build();
            requestSectionList.add(requestSection);
        }
        return requestSectionList;
    }

    public List<RequestSection> findNewRequests() throws ServiceException {
        List<RequestSection> requestSectionList = new ArrayList<>();
        List<UserSection> userSectionList;
        try {
            userSectionList = userSectionDAO.findAll();
        } catch (DaoException e) {
            logger.warn("Service findNewRequests error");
            throw new ServiceException("Service findNewRequests error: " + e);
        }
        for (UserSection userSection : userSectionList) {
            Section section = sectionService.findSectionById(userSection.getSectionId());
            RequestSection requestSection = new RequestSection.Builder()
                    .withConference(conferenceService.findConferenceById(section.getConferenceId()))
                    .withSection(section)
                    .withUserSection(userSection)
                    .withUser(userService.findUserById(userSection.getUserId()))
                    .build();
            requestSectionList.add(requestSection);
        }
        return requestSectionList;
    }

    public List<Long> findRequestsByUserId(Long id) throws ServiceException {
        List<UserSection> userSectionList = findSectionByUserId(id);
        List<Long> sectionIdList = new ArrayList<>();
        for (UserSection userSection : userSectionList) {
            sectionIdList.add(userSection.getSectionId());
        }
        return sectionIdList;
    }

    @Override
    public boolean create(UserSection userSection) throws ServiceException {
        try {
            return userSectionDAO.create(userSection);
        } catch (DaoException e) {
            logger.warn("Service createUserSection error");
            throw new ServiceException("Service createUserSection error: " + e);
        }
    }

    @Override
    public boolean update(UserSection userSection) throws ServiceException {
        try {
            return userSectionDAO.update(userSection);
        } catch (DaoException e) {
            logger.warn("Service createUserSection error");
            throw new ServiceException("Service createUserSection error: " + e);
        }
    }

    @Override
    public boolean delete(UserSection userSection) throws ServiceException {
        try {
            return userSectionDAO.delete(userSection);
        } catch (DaoException e) {
            logger.warn("Service createUserSection error");
            throw new ServiceException("Service createUserSection error: " + e);
        }
    }
}
