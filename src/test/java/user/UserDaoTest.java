package user;

import by.playgendary.bertosh.Application;
import by.playgendary.bertosh.entities.User;
import by.playgendary.bertosh.exceptions.EntityNotFoundException;
import by.playgendary.bertosh.repositories.implementations.UserDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class UserDaoTest {

    private static String userFirstName = "Tony";
    private static String userLastName = "Montana";
    private static String userEmail = "tony.montana@gmail.com";
    private static String updateUserEmail = "tony.montana@yandex.ru";
    private static Long userId = 13L;
    private static Long wrongUserId = 3L;
    private static String testEmail = "kirillbertosh@gmail.com";

    private User user = new User();

    @Autowired
    private UserDao dao;

    @Before
    public void initTest() {
        user.setEmail(userEmail);
        user.setFirstName(userFirstName);
        user.setLastName(userLastName);
    }

    @Test
    @Transactional
    public void testSave() {
        user = dao.save(user);
        assertNotNull(user);
        assertNotEquals(user.getId(), 0);
    }

    /**
     * User with id = 13 is in database before executing test
     */
    @Test
    public void testFindById() {
        User user = dao.findById(userId);
        assertNotNull(user);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testFindByIdWithWrongId() {
        User user = dao.findById(wrongUserId);
        assertNull(user);
    }

    @Test
    @Transactional
    public void testUpdate() {
        user.setEmail(updateUserEmail);
        user = dao.update(user);
        assertEquals(user.getEmail(), updateUserEmail);
    }

    @Test(expected = EntityNotFoundException.class)
    @Transactional
    public void testDelete() {
        user = dao.save(user);
        Long id = user.getId();
        assertNotNull(dao.findById(id));
        dao.delete(user);
        assertNull(dao.findById(id));
    }

    /**
     * One user is in database
     */

    @Test
    public void testFindAll() {
        List<User> userList = dao.findAll();
        assertNotNull(userList);
        assertEquals(userList.size(), 1);
    }

    @Test
    public void testFindByEmail() {
        user = dao.findByEmail(testEmail);
        assertNotNull(user);
    }
}
