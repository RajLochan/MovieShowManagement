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
public class ScreenUpsertRequest {
    @NotEmpty
    private String name;

    private Integer frontSeats;

    private Integer middleSeats;

    private Integer backSeats;
    @Positive
    private Integer theatreId;
}
