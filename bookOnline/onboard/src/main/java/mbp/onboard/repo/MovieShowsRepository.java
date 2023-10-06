package mbp.onboard.repo;


import mbp.onboard.entity.MovieShows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieShowsRepository extends JpaRepository<MovieShows, Integer> {

    @Query(nativeQuery = true, value = "select ((ad.front_seats+ad.middle_seats+ad.back_seats) - (ms.blocked_seats + booked_seats)) as free_seats from" +
            " movie_shows ms join screen ad on ms.audi_id=ad.id where ms.id=?1")
    Integer getFreeSeatsCount(Integer showId);

}
