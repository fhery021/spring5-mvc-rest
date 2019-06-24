package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jt on 10/6/17.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {

    @ApiModelProperty(value = "Name of the vendor", required = true)
    private String name;

    @ApiModelProperty(value = "URL of the vendor, generated automatically based on vendor id.")
    @JsonProperty("vendor_url")
    private String vendorUrl;

}
