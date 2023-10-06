package mbp.onboard.service.impl;

import jakarta.persistence.EntityNotFoundException;

import mbp.onboard.domainObject.TheatreOnboardRequest;
import mbp.onboard.domainObject.TheatreResponse;
import mbp.onboard.entity.City;
import mbp.onboard.entity.Theatre;
import mbp.onboard.pubsub.TheatresPublisher;

import mbp.onboard.util.Operation;
import mbp.onboard.repo.CityRepository;
import mbp.onboard.repo.TheatreRepository;
import mbp.onboard.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
public class TheatreServiceImpl implements TheatreService {
    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private TheatresPublisher theatresPublisher;

    @Override
    public TheatreResponse saveTheatre(TheatreOnboardRequest request) throws Exception {
        Optional<City> city = cityRepository.findById(request.getCityId());
        if (city.isEmpty()) {
            throw new EntityNotFoundException("City not found");
        }
        City cityDb = city.get();
        Theatre theatreDb = theatreRepository.save(new Theatre(null, request.getOwnerId(),request.getName(), request.getLat(), request.getLng(), request.getArea(), cityDb));
        publishTheatres(theatreDb.getId(), theatreDb.getName(), theatreDb.getCity().getId(),
                theatreDb.getCity().getName(), theatreDb.getLat(), theatreDb.getLng(), Operation.create);
        return new TheatreResponse(theatreDb.getId(), theatreDb.getName(), theatreDb.getLat(), theatreDb.getLng(),  theatreDb.getArea(), cityDb.getId());
    }

    @Override
    public TheatreResponse getTheatre(Integer id) throws Exception {
        Optional<Theatre> theatre = theatreRepository.findById(id);
        if (theatre.isEmpty()) {
            throw new EntityNotFoundException("Theatre not found");
        }
        Theatre theatreDb = theatre.get();
        return new TheatreResponse(theatreDb.getId(), theatreDb.getName(), theatreDb.getLat(), theatreDb.getLng(), theatreDb.getArea(), theatreDb.getCity().getId());
    }

    @Override
    public TheatreResponse updateTheatre(Integer id, TheatreOnboardRequest request) throws Exception {
        Optional<City> city = cityRepository.findById(request.getCityId());
        if (city.isEmpty()) {
            throw new EntityNotFoundException("City not found");
        }
        City cityDb = city.get();
        Optional<Theatre> theatre = theatreRepository.findById(id);
        if (theatre.isEmpty()) {
            throw new EntityNotFoundException("Theatre not found");
        }
        Theatre theatreDb = theatre.get();
        theatreDb.setArea(request.getArea());
        theatreDb.setName(request.getName());
        theatreDb.setCity(cityDb);
        theatreDb.setLat(request.getLat());
        theatreDb.setLng(request.getLng());
        publishTheatres(theatreDb.getId(), theatreDb.getName(), theatreDb.getCity().getId(),
                theatreDb.getCity().getName(), theatreDb.getLat(), theatreDb.getLng(), Operation.update);
        return new TheatreResponse(theatreDb.getId(), theatreDb.getName(), theatreDb.getLat(), theatreDb.getLng(), theatreDb.getArea(), theatreDb.getCity().getId());
    }

    @Override
    public void deleteTheatre(Integer id) throws Exception {
        Optional<Theatre> theatre = theatreRepository.findById(id);
        if (theatre.isEmpty()) {
            throw new EntityNotFoundException("Theatre not found");
        }
        Theatre theatreDb = theatre.get();
        publishTheatres(theatreDb.getId(), theatreDb.getName(), theatreDb.getCity().getId(),
                theatreDb.getCity().getName(), theatreDb.getLat(), theatreDb.getLng(), Operation.delete);
        theatreRepository.deleteById(id);
    }

    protected void publishTheatres(Integer theatreId, String theatreName, Integer cityId, String cityName, Double lat
            , Double lng, Operation operation) {
        theatresPublisher.publish( theatreName,new TheatreMessage(theatreId, theatreName, cityName, cityId,
                new GeoPoint(lat, lng), operation));
    }

}
