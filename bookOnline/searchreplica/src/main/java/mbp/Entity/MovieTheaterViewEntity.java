package mbp.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
@Table(name="MovieTheaterView")
@Immutable
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class MovieTheaterViewEntity implements Serializable {
    @Id
    @Column(name = "show_id")
    private Long id;
    @Column(name = "movie_name")
    private String movieName;
    @Column(name = "movie_variant")
    private String movieVariant;
    @Column(name = "theatre_name")
    private String theatreName;
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "show_time")
    private Time showTime;
    @Column(name = "show_date")
    private Date showDate;
    @Column(name = "screen_name")
    private String screenName;
}
