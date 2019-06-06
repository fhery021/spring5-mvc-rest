package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    String CUSTOMER_BASE_URL = "/api/v1/customer/";

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    default CustomerDTO customerToCustomerDTO(Customer customer){
        if (customer == null){
            return null;
        }
        CustomerDTO dto = new CustomerDTO();
        dto.setFirstname(customer.getFirstname());
        dto.setLastname(customer.getLastname());
        dto.setCustomer_url(CUSTOMER_BASE_URL + customer.getId());
        return dto;
    }

    Customer customerDTOToCustomer(CustomerDTO customerDTO);
}
