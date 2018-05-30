package company;

import by.playgendary.bertosh.Application;
import by.playgendary.bertosh.entities.Company;
import by.playgendary.bertosh.exceptions.EntityNotFoundException;
import by.playgendary.bertosh.repositories.implementations.CompanyDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Note : companies with id 12 and 13 i added manually to database
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class CompanyDaoTest {

    @Autowired
    private CompanyDao companyDao;

    private static String firstCompanyName = "Test company 1";
    private static String secondCompanyName = "Test company 2";
    private Company testCompany = new Company();


    @Before
    public void init() {
        testCompany.setCompanyName(firstCompanyName);
    }

    @After
    public void clean() {

    }

    @Test
    @Transactional
    public void testSave() {
        testCompany = companyDao.save(testCompany);
        assertNotNull(testCompany);
    }

    @Test
    public void testFindById() {
        Company company = companyDao.findById(12L);
        assertNotNull(company);
        assertEquals(company.getCompanyName(), "string");
    }

    @Test
    @Transactional
    public void testUpdate() {
        Company company = companyDao.findById(12L);
        company.setCompanyName(secondCompanyName);
        company = companyDao.update(company);
        assertEquals(company.getCompanyName(), secondCompanyName);
    }

    @Test
    public void testFindAll() {
        List<Company> companyList = companyDao.findAll();
        assertNotNull(companyList);
        assertEquals(companyList.size(), 2);
    }

    @Test
    @Transactional
    public void testDelete() {
        companyDao.delete(companyDao.findById(13L));
        List<Company> companyList = companyDao.findAll();
        assertEquals(companyList.size(), 1);
        assertNotEquals(companyList.get(0).getCompanyName(), firstCompanyName);
    }
}
