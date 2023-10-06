package mbp.onboard.service.impl;

import jakarta.persistence.EntityNotFoundException;

import mbp.onboard.domainObject.CityResponse;
import mbp.onboard.domainObject.CityUpsertRequest;
import mbp.onboard.entity.City;
import mbp.onboard.entity.Country;
import mbp.onboard.repo.CityRepository;
import mbp.onboard.repo.CountryRepository;
import mbp.onboard.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
public class CityServiceImpl implements CityService {
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Override
    public CityResponse saveCity(CityUpsertRequest request) throws Exception {
        Country countryDb = countryRepository.getReferenceById(request.getCountryId());
        if (countryDb == null) {
            throw new EntityNotFoundException("Country not found");
        }
        City cityDb = new City(null, request.getName(), request.getDistrict(), request.getState(), countryDb);
        City saved = cityRepository.save(cityDb);
        return new CityResponse(saved.getId(), saved.getName(), saved.getDistrict(), saved.getState(), saved.getCountry().getId());
    }

    @Override
    public CityResponse getCity(Integer id) throws Exception {
        City cityDb = cityRepository.getReferenceById(id);
        return new CityResponse(cityDb.getId(), cityDb.getName(), cityDb.getDistrict(), cityDb.getState(), cityDb.getCountry().getId());
    }

    @Override
    public List<CityResponse> getCities(Integer page, Integer pageSize) throws Exception {
        Pageable paging = PageRequest.of(page, pageSize);
        Page<City> cityPages = cityRepository.findAll(paging);
        if (cityPages.isEmpty()) {
            throw new EntityNotFoundException("Data not found");
        }
        return cityPages.get().map(r -> {
            return new CityResponse(r.getId(), r.getName(), r.getDistrict(), r.getState(),
                    r.getCountry().getId());
        }).collect(Collectors.toList());
    }

    @Override
    public boolean deleteCity(Integer id) throws Exception {
        cityRepository.deleteById(id);
        return true;
    }

    @Override
    public CityResponse updateCity(Integer id, CityUpsertRequest request) throws Exception {
        Optional<City> optionalCity = cityRepository.findById(id);
        if (optionalCity.isEmpty()) {
            throw new EntityNotFoundException("City not found");
        }
        City cityDb = optionalCity.get();
        Country countryDb = countryRepository.getReferenceById(request.getCountryId());
        if (countryDb == null) {
            throw new EntityNotFoundException("Country not found");
        }
        cityDb.setCountry(countryDb);
        cityDb.setName(request.getName());
        cityDb.setDistrict(request.getDistrict());
        cityDb.setState(request.getState());
        City saved = cityRepository.save(cityDb);
        return new CityResponse(saved.getId(), saved.getName(), saved.getDistrict(), saved.getState(), saved.getCountry().getId());
    }
}
