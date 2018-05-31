package user;

import by.playgendary.bertosh.Application;
import by.playgendary.bertosh.entities.User;
import by.playgendary.bertosh.exceptions.EntityNotFoundException;
import by.playgendary.bertosh.exceptions.IllegalArgumentsException;
import by.playgendary.bertosh.payloads.UserRequest;
import by.playgendary.bertosh.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceTest {

    private static String userFirstName = "Tony";
    private static String userLastName = "Montana";
    private static String userEmail = "tony.montana@gmail.com";
    private static String updateUserEmail = "tony.montana@yandex.ru";
    private static Long userId = 13L;
    private static Long wrongUserId = 3L;
    private static String testEmail = "kirillbertosh@gmail.com";
    private static Long companyId = 12L;

    private User user = new User();
    private UserRequest request = new UserRequest();

    @Autowired
    private UserService service;

    @Before
    public void initTest() {
        user.setEmail(userEmail);
        user.setFirstName(userFirstName);
        user.setLastName(userLastName);

        request.setEmail(userEmail);
        request.setFirstName(userFirstName);
        request.setLastName(userLastName);
        request.setCompanyId(companyId);
    }

    @Test
    @Transactional
    public void testSave() {
        User user = service.save(request);
        assertNotNull(user);
    }

    /**
     * User with email kirillbertosh@gmail.com already exist
     */
    @Test(expected = IllegalArgumentsException.class)
    @Transactional
    public void testSaveUserWitchExist() {
        request.setEmail(testEmail);
        User user = service.save(request);
        assertNull(user);
    }

    @Test
    public void testFindById() {
        User user = service.findById(userId);
        assertNotNull(user);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testFindByIdWithWrongId() {
        User user = service.findById(wrongUserId);
        assertNull(user);
    }

    @Test
    @Transactional
    public void testUpdate() {
        request.setEmail(updateUserEmail);
        user = service.update(13L, request);
        assertEquals(user.getEmail(), updateUserEmail);
    }

    @Test(expected = EntityNotFoundException.class)
    @Transactional
    public void testDelete() {
        user = service.save(request);
        Long id = user.getId();
        assertNotNull(service.findById(id));
        service.delete(id);
        assertNull(service.findById(id));
    }

    /**
     * One user is in database
     */

    @Test
    public void testFindAll() {
        List<User> userList = service.findAll();
        assertNotNull(userList);
        assertEquals(userList.size(), 1);
    }

    @Test
    public void testFindByEmail() {
        user = service.findByEmail(testEmail);
        assertNotNull(user);
    }
}
