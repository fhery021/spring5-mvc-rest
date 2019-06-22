package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {

    List<VendorDTO> getAllVendors();

    VendorDTO createNewVendor(VendorDTO vendorDTO);

    void deleteVendorById(Long id);

    VendorDTO getVendorById(Long id);

    /** Update a Vendor */
    VendorDTO patchVendor(Long id, VendorDTO dto);

    /** Replace a vendor by new data */
    VendorDTO putVendor(Long id, VendorDTO dto);

}
