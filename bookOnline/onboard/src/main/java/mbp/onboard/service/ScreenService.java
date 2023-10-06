package mbp.onboard.service;


import mbp.onboard.domainObject.ScreenResponse;
import mbp.onboard.domainObject.ScreenUpsertRequest;

public interface ScreenService {
    ScreenResponse saveAudi(ScreenUpsertRequest request) throws Exception;

    ScreenResponse getAudi(Integer id) throws Exception;

    ScreenResponse updateAudi(Integer id, ScreenUpsertRequest request) throws Exception;

    void deleteAudi(Integer id) throws Exception;
}
