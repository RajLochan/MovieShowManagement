package mbp.onboard.domainObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class CityResponse {
    private Integer id;
    private String name;
    private String district;
    private String state;
    private Integer countryId;
}
