package by.playgendary.bertosh.repositories.implementations;

import by.playgendary.bertosh.entities.Booking;
import by.playgendary.bertosh.exceptions.DatabaseException;
import by.playgendary.bertosh.exceptions.EntityNotFoundException;
import by.playgendary.bertosh.repositories.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.List;

@Repository
public class BookingDao implements GenericDao<Booking, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    private final static Logger logger = LogManager.getLogger(BookingDao.class);

    @Override
    public Booking save(Booking booking) throws DatabaseException {
        try {
            entityManager.persist(booking);
            return booking;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while saving new booking with id = " + booking.getId());
        }
    }

    @Override
    public Booking update(Booking booking) throws DatabaseException {
        try {
            return entityManager.merge(booking);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while updating booking with id = " + booking.getId());
        }
    }

    @Override
    public void delete(Booking booking) throws DatabaseException {
        try {
            entityManager.remove(booking);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while deleting room with id = " + booking.getId());
        }
    }

    @Override
    public List<Booking> findAll() throws EntityNotFoundException, DatabaseException {
        try {
            List<Booking> bookingList = entityManager.createQuery("from Booking b", Booking.class).getResultList();
            if (bookingList != null) {
                return bookingList;
            } else {
                throw new EntityNotFoundException("Can't find any bookings");
            }
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while creating lost of all bookings");
        }
    }

    @Override
    public Booking findById(Long id) throws EntityNotFoundException, DatabaseException {
        try {
            Booking booking = entityManager.find(Booking.class, id);
            if (booking != null) {
                return booking;
            } else {
                throw new EntityNotFoundException("Can't find booking with id = " + id);
            }
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while finding booking with id = " + id);
        }
    }

    public List<Booking> findByDate(Date bookingDate) {
        try {
            return entityManager.createQuery("from Booking b where b.bookingDate=:bookingDate", Booking.class)
                    .setParameter("bookingDate", bookingDate)
                    .getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while finding bookings with date = " + bookingDate);
        }
    }
}
