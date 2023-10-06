package mbp.domainobject;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchFilterRequest {
    private String movieName;
    private  String movieVariant;
    private String theatreName;
    @NotEmpty
    private String cityName;
    private LocalTime showTime;
    @NotNull
    private Date showDate;
}
