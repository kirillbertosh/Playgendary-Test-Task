package by.playgendary.bertosh.repositories.implementations;

import by.playgendary.bertosh.entities.Booking;
import by.playgendary.bertosh.exceptions.DatabaseException;
import by.playgendary.bertosh.repositories.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BookingDao implements GenericDao<Booking, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    private final static Logger logger = LogManager.getLogger(BookingDao.class);

    @Override
    public Booking save(Booking booking) {
        try {
            entityManager.persist(booking);
            return booking;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while saving new booking with id = " + booking.getId());
        }
    }

    @Override
    public Booking update(Booking booking) {
        try {
            return entityManager.merge(booking);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while updating booking with id = " + booking.getId());
        }
    }

    @Override
    public void delete(Booking booking) {
        try {
            entityManager.remove(booking);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while deleting room with id = " + booking.getId());
        }
    }

    @Override
    public List<Booking> findAll() {
        try {
            return entityManager.createQuery("from Booking b").getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while creating lost of all bookings");
        }
    }

    @Override
    public Booking findById(Long id) {
        try {
            return entityManager.find(Booking.class, id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while finding booking with id = " + id);
        }
    }
}
