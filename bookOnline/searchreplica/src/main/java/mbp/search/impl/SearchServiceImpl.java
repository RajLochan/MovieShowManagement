package mbp.search.impl;

import mbp.Entity.MovieTheaterViewEntity;
import mbp.cache.MovieSearchViewCache;

import mbp.domainobject.SearchFilterRequest;
import mbp.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    MovieSearchViewCache movieSearchViewCache;

    @Override
    public List<MovieTheaterViewEntity> theatreSearch(SearchFilterRequest request) throws Exception {
        List<MovieTheaterViewEntity> movieTheaterViewEntities=movieSearchViewCache.theatreSearch(request.getCityName(),
                new java.sql.Date(request.getShowDate().getTime()));
        return movieTheaterViewEntities.stream().filter(res->request.getMovieName()==null || res.getMovieName().equals(request.getMovieName()))
                .filter(res->request.getTheatreName()==null || res.getTheatreName().equals(request.getTheatreName()))
                .filter(res->request.getMovieVariant()==null || res.getMovieVariant().equals(request.getMovieVariant()))
                .filter(res->request.getShowTime()==null || res.getShowTime().toLocalTime().compareTo(request.getShowTime())>=0)
                .collect(Collectors.toList());
    }


    public List<MovieTheaterViewEntity> searchRunningShows(SearchFilterRequest request) throws Exception{
            return movieSearchViewCache.searchRunningShows(request.getCityName());
    }

}
