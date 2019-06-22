package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorMapper mapper;
    private final VendorRepository repository;

    public VendorServiceImpl(VendorMapper mapper, VendorRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return repository
                .findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = mapper.vendorToVendorDTO(vendor);
                    vendorDTO.setUrl(getVendorUrl(vendor.getId()));
                    return vendorDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return saveAndReturnDTO(mapper.vendorDTOToVendor(vendorDTO));
    }

    @Override
    public void deleteVendorById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return repository
                .findById(id)
                .map(vendor -> {
                    VendorDTO dto = mapper.vendorToVendorDTO(vendor);
                    dto.setUrl(getVendorUrl(id));
                    return dto;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO dto) {
        return repository
                .findById(id)
                .map(vendor -> {
                    if (dto.getName() != null){
                        vendor.setName(dto.getName());
                    }
                    VendorDTO savedDTO = mapper.vendorToVendorDTO(repository.save(vendor));
                    savedDTO.setUrl(getVendorUrl(id));
                    return savedDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO putVendor(Long id, VendorDTO dto) {
        Vendor vendor = mapper.vendorDTOToVendor(dto);
        vendor.setId(id);

        return saveAndReturnDTO(vendor);
    }

    private String getVendorUrl(Long id) {
        return VendorController.BASE_URL + "/" + id;
    }

    private VendorDTO saveAndReturnDTO(Vendor vendor){
        Vendor savedVendor = repository.save(vendor);
        VendorDTO dto = mapper.vendorToVendorDTO(savedVendor);
        dto.setUrl(getVendorUrl(savedVendor.getId()));
        return dto;
    }
}
