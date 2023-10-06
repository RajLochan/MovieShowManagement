package mbp.onboard.domainObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ScreenResponse {
    private Integer Id;

    private String name;

    private Integer frontSeats;

    private Integer middleSeats;

    private Integer backSeats;

    private Integer theatreId;
}
