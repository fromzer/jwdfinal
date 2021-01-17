package com.epam.jwd.service.impl;

import com.epam.jwd.dao.SectionDAO;
import com.epam.jwd.domain.impl.Section;
import com.epam.jwd.exception.DaoException;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class SectionService implements Service<Section> {
    private static final Logger logger = LoggerFactory.getLogger(SectionService.class);
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static SectionService instance;
    private SectionDAO sectionDAO = SectionDAO.getInstance();

    private SectionService() {
    }

    public static SectionService getInstance() {
        if (!isCreated.get()) {
            reentrantLock.lock();
            try {
                if (instance == null) {
                    instance = new SectionService();
                    isCreated.set(true);
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        return instance;
    }

    public List<Section> findAllSection() throws ServiceException {
        try {
            return sectionDAO.findAll();
        } catch (DaoException e) {
            logger.warn("Service findAllSection error");
            throw new ServiceException("Service findAllSection error: " + e);
        }
    }

    public Section findSectionById(Long id) throws ServiceException {
        try {
            return sectionDAO.findEntityById(id).orElseThrow();
        } catch (DaoException e) {
            logger.warn("Service findSectionById error");
            throw new ServiceException("Service findSectionById error: " + e);
        }
    }

    public List<Section> findSectionByConferenceId(Long id) throws ServiceException {
        try {
            return sectionDAO.findEntityByConferenceId(id);
        } catch (DaoException e) {
            logger.warn("Service findSectionByConferenceId error");
            throw new ServiceException("Service findSectionByConferenceId error: " + e);
        }
    }

    @Override
    public boolean create(Section section) throws ServiceException {
        try {
            return sectionDAO.create(section);
        } catch (DaoException e) {
            logger.warn("Service createSection error");
            throw new ServiceException("Service createSection error: " + e);
        }
    }

    @Override
    public boolean update(Section section) throws ServiceException {
        try {
            return sectionDAO.update(section);
        } catch (DaoException e) {
            logger.warn("Service updateSection error");
            throw new ServiceException("Service updateSection error: " + e);
        }
    }

    @Override
    public boolean delete(Section section) throws ServiceException {
        try {
            return sectionDAO.delete(section);
        } catch (DaoException e) {
            logger.warn("Service deleteSection error");
            throw new ServiceException("Service deleteSection error: " + e);
        }
    }
}
