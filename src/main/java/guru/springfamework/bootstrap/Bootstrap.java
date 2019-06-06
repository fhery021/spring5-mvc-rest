package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by jt on 9/24/17.
 */
@Component
public class Bootstrap implements CommandLineRunner{

    private final CategoryRepository categoryRespository;
    private final CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRespository, CustomerRepository customerRepository) {
        this.categoryRespository = categoryRespository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
    }

    private void loadCategories(){
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRespository.save(fruits);
        categoryRespository.save(dried);
        categoryRespository.save(fresh);
        categoryRespository.save(exotic);
        categoryRespository.save(nuts);


        System.out.println("Number of categories = " + categoryRespository.count() );
    }

    private void loadCustomers() {
        final String FIRST_NAME1 = "Joe";
        final String LAST_NAME1 = "Newman";
        final String CUSTOMER_URL1 = "/shop/customers/1";

        final String FIRST_NAME2 = "Michael";
        final String LAST_NAME2 = "Lajos";
        final String CUSTOMER_URL2 = "/shop/customers/2";

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstname(FIRST_NAME1);
        customer1.setLastname(LAST_NAME1);

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstname(FIRST_NAME2);
        customer2.setLastname(LAST_NAME2);

        customerRepository.saveAll(Arrays.asList(customer1, customer2));

        System.out.println("Number of customers = " + customerRepository.count());
    }
}
