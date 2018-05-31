package room;

import by.playgendary.bertosh.Application;
import by.playgendary.bertosh.entities.Room;
import by.playgendary.bertosh.exceptions.EntityNotFoundException;
import by.playgendary.bertosh.exceptions.IllegalArgumentsException;
import by.playgendary.bertosh.payloads.RoomRequest;
import by.playgendary.bertosh.repositories.implementations.CompanyDao;
import by.playgendary.bertosh.services.RoomService;
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
public class RoomServiceTest {

    private static Long companyId = 12L;
    private static int roomNumber = 404;
    private static int updateRoomNumber = 505;
    private static Long roomId = 17L;

    private Room room = new Room();
    private RoomRequest roomRequest = new RoomRequest();
    @Autowired
    private RoomService service;
    @Autowired
    private CompanyDao companyDao;

    @Before
    public void init() {
        room.setRoomNumber(roomNumber);
        room.setCompany(companyDao.findById(companyId));

        roomRequest.setCompanyId(companyId);
        roomRequest.setRoomNumber(roomNumber);
    }

    @Test
    @Transactional
    public void testSave() {
        room = service.save(roomRequest);
        assertNotNull(room);
        assertEquals(room.getRoomNumber(), roomNumber);
    }

    /**
     * Room with number 121 and company id = 12 already exist in database
     */
    @Test(expected = IllegalArgumentsException.class)
    @Transactional
    public void testSaveRoomThatAlreadyExist() {
        roomRequest.setRoomNumber(121);
        roomRequest.setCompanyId(12L);
        Room room = service.save(roomRequest);
        assertNull(room);
    }

    @Test(expected = EntityNotFoundException.class)
    @Transactional
    public void testSaveRoomWithCompanyThanNotExist() {
        roomRequest.setCompanyId(1L);
        Room room = service.save(roomRequest);
        assertNull(room);
    }

    /**
     * Room with id = 17 already exist
     */
    @Test
    public void testFindById() {
        Room room = service.findById(roomId);
        assertNotNull(room);
    }

    @Test
    public void testFindAll() {
        List<Room> roomList = service.findAll();
        assertNotEquals(roomList.size(), 0);
    }

    /**
     * Room with number 121 already exist
     */
    @Test
    public void testFindByNumber() {
        Room room = service.findByNumber(121, 12L);
        assertNotNull(room);
    }

    @Test(expected = EntityNotFoundException.class)
    @Transactional
    public void testDelete() {
        room = service.save(roomRequest);
        Long id = room.getId();
        assertNotNull(service.findById(id));
        service.delete(room.getId());
        assertNull(service.findById(id));
    }

    @Test
    @Transactional
    public void testUpdate() {
        roomRequest.setRoomNumber(updateRoomNumber);
        room = service.update(roomId, roomRequest);
        assertEquals(room.getRoomNumber(), updateRoomNumber);
    }
}
