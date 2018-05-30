package room;

import by.playgendary.bertosh.Application;
import by.playgendary.bertosh.entities.Company;
import by.playgendary.bertosh.entities.Room;
import by.playgendary.bertosh.exceptions.EntityNotFoundException;
import by.playgendary.bertosh.repositories.implementations.CompanyDao;
import by.playgendary.bertosh.repositories.implementations.RoomDao;
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
public class RoomDaoTest {

    // Company with id = 12 already exist
    private static Long companyId = 12L;
    private static int roomNumber = 404;
    private static int updateRoomNumber = 505;
    private static Long roomId = 17L;

    private Room room = new Room();

    @Autowired
    private RoomDao dao;
    @Autowired
    private CompanyDao companyDao;

    @Before
    public void init() {
        room.setRoomNumber(roomNumber);
        room.setCompany(companyDao.findById(companyId));
    }

    @Test
    @Transactional
    public void testSave() {
        room = dao.save(room);
        assertNotNull(room);
        assertEquals(room.getRoomNumber(), roomNumber);
    }

    /**
     * Room with id = 17 already exist
     */
    @Test
    public void testFindById() {
        Room room = dao.findById(roomId);
        assertNotNull(room);
    }

    @Test
    public void testFindAll() {
        List<Room> roomList = dao.findAll();
        assertNotEquals(roomList.size(), 0);
    }

    /**
     * Room with number 121 already exist
     */
    @Test
    public void testFindByNumber() {
        Company company = companyDao.findById(12L);
        Room room = dao.findByNumber(121, company);
        assertNotNull(room);
    }

    @Test(expected = EntityNotFoundException.class)
    @Transactional
    public void testDelete() {
        room = dao.save(room);
        Long id = room.getId();
        assertNotNull(dao.findById(id));
        dao.delete(room);
        assertNull(dao.findById(id));
    }

    @Test
    @Transactional
    public void testUpdate() {
        room.setRoomNumber(updateRoomNumber);
        room = dao.update(room);
        assertEquals(room.getRoomNumber(), updateRoomNumber);
    }
}
