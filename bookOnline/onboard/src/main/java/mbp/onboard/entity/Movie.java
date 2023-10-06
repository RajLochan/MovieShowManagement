package mbp.onboard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "duration_hour")
    private Integer durationHour;

    @Column(name = "duration_mint")
    private Integer durationMint;

    @ManyToOne
    @JoinColumn(name = "variant_id", referencedColumnName = "id")
    private MovieVariant variant;
}
