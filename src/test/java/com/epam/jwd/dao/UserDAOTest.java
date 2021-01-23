package com.epam.jwd.dao;

import com.epam.jwd.domain.impl.User;
import com.epam.jwd.domain.impl.UserRole;
import com.epam.jwd.exception.DaoException;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserDAOTest {
    private UserDAO userDAO = UserDAO.getInstance();
    private User userCreateDelete = new User.Builder()
            .setLogin("TestNg")
            .setFirstName("Test")
            .setLastName("NG")
            .setPassword("test")
            .setEmail("test@ng.com")
            .setRole(UserRole.USER)
            .build();
    private User userFind = userDAO.findEntityByLogin("user").get();;
    private User updateUser = new User.Builder()
            .setId(userFind.getId())
            .setLogin("TestNg")
            .setFirstName("TestUpdate")
            .setLastName("Update")
            .setEmail("test@ng.com")
            .setPassword("testNG")
            .setRole(UserRole.USER)
            .build();
    private String psw;


    public UserDAOTest() throws DaoException {
    }

    @Test
    public void testFindEntityByLogin() throws DaoException {
        assertEquals(userFind.getLogin(), "user");
    }

    @Test
    public void testUpdate() throws DaoException {
        boolean isUpdated = userDAO.update(updateUser);
        assertEquals(isUpdated, true);
    }

    @Test
    public void testUpdateWithoutPassword() throws DaoException {
        User updateUserWP = new User.Builder()
                .setId(userFind.getId())
                .setLogin("user")
                .setFirstName("TestUpdate")
                .setLastName("WithoutPassword")
                .setEmail("test@ngtest.com")
                .setRole(UserRole.USER)
                .build();
        boolean isUpdated = userDAO.updateWithoutPassword(updateUserWP);
        assertEquals(isUpdated, true);
    }

    @Test
    public void testIsExistUser() throws DaoException {
        assertTrue(userDAO.isExistUser(userFind.getLogin()));
    }

    @Test
    public void testFindPasswordByLogin() throws DaoException {
        psw = userDAO.findPasswordByLogin(userFind.getLogin()).get();
        assertEquals("0fd7dbbf7758929d8b884cbff7caa400", psw);
    }

    @Test
    public void testCreate() throws DaoException {
        boolean isCreated = userDAO.create(userCreateDelete);
        assertEquals(isCreated, true);
    }

    @Test
    public void testDelete() throws DaoException {
        User tmpUser = userDAO.findEntityByLogin(userCreateDelete.getLogin()).get();
        assertEquals(userDAO.delete(tmpUser), true);
    }
}