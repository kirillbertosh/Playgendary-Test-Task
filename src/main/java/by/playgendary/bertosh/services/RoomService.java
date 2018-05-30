package by.playgendary.bertosh.services;

import by.playgendary.bertosh.entities.Company;
import by.playgendary.bertosh.entities.Room;
import by.playgendary.bertosh.exceptions.EntityNotFoundException;
import by.playgendary.bertosh.exceptions.IllegalArgumentsException;
import by.playgendary.bertosh.exceptions.ServiceException;
import by.playgendary.bertosh.payloads.RoomRequest;
import by.playgendary.bertosh.repositories.implementations.CompanyDao;
import by.playgendary.bertosh.repositories.implementations.RoomDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.List;

@Service
@Transactional
public class RoomService {

    @Autowired
    private RoomDao dao;
    @Autowired
    private CompanyDao companyDao;

    private final static Logger logger = LogManager.getLogger(RoomService.class);

    public Room save(RoomRequest roomRequest) throws EntityNotFoundException, ServiceException {
        try {
            Company company = companyDao.findById(roomRequest.getCompanyId());
            Room checkRoom = dao.findByNumber(roomRequest.getRoomNumber(), company);
            if (checkRoom != null && checkRoom.getCompany().getId() == roomRequest.getCompanyId()) {
                throw new IllegalArgumentsException("Room already exist");
            } else {
                Room room = new Room();
                room.setCompany(company);
                room.setRoomNumber(roomRequest.getRoomNumber());
                return dao.save(room);
            }
        } catch (IllegalArgumentsException | EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in saving new room transaction");
        }
    }

    public Room update(Long id, Room updateRoom) throws EntityNotFoundException, ServiceException {
        try {
            Room room = dao.findById(id);
            if (updateRoom.getRoomNumber() != 0 && updateRoom.getRoomNumber() != room.getRoomNumber()) {
                room.setRoomNumber(updateRoom.getRoomNumber());
            }
            if (updateRoom.getCompany() != null && !Objects.equals(room.getCompany(), updateRoom.getCompany())) {
                room.setCompany(updateRoom.getCompany());
            }
            return dao.update(room);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in updating room with id = " + id + " transaction");
        }
    }

    public void delete(Long id) throws EntityNotFoundException, ServiceException {
        try {
            Room room = dao.findById(id);
            room.setCompany(null);
            dao.delete(room);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in deleting room with id = " + id + " transaction");
        }
    }

    public Room findById(Long id) throws EntityNotFoundException, ServiceException {
        try {
            return dao.findById(id);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in finding room with id = " + id + " transaction");
        }
    }

    public List<Room> findAll() throws EntityNotFoundException, ServiceException {
        try {
            return dao.findAll();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in creating list of all rooms transaction");
        }
    }

    public Room findByNumber(Integer number, Long companyId) throws EntityNotFoundException, ServiceException {
        try {
            Company company = companyDao.findById(companyId);
            Room room = dao.findByNumber(number, company);
            if (room != null) {
                return room;
            } else {
                throw new EntityNotFoundException(
                        "Can't find room with number = " + number + "at company with id = " + companyId);
            }
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in finding room with number = " + number + " transaction");
        }
    }
}
