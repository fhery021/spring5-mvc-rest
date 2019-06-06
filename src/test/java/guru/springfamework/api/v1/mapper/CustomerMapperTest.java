package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerMapperTest {

    public static final String FIRST_NAME = "Joe";
    public static final String LAST_NAME = "Newman";
    public static final String CUSTOMER_URL = "/shop/customers/1";

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() throws Exception{
        //given
        Customer customer = new Customer();
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);
        customer.setCustomer_url(CUSTOMER_URL);

        //when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        //then
        assertEquals(FIRST_NAME, customerDTO.getFirstname());
        assertEquals(LAST_NAME, customerDTO.getLastname());
        assertEquals(CUSTOMER_URL, customerDTO.getCustomer_url());
    }
}
