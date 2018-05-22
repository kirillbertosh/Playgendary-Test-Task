package by.playgendary.bertosh.repositories.implementations;

import by.playgendary.bertosh.entities.User;
import by.playgendary.bertosh.exceptions.DatabaseException;
import by.playgendary.bertosh.repositories.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDao implements GenericDao<User, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    private final static Logger logger = LogManager.getLogger(UserDao.class);

    public UserDao() {
    }

    @Override
    public User save(User user) {
        try {
            entityManager.persist(user);
            return user;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while saving new user");
        }
    }

    @Override
    public User update(User user) {
        try {
            return entityManager.merge(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while updating user with id = " + user.getId());
        }
    }

    @Override
    public void  delete(User user) {
        try {
            entityManager.remove(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while deleting user with id = " + user.getId());
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return entityManager.createQuery("from User c").getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while getting list of all users");
        }
    }

    @Override
    public User findById(Long id) {
        try {
            return entityManager.find(User.class, id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while getting user with id = " + id);
        }
    }

    public User findByEmail(String email) {
        try {
            return (User)entityManager.createQuery("select u from User u where u.email=:email")
                    .setParameter("email", email)
                    .getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while getting user with email = " + email);
        }
    }
}
