package by.playgendary.bertosh.services;

import by.playgendary.bertosh.entities.Company;
import by.playgendary.bertosh.entities.User;
import by.playgendary.bertosh.exceptions.EntityNotFoundException;
import by.playgendary.bertosh.exceptions.IllegalArgumentsException;
import by.playgendary.bertosh.exceptions.ServiceException;
import by.playgendary.bertosh.payloads.UserRequest;
import by.playgendary.bertosh.repositories.implementations.CompanyDao;
import by.playgendary.bertosh.repositories.implementations.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao dao;
    @Autowired
    private CompanyDao companyDao;

    private final static Logger logger = LogManager.getLogger(UserService.class);

    public User save(UserRequest userRequest) throws IllegalArgumentsException, EntityNotFoundException, ServiceException {
        try {
            User user = new User();
            Company company = companyDao.findById(userRequest.getCompanyId());
            if (dao.findByEmail(userRequest.getEmail()) == null) {
                user.setCompany(company);
                user.setFirstName(userRequest.getFirstName());
                user.setLastName(userRequest.getLastName());
                user.setEmail(userRequest.getEmail());
                return dao.save(user);
            } else {
                throw new IllegalArgumentsException("User with email " + userRequest.getEmail() + " already exist");
            }
        } catch (IllegalArgumentsException | EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in saving new user transaction");
        }
    }

    public User update(Long id, User updateUser) throws EntityNotFoundException, ServiceException {
        try {
            User user = dao.findById(id);
            if (updateUser.getEmail() != null) {
                user.setEmail(updateUser.getEmail());
            }
            if (updateUser.getFirstName() != null) {
                user.setFirstName(updateUser.getFirstName());
            }
            if (updateUser.getLastName() != null) {
                user.setLastName(updateUser.getLastName());
            }
            return dao.update(user);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in updating user with id = " + id + " transaction");
        }
    }

    public void delete(Long id) throws EntityNotFoundException, ServiceException {
        try {
            User user = dao.findById(id);
            user.setCompany(null);
            dao.delete(user);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in deleting user with id = " + id + " transaction");
        }
    }

    public User findById(Long id) throws EntityNotFoundException, ServiceException {
        try {
            return dao.findById(id);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in finding user with id = " + id + " transaction");
        }
    }

    public List<User> findAll() throws EntityNotFoundException, ServiceException {
        try {
            return dao.findAll();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in creating list of all users transaction");
        }
    }

    public User findByEmail(String email) throws EntityNotFoundException, ServiceException {
        try {
            return dao.findByEmail(email);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in finding user with email = " + email + " transaction");
        }
    }

    public boolean existByEmail(String email) throws ServiceException {
        try {
            return dao.findByEmail(email) != null;
        } catch (EntityNotFoundException e) {
            return false;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in finding user with email = " + email + " transaction");
        }
    }
}
