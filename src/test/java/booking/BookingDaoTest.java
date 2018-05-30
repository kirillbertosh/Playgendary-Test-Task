package booking;

import by.playgendary.bertosh.Application;
import by.playgendary.bertosh.entities.Booking;
import by.playgendary.bertosh.entities.Room;
import by.playgendary.bertosh.entities.User;
import by.playgendary.bertosh.exceptions.EntityNotFoundException;
import by.playgendary.bertosh.repositories.implementations.BookingDao;
import by.playgendary.bertosh.repositories.implementations.RoomDao;
import by.playgendary.bertosh.repositories.implementations.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class BookingDaoTest {

    private static Date bookingDate = Date.valueOf("2018-05-30");
    private static Time startTime = Time.valueOf("10:00:00");
    private static Time endTime = Time.valueOf("11:00:00");

    private Room room;
    private User user;
    private Booking booking = new Booking();

    @Autowired
    private BookingDao dao;
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private UserDao userDao;

    @Before
    public void init() {
        booking.setBookingDate(bookingDate);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        room = roomDao.findById(17L);
        user = userDao.findById(13L);
        booking.setRoom(room);
        booking.setUser(user);
    }

    @Test
    @Transactional
    public void testSave() {
        booking = dao.save(booking);
        assertNotNull(booking);
        assertNotEquals(booking.getId(), 0);
    }

    /**
     * Booking with id = 18 already exist
     */
    @Test
    public void testFindById() {
        Booking booking = dao.findById(18L);
        assertNotNull(booking);
        assertEquals(booking.getId(), 18L);
    }

    @Test
    public void testFindAll() {
        List<Booking> bookingList = dao.findAll();
        assertNotNull(bookingList);
        assertNotEquals(bookingList.size(), 0);
    }

    @Test
    public void testFindByDate() {
        List<Booking> bookingList = dao.findByDate(bookingDate);
        assertNotNull(bookingList);
        assertNotEquals(bookingList.size(), 0);
    }

    @Test
    @Transactional
    public void testUpdate() {
        booking.setBookingDate(Date.valueOf("2018-05-31"));
        booking = dao.update(booking);
        assertEquals(booking.getBookingDate(), Date.valueOf("2018-05-31"));
    }

    @Test(expected = EntityNotFoundException.class)
    @Transactional
    public void testDelete() {
        booking = dao.save(booking);
        Long id = booking.getId();
        assertNotNull(dao.findById(id));
        dao.delete(booking);
        assertNull(dao.findById(id));
    }
}
