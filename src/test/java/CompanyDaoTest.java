import by.playgendary.bertosh.entities.Company;
import by.playgendary.bertosh.entities.Room;
import by.playgendary.bertosh.repositories.implementations.CompanyDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class CompanyDaoTest {

    private static String firstCompanyName = "Test company 1";
    private static String secondCompanyName = "Test company 2";

    private final Set<Room> companyRooms = new HashSet<>();
    private Company firstTestCompany = new Company();
    private Company secondTestCompany = new Company();

    private CompanyDao companyDao;

    @Before
    public void init() {
        companyDao = new CompanyDao();

        firstTestCompany.setCompanyName(firstCompanyName);
        secondTestCompany.setCompanyName(secondCompanyName);
        firstTestCompany = companyDao.save(firstTestCompany);
        secondTestCompany = companyDao.save(secondTestCompany);
    }

    @After
    public void clean() {

    }

    @Test
    public void testSave() {
        Assert.assertNotEquals(firstTestCompany.getId(), 0);
    }

    @Test
    public void testGetAll() {
        Assert.assertNotNull(companyDao.findAll());
    }

    @Test
    public void testUpdate() {
        firstTestCompany.setCompanyName(secondCompanyName);
        companyDao.update(firstTestCompany);
        //Assert.assertEquals();
    }
}
