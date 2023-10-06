package mbp.onboard.domainObject;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShowUpsertRequest {
    @Positive
    private Integer movieId;
    @Positive
    private Integer audiId;
    private Integer blockedSeats;
    private Integer bookedSeats;
    private Date showDateTime;

}
