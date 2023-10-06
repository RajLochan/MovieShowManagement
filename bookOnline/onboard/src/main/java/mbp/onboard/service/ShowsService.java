package mbp.onboard.service;


import mbp.onboard.domainObject.ShowResponse;
import mbp.onboard.domainObject.ShowUpsertRequest;

public interface ShowsService {
    ShowResponse saveShow(ShowUpsertRequest request) throws Exception;

    ShowResponse getShow(Integer id) throws Exception;

    ShowResponse updateShow(Integer id, ShowUpsertRequest request) throws Exception;

    void deleteShow(Integer id) throws Exception;

    Integer getFreeSeatsCount(Integer id) throws Exception;

}
