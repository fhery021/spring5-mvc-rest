package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class VendorMapperTest {
//    {
//        "name": "Western Tasty Fruits Ltd.",
//            "vendor_url": "/shop/vendors/672"
//    },
    private static final String NAME = "Western Tasty Fruits Ltd.";
    private static final String URL = "/shop/vendors/672";
    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void vendorToVendorDTO(){
        //given
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setUrl(URL);

        // when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        assertEquals(NAME, vendorDTO.getName());
        assertThat(URL, equalTo(vendor.getUrl()));
    }
}
