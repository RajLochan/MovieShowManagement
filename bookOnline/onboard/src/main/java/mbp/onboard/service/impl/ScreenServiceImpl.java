package mbp.onboard.service.impl;

import jakarta.persistence.EntityNotFoundException;
import mbp.onboard.domainObject.ScreenResponse;
import mbp.onboard.domainObject.ScreenUpsertRequest;
import mbp.onboard.entity.Screen;
import mbp.onboard.entity.Theatre;
import mbp.onboard.repo.ScreenRepository;
import mbp.onboard.repo.TheatreRepository;
import mbp.onboard.service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
public class ScreenServiceImpl implements ScreenService {
    @Autowired
    private ScreenRepository audiRepository;
    @Autowired
    private TheatreRepository theatreRepository;

    @Override
    public ScreenResponse saveAudi(ScreenUpsertRequest request) throws Exception {
        Optional<Theatre> theatre = theatreRepository.findById(request.getTheatreId());
        if (theatre.isEmpty()) {
            throw new EntityNotFoundException("Theatre not found");
        }
        Theatre theatreDb = theatre.get();
        Screen audiSaved = audiRepository.save(new Screen(null, request.getFrontSeats(),
                request.getMiddleSeats(), request.getBackSeats(), request.getName(), theatreDb));

        return new ScreenResponse(audiSaved.getId(), audiSaved.getName(), audiSaved.getFrontSeats(),
                audiSaved.getMiddleSeats(), audiSaved.getBackSeats(),
                audiSaved.getTheatre().getId());
    }

    @Override
    public ScreenResponse getAudi(Integer id) throws Exception {
        Optional<Screen> audi = audiRepository.findById(id);
        if (audi.isEmpty()) {
            throw new EntityNotFoundException("Audi not found");
        }
        Screen audiDb = audi.get();
        return new ScreenResponse(audiDb.getId(), audiDb.getName(), audiDb.getFrontSeats(),
                audiDb.getMiddleSeats(), audiDb.getBackSeats(),
                audiDb.getTheatre().getId());
    }

    @Override
    public ScreenResponse updateAudi(Integer id, ScreenUpsertRequest request) throws Exception {
        Optional<Screen> audi = audiRepository.findById(id);
        if (audi.isEmpty()) {
            throw new EntityNotFoundException("Audi not found");
        }
        Screen audiDb = audi.get();
        Optional<Theatre> theatre = theatreRepository.findById(request.getTheatreId());
        if (theatre.isEmpty()) {
            throw new EntityNotFoundException("Theatre not found");
        }
        Theatre theatreDb = theatre.get();
        audiDb.setName(request.getName());
        audiDb.setTheatre(theatreDb);
        audiDb.setFrontSeats(request.getFrontSeats());
        audiDb.setBackSeats(request.getBackSeats());
        audiDb.setMiddleSeats(request.getMiddleSeats());
        Screen audiSaved = audiRepository.save(audiDb);

        return new ScreenResponse(audiSaved.getId(), audiSaved.getName(), audiSaved.getFrontSeats(),
                audiSaved.getMiddleSeats(), audiSaved.getBackSeats(),
                audiSaved.getTheatre().getId());
    }

    @Override
    public void deleteAudi(Integer id) throws Exception {
        theatreRepository.deleteById(id);
    }
}
