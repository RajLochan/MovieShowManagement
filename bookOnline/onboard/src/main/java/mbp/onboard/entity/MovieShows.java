package mbp.onboard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Date;
import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie_shows")
public class MovieShows {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;

    @ManyToOne()
    @JoinColumn(name = "audi_id", referencedColumnName = "id")
    private Screen audi;

    @Column(name = "blocked_seats")
    private Integer blockedSeats;

    @Column(name = "booked_seats")
    private Integer bookedSeats;

    @Column(name = "show_time")
    private Time showTime;

    @Column(name = "show_date")
    private Date showDate;

}
