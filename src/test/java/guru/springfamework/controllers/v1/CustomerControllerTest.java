package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {
    private static final String FIRST_NAME1 = "Joe";
    private static final String LAST_NAME1 = "Newman";
    private static final String CUSTOMER_URL1 = "/shop/customers/1";

    private static final String FIRST_NAME2 = "Michael";
    private static final String LAST_NAME2 = "Lajos";
    private static final String CUSTOMER_URL2 = "/shop/customers/2";

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    private MockMvc mockMvc;

    private CustomerDTO customer1;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        customer1 = new CustomerDTO();
        customer1.setId(1L);
        customer1.setFirstname(FIRST_NAME1);
        customer1.setLastname(LAST_NAME1);
        customer1.setCustomer_url(CUSTOMER_URL1);
    }

    @Test
    public void testListCustomers() throws Exception {
        CustomerDTO customer2 = new CustomerDTO();
        customer2.setId(2L);
        customer2.setFirstname(FIRST_NAME2);
        customer2.setLastname(LAST_NAME2);
        customer2.setCustomer_url(CUSTOMER_URL2);

        List<CustomerDTO> customers = Arrays.asList(customer1, customer2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        when(customerService.getCustomerById(anyLong())).thenReturn(customer1);

        mockMvc.perform(get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME1)))
                .andExpect(jsonPath("$.lastname", equalTo(LAST_NAME1)))
                .andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_URL1)));
    }
}
