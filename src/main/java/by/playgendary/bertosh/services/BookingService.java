package by.playgendary.bertosh.services;

import by.playgendary.bertosh.email.EmailSender;
import by.playgendary.bertosh.entities.Booking;
import by.playgendary.bertosh.entities.Room;
import by.playgendary.bertosh.entities.User;
import by.playgendary.bertosh.exceptions.EntityNotFoundException;
import by.playgendary.bertosh.exceptions.IllegalArgumentsException;
import by.playgendary.bertosh.exceptions.ServiceException;
import by.playgendary.bertosh.payloads.BookingRequest;
import by.playgendary.bertosh.repositories.implementations.BookingDao;
import by.playgendary.bertosh.repositories.implementations.RoomDao;
import by.playgendary.bertosh.repositories.implementations.UserDao;
import by.playgendary.bertosh.util.BookingValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class BookingService {

    @Autowired
    private BookingDao dao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private BookingValidator bookingValidator;
    @Autowired
    private EmailSender emailSender;

    private final static Logger logger = LogManager.getLogger(BookingService.class);

    public Booking save(BookingRequest bookingRequest)
            throws EntityNotFoundException, IllegalArgumentsException, ServiceException {
        try {
            if (!bookingValidator.isRoomBooked(bookingRequest.getRoomId(), bookingRequest.getBookingDate(),
                    bookingRequest.getStartTime(), bookingRequest.getEndTime())) {
                return null;
            }
            User user = userDao.findById(bookingRequest.getUserId());
            Room room = roomDao.findById(bookingRequest.getRoomId());
            Booking booking = new Booking();
            booking.setUser(user);
            booking.setRoom(room);
            booking.setBookingDate(bookingRequest.getBookingDate());
            booking.setStartTime(bookingRequest.getStartTime());
            booking.setEndTime(bookingRequest.getEndTime());

            emailSender.send(user, booking);

            return dao.save(booking);
        } catch (IllegalArgumentsException | EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in saving new booking transaction");
        }
    }

    public Booking update(Long id, BookingRequest updateBooking) throws EntityNotFoundException, ServiceException {
        try {
            Booking booking = dao.findById(id);
            if (updateBooking.getUserId() != null) {
                booking.setUser(userDao.findById(updateBooking.getUserId()));
            }
            if (updateBooking.getBookingDate() != null) {
                booking.setBookingDate(updateBooking.getBookingDate());
            }
            if (updateBooking.getRoomId() != null) {
                booking.setRoom(roomDao.findById(updateBooking.getRoomId()));
            }
            if (updateBooking.getStartTime() != null) {
                booking.setStartTime(updateBooking.getStartTime());
            }
            if (updateBooking.getEndTime() != null) {
                booking.setEndTime(updateBooking.getEndTime());
            }
            return dao.update(booking);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception on updating booking with id = " + id + " transaction");
        }
    }

    public void delete(Long id) throws EntityNotFoundException, ServiceException {
        try {
            Booking booking = dao.findById(id);
            dao.delete(booking);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in deleting booking with id = " + id + " transaction");
        }
    }

    public List<Booking> findAll() throws EntityNotFoundException, ServiceException {
        try {
            return dao.findAll();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in creating list of all bookings transaction");
        }
    }

    public Booking findById(Long id) throws EntityNotFoundException, ServiceException {
        try {
            return dao.findById(id);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in finding booking with id = " + id + " transaction");
        }
    }

    public List<Booking> findUserBookings(Long userId) throws EntityNotFoundException, ServiceException {
        try {
            User user = userDao.findById(userId);
            List<Booking> bookingList = dao.findAll();
            bookingList.removeIf(booking -> !Objects.equals(booking.getUser(), user));
            return bookingList;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in creating user's booking list transaction");
        }
    }
}
