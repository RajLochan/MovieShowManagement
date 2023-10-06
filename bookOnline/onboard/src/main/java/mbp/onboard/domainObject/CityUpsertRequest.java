package mbp.onboard.domainObject;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class CityUpsertRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String district;
    @NotEmpty
    private String state;
    @Positive
    private Integer countryId;
}
