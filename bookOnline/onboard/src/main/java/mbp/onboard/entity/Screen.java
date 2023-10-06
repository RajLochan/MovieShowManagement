package mbp.onboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "screen")
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @JsonIgnore
    @Column(name = "front_seats")
    private Integer frontSeats;
    @JsonIgnore
    @Column(name = "middle_seats")
    private Integer middleSeats;
    @JsonIgnore
    @Column(name = "back_seats")
    private Integer backSeats;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "theatre_id", referencedColumnName = "id")
    private Theatre theatre;

    public int getTotalSeats(){
        return frontSeats+middleSeats+backSeats;
    }
}
