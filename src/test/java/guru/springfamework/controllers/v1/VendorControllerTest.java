package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.domain.Vendor;
import guru.springfamework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest extends AbstractRestControllerTest{

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    VendorDTO dto1;
    VendorDTO dto2;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();

        initDTOs();
    }

    private void initDTOs() {
        dto1 = new VendorDTO();
        dto1.setName("Western Tasty Fruits Ltd.");
        dto1.setUrl(VendorController.BASE_URL + "/672");

        dto2 = new VendorDTO();
        dto2.setName("Exotic Fruits Company");
        dto2.setUrl(VendorController.BASE_URL + "/32");
    }

    @Test
    public void getVendors() throws Exception {
        // given from initDTOs

        when(vendorService.getAllVendors()).thenReturn(Arrays.asList(dto1, dto2));

        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void createNewVendor() throws Exception {
        when(vendorService.createNewVendor(any(VendorDTO.class))).thenReturn(this.dto1);

        mockMvc.perform(post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(dto1.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(dto1.getUrl())));
    }

    @Test
    public void deleteVendorById() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getVendorById() throws Exception {
        when(vendorService.getVendorById(anyLong())).thenReturn(dto2);

        mockMvc.perform(get(VendorController.BASE_URL + "/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(dto2.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(dto2.getUrl())));
    }

    @Test
    public void patchVendorById() throws Exception{
      // patching dto2 with name of dto1 => dto2 will have the same name as dto1
        dto2.setName(dto1.getName());
        when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(dto2);

        mockMvc.perform(patch(VendorController.BASE_URL + "/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(dto1.getName())))
                .andExpect(jsonPath("vendor_url", equalTo(dto2.getUrl())));

    }

    @Test
    public void updateVendorById() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Asus");

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendorDTO.getName());
        returnDTO.setUrl(VendorController.BASE_URL  + "/1");

        when(vendorService.putVendor(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(put(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Asus")));


    }
}