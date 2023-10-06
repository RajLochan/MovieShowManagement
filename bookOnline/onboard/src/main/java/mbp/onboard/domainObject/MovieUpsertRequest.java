package mbp.onboard.domainObject;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MovieUpsertRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @Positive
    private Integer durationHour;
    @Positive
    private Integer durationMint;
    @Positive
    private Integer variantId;
}
