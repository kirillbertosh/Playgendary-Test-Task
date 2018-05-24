package by.playgendary.bertosh.repositories.implementations;

import by.playgendary.bertosh.entities.Room;
import by.playgendary.bertosh.exceptions.DatabaseException;
import by.playgendary.bertosh.exceptions.EntityNotFoundException;
import by.playgendary.bertosh.repositories.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoomDao implements GenericDao<Room, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    private final static Logger logger = LogManager.getLogger(RoomDao.class);

    @Override
    public Room save(Room room) {
        try {
            entityManager.persist(room);
            return room;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while saving new room with number = " + room.getRoomNumber());
        }
    }

    @Override
    public Room update(Room room) {
        try {
            return entityManager.merge(room);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while updating room with id = " + room.getId());
        }
    }

    @Override
    public void delete(Room room) {
        try {
            entityManager.remove(room);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while deleting room with number = " + room.getRoomNumber());
        }
    }

    @Override
    public List<Room> findAll() {
        try {
            List<Room> rooms = entityManager.createQuery("from Room r").getResultList();
            if (rooms != null) {
                return rooms;
            } else {
                throw new EntityNotFoundException("Can't find any rooms");
            }
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while creating lost of all rooms");
        }
    }

    @Override
    public Room findById(Long id) {
        try {
            Room room = entityManager.find(Room.class, id);
            if (room != null) {
                return room;
            } else {
                throw new EntityNotFoundException("Can't find room with id = " + id);
            }
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while finding room with id = " + id);
        }
    }

    public Room findByNumber(Integer roomNumber) {
        try {
            Room room = (Room)entityManager.createQuery("select r from Room r where r.roomNumber=:roomNumber")
                    .setParameter("roomNumber", roomNumber)
                    .getResultList()
                    .get(0);
            if (room != null) {
                return room;
            } else {
                throw new EntityNotFoundException("Can't find room with number = " + roomNumber);
            }
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while finding room with number = " + roomNumber.toString());
        }
    }
}
