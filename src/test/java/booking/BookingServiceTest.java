package booking;

import by.playgendary.bertosh.Application;
import by.playgendary.bertosh.entities.Booking;
import by.playgendary.bertosh.exceptions.EntityNotFoundException;
import by.playgendary.bertosh.exceptions.IllegalArgumentsException;
import by.playgendary.bertosh.payloads.BookingRequest;
import by.playgendary.bertosh.services.BookingService;
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
public class BookingServiceTest {

    private static Date bookingDate = Date.valueOf("2018-05-30");
    private static Time startTime = Time.valueOf("10:00:00");
    private static Time endTime = Time.valueOf("11:00:00");
    private static Long roomId = 17L;
    private static Long userId = 13L;
    private static Long bookingId = 18L;

    private Booking booking = new Booking();
    private BookingRequest request = new BookingRequest();

    @Autowired
    private BookingService service;

    @Before
    public void init() {
        request.setBookingDate(bookingDate);
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        request.setRoomId(roomId);
        request.setUserId(userId);
    }

    @Test
    @Transactional
    public void testSave() {
        Booking booking = service.save(request);
        assertNotNull(booking);
        assertNotEquals(booking.getId(), 0);
        assertEquals(booking.getBookingDate(), bookingDate);
    }

    @Test(expected = IllegalArgumentsException.class)
    @Transactional
    public void testSaveWithIncorrectTimeInterval() {
        request.setStartTime(endTime);
        request.setEndTime(startTime);
        Booking booking = service.save(request);
        assertNull(booking);
    }

    @Test(expected = IllegalArgumentsException.class)
    @Transactional
    public void testSaveInNotWorkingTimeOfCompany() {
        request.setStartTime(Time.valueOf("06:00:00"));
        request.setEndTime(Time.valueOf("23:00:00"));
        Booking booking = service.save(request);
        assertNull(booking);
    }

    /**
     * Booking in period from 11:00 to 11:30 is exist
     */
    @Test(expected = IllegalArgumentsException.class)
    @Transactional
    public void testSaveInBookedTime() {
        request.setStartTime(Time.valueOf("10:00:00"));
        request.setEndTime(Time.valueOf("11:40:00"));
        Booking booking = service.save(request);
        assertNull(booking);
    }

    /**
     * Booking with id 18 already exist
     */
    @Test
    public void testFindById() {
        Booking booking = service.findById(bookingId);
        assertNotNull(booking);
        assertTrue(booking.getId() == bookingId);
    }

    @Test
    public void testFindAll() {
        List<Booking> bookingList = service.findAll();
        assertNotNull(bookingList);
        assertNotEquals(bookingList.size(), 0);
    }

    @Test
    public void testFindUserBookings() {
        List<Booking> bookingList = service.findUserBookings(userId);
        assertNotNull(bookingList);
        assertNotEquals(bookingList.size(), 0);
    }

    @Test(expected = EntityNotFoundException.class)
    @Transactional
    public void testDelete() {
        booking = service.save(request);
        Long id = booking.getId();
        assertNotNull(service.findById(id));
        service.delete(booking.getId());
        assertNull(service.findById(id));
    }

    @Test
    @Transactional
    public void testUpdate() {
        request.setBookingDate(Date.valueOf("2018-06-02"));
        booking = service.update(bookingId, request);
        assertNotNull(booking);
        assertTrue(booking.getId() == bookingId);
    }
}
