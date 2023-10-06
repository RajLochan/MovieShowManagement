package mbp.onboard.domainObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MovieResponse {
    private Integer id;
    private String name;

    private String description;

    private Integer durationHour;

    private Integer durationMint;

    private Integer variantId;
}
