package mbp.cache;




import mbp.Entity.MovieTheaterViewEntity;
import mbp.onboard.repo.MovieShowViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class MovieSearchViewCache {
    @Autowired
    private MovieShowViewRepository movieShowViewRepository;

    @Autowired
    RedisTemplate redisTemplate;
    @Cacheable(cacheNames = "#myCache", key = "#cityName + '-' + #showDate")
    public List<MovieTheaterViewEntity> theatreSearch(String cityName, Date showDate) throws Exception {
         List<MovieTheaterViewEntity> result=movieShowViewRepository.findByDate(cityName,showDate);
        return result;
    }

    @Cacheable(cacheNames = "#myCache", key = "#cityName")
    public List<MovieTheaterViewEntity>  searchRunningShows(String cityName) throws Exception {
        List<MovieTheaterViewEntity> result= movieShowViewRepository.findByTodaysDate(cityName);
        return result;
    }
}
