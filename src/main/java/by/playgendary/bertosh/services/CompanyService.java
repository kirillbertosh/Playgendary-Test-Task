package by.playgendary.bertosh.services;

import by.playgendary.bertosh.entities.Company;
import by.playgendary.bertosh.exceptions.EntityNotFoundException;
import by.playgendary.bertosh.exceptions.ServiceException;
import by.playgendary.bertosh.repositories.implementations.CompanyDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CompanyService {

    @Autowired
    private CompanyDao dao;

    private final static Logger logger = LogManager.getLogger(CompanyService.class);

    public Company save(Company company) throws ServiceException {
        try {
            return dao.save(company);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in saving new company transaction");
        }
    }

    public Company update(Long id, Company updateCompany) throws EntityNotFoundException, ServiceException {
        try {
            Company company = dao.findById(id);
            if (updateCompany.getCompanyName() != null) {
                company.setCompanyName(updateCompany.getCompanyName());
            }
            if (updateCompany.getOpenTime() != null) {
                company.setOpenTime(updateCompany.getOpenTime());
            }
            if (updateCompany.getCloseTime() != null) {
                company.setCloseTime(updateCompany.getCloseTime());
            }
            return dao.update(company);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in updating company with id = " + id + " transaction");
        }
    }

    public void delete(Long id) throws EntityNotFoundException, ServiceException {
        try {
            Company company = dao.findById(id);
            dao.delete(company);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in deleting company with id = " + id + " transaction");
        }
    }

    public Company findById(Long id) throws EntityNotFoundException, ServiceException {
        try {
            return dao.findById(id);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("Exception in finding company with id = " + id + " transaction");
        }
    }
}
