import by.playgendary.bertosh.entities.Company;
import by.playgendary.bertosh.entities.Room;
import by.playgendary.bertosh.services.CompanyService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class CompanyServiceTest {
    private static String firstCompanyName = "Test company 1";
    private static String secondCompanyName = "Test company 2";
    private static long testCompanyId = 1;

    private final Set<Room> companyRooms = new HashSet<>();
    private Company testCompany = new Company();

    private CompanyService service = new CompanyService();

    @Before
    public void init() {
        companyRooms.add(new Room(11, 121, testCompany));
        companyRooms.add(new Room(12, 122, testCompany));
        companyRooms.add(new Room(13, 123, testCompany));

        testCompany.setCompanyName(firstCompanyName);
        //testCompany.setId(testCompanyId);
        //testCompany.setRooms(companyRooms);
        //testCompany.setCloseTime(new Time());
    }

    @After
    public void clean() {

    }

    @Test
    public void testSave() {
        testCompany = service.save(testCompany);
        Assert.assertNotNull(testCompany);
    }


}
