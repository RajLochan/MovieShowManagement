package mbp.onboard.domainObject;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class TheatreGetDeleteRequest {
    @NotEmpty
    private String ownerId;
    @Positive
    Integer theaterId;
}
