package mbp.onboard.repo;


import mbp.Entity.MovieTheaterViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
@Repository
public interface MovieShowViewRepository extends JpaRepository<MovieTheaterViewEntity, Long> {


   @Query(nativeQuery = true,value = "select * from MovieTheaterView where show_date = CURDATE() and city_name=?1")
    List<MovieTheaterViewEntity> findByTodaysDate(String cityName);

    @Query(nativeQuery = true,value = "select * from MovieTheaterView where show_date =?2  and city_name=?1")
    List<MovieTheaterViewEntity> findByDate(String cityName, Date date);
    List<MovieTheaterViewEntity> findByMovieName(String movieName);

    List<MovieTheaterViewEntity> findByCityName(String cityName);

}
