package mbp.onboard.domainObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShowResponse {
    private Integer showId;
    private Integer movieId;
    private Integer audiId;
    private Integer blockedSeats;
    private Integer bookedSeats;
    private Date showDateTime;
}
