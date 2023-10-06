package mbp.onboard.domainObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class TheatreResponse {
    private Integer id;

    private String name;

    private Double lat;

    private Double lng;


    private String area;

    private Integer cityId;
}
