package by.playgendary.bertosh.repositories.implementations;

import by.playgendary.bertosh.entities.Company;
import by.playgendary.bertosh.exceptions.DatabaseException;
import by.playgendary.bertosh.repositories.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CompanyDao implements GenericDao<Company, Long>{

    @PersistenceContext
    private EntityManager entityManager;

    private final static Logger logger = LogManager.getLogger(CompanyDao.class);


    @Override
    public Company save(Company company) {
        try {
            entityManager.persist(company);
            return company;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while saving new company with name = " + company.getCompanyName());
        }
    }

    @Override
    public Company update(Company company) {
        try {
            return entityManager.merge(company);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while updating company with id = " + company.getId());
        }
    }

    @Override
    public void delete(Company company) {
        try {
            entityManager.remove(company);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while deleting company with name = " + company.getCompanyName());
        }
    }

    @Override
    public List<Company> findAll() {
        try {
            return entityManager.createQuery("from Company c").getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while creating list of all companies");
        }
    }

    @Override
    public Company findById(Long id) {
        try {
            return entityManager.find(Company.class, id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Exception while finding company with id = " + id);
        }
    }
}
