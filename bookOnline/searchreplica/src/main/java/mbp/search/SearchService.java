package mbp.search;



import mbp.Entity.MovieTheaterViewEntity;
import mbp.domainobject.SearchFilterRequest;

import java.util.List;

public interface SearchService {
    List<MovieTheaterViewEntity> theatreSearch(SearchFilterRequest request) throws Exception;
    List<MovieTheaterViewEntity> searchRunningShows(SearchFilterRequest request) throws Exception;
}
