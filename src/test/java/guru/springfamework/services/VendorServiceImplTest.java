package guru.springfamework.services;

import com.sun.org.apache.bcel.internal.generic.NEW;
import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VendorServiceImplTest  {
    @Mock
    VendorRepository repository;

    VendorMapper mapper = VendorMapper.INSTANCE;

    VendorService vendorService;

    private Vendor vendor1;
    private Vendor vendor2;
    final String NEW_NAME = "New Vendor Name Ltd.";

    private final void initVendors(){
        vendor1 = new Vendor();
        vendor1.setId(1L);
        vendor1.setName("Western Tasty Fruits Ltd.");
        vendor1.setUrl("/shop/vendors/672");

        vendor2 = new Vendor();
        vendor2.setId(2L);
        vendor2.setName("Exotic Fruits Company");
        vendor2.setUrl("/shop/vendors/32");
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(mapper, repository);

        initVendors();
    }

    @Test
    public void getAllVendors(){
        // given = initVendors() from setUp()
        when(repository.findAll()).thenReturn(Arrays.asList(vendor1, vendor2));

        // when
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        // then
        assertThat(vendorDTOS, notNullValue());
        assertThat(2, equalTo(vendorDTOS.size()));
    }

    @Test
    public void createNewVendor(){
        //given
        final String VENDOR_NAME = "New Vendor LTD.";

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(VENDOR_NAME);

        Vendor vendor = new Vendor();
        vendor.setId(3L);
        vendor.setName(VENDOR_NAME);


//        when(repository.save(any())).thenReturn(vendor);
        given(repository.save(any(Vendor.class))).willReturn(vendor);

        // when
        VendorDTO createdVendor = vendorService.createNewVendor(vendorDTO);

        //then
        assertThat(createdVendor, notNullValue());
        assertThat(createdVendor.getName(), equalTo(VENDOR_NAME));
        assertThat(createdVendor.getUrl(), equalTo("/api/v1/vendors/3"));

    }

    @Test
    public void deleteById() throws Exception{
        vendorService.deleteVendorById(1L);
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    public void getVendorById() throws Exception{
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.of(vendor2));

        //when
        VendorDTO dto = vendorService.getVendorById(2L);

        //then
        assertThat(dto, notNullValue());
        assertThat(dto.getName(), equalTo(vendor2.getName()));
    }

    @Test
    public void patchVendor() throws Exception {
        VendorDTO dto = new VendorDTO();
        dto.setName(NEW_NAME);

        when(repository.findById(anyLong())).thenReturn(Optional.of(vendor1));
        when(repository.save(any(Vendor.class))).thenReturn(vendor1);

        VendorDTO savedDTO = vendorService.patchVendor(1L, dto);

        assertThat(savedDTO.getName(), equalTo(NEW_NAME));

    }

    @Test
    public void putVendor() throws Exception {
        VendorDTO dto = new VendorDTO();
        dto.setName(NEW_NAME);

        vendor1.setName(NEW_NAME);

        when(repository.save(any(Vendor.class))).thenReturn(vendor1);

        VendorDTO savedDTO = vendorService.putVendor(1L, dto);

        assertThat(savedDTO.getName(), equalTo(NEW_NAME));
    }
}
