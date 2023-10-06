package mbp.booking.redisCache;

import lombok.extern.slf4j.Slf4j;
import mbp.booking.entity.SeatAllocationStatus;
import mbp.booking.entity.SeatAllocationStatusId;
import mbp.booking.exception.SeatNotAvailableException;
import mbp.booking.repo.SeatAllocationStatusRepository;
import mbp.booking.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
@Slf4j
public class SeatLockRepositoryImpl implements SeatLockRepository {


    @Autowired
    private CartBookingCacheRepository cartBookingCache;
    @Autowired
    SeatAllocationStatusRepository seatAllocationStatusRepository;
    private static final String LOCK_PREFIX = "dist-lock:";


    @Override
    public void blockSeat(Integer showId, Set<Integer> seats, String lockOwner) throws SeatNotAvailableException {
        Set<Integer> seatAllocated = new HashSet<>(seats.size());
        Integer seatNotAvailable = null;
        for (Integer seatId : seats) {
            try {
                String lockKey = LOCK_PREFIX + showId + ":" + seatId;
                Boolean locked = cartBookingCache.acqDistributedLock(lockKey, lockOwner);
                if (locked != null && locked) {
                    seatAllocated.add(seatId);
                } else {
                    unblockSeat(showId, seats, lockOwner);
                    throw new SeatNotAvailableException(String.format("Seat not available %s", seatNotAvailable));
                }
            }catch (Exception e){
                unblockSeat(showId, seats, lockOwner);
                throw new SeatNotAvailableException(String.format("Seat not available %s", seatNotAvailable));
            }
        }

    }

    @Override
    public void confirmSeat(Integer showId, Set<Integer> seats, String lockOwner) throws SeatNotAvailableException {
        Set<Integer> seatConfirmed = new HashSet<>(seats.size());
        Integer seatNotAvailable = null;
        for (Integer seatId : seats) {
            try{
            String lockKey = LOCK_PREFIX + showId + ":" + seatId;
            Boolean locked =cartBookingCache.checkDistributedLock(lockKey,lockOwner);
            if (locked != null && locked)  {
                seatAllocationStatusRepository.save(new SeatAllocationStatus(showId,seatId,lockOwner, Status.success));
                seatConfirmed.add(seatId);
                cartBookingCache.relDistributedLock(lockKey,lockOwner);
            }else{
                unblockSeat(showId, seats, lockOwner);
                throw new SeatNotAvailableException(String.format("Seat not available %s", seatNotAvailable));
            }
            }catch (Exception e){
                unblockSeat(showId, seats, lockOwner);
                throw new SeatNotAvailableException(String.format("Seat not available %s", seatNotAvailable));
            }
        }

    }

    @Override
    public void unblockSeat(Integer showId, Set<Integer> seats, String lockOwner) throws SeatNotAvailableException {
        for (Integer seatId : seats) {
            try{
            String lockKey = LOCK_PREFIX + showId + ":" + seatId;
            Boolean locked =cartBookingCache.checkOrGetDistributedLock(lockKey,lockOwner);
            if (locked != null && locked) {
                SeatAllocationStatusId id = new SeatAllocationStatusId();
                id.setShowId(showId);
                id.setSeatId(seatId);
                Optional<SeatAllocationStatus> savedSeat=seatAllocationStatusRepository.findById(id);
                savedSeat.ifPresent(object -> {
                    if(object.getUserId().equals(lockOwner)) {
                        seatAllocationStatusRepository.delete(object);
                        log.debug("savedSeat deleted.for "+savedSeat);
                    }
                });
            }
            cartBookingCache.relDistributedLock(lockKey,lockOwner);
            }catch (Exception e){
                unblockSeat(showId, seats, lockOwner);
                throw new SeatNotAvailableException(String.format("unblockSeat failed %s", seats));
            }
        }
    }



}
