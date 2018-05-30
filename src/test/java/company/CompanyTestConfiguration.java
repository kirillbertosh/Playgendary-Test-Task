package company;

import by.playgendary.bertosh.repositories.implementations.CompanyDao;
import by.playgendary.bertosh.services.CompanyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackageClasses = {
        CompanyService.class,
        CompanyDao.class
})
@SpringBootApplication
public class CompanyTestConfiguration {

    public static void main(String... args) {
        SpringApplication.run(CompanyTestConfiguration.class);
    }
}
