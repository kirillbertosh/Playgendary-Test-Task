package company;

import by.playgendary.bertosh.Application;
import by.playgendary.bertosh.entities.Company;
import by.playgendary.bertosh.exceptions.EntityNotFoundException;
import by.playgendary.bertosh.services.CompanyService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Note : companies with id 12 and 13 i added manually to database
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class CompanyServiceTest {
    private static String firstCompanyName = "Test company 1";
    private static String secondCompanyName = "Test company 2";
    private Company testCompany = new Company();

    @Autowired
    private CompanyService service;

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
        testCompany = service.save(testCompany);
        Assert.assertNotNull(testCompany);
    }

    @Test
    public void testFindById() {
        Company company = service.findById(12L);
        assertNotNull(company);
        assertEquals(company.getId(), 12);
    }

    @Test
    @Transactional
    public void testUpdate() {
        Company company = service.findById(12L);
        company.setCompanyName(secondCompanyName);
        company = service.update(12L, company);
        assertEquals(company.getCompanyName(), secondCompanyName);
    }

    @Test(expected = EntityNotFoundException.class)
    @Transactional
    public void testDelete() {
        service.delete(13L);
        assertNull(service.findById(13L));
    }

}
