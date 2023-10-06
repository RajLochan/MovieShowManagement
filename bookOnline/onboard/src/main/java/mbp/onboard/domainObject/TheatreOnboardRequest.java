package mbp.onboard.domainObject;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

 @AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class TheatreOnboardRequest {
    @NotEmpty
    private String ownerId;
    @NotEmpty
    private String name;
    @NotEmpty
    private String area;
    @NotNull
    private Double lat;
     @NotNull
    private Double lng;
    @Positive
    private Integer cityId;
}
