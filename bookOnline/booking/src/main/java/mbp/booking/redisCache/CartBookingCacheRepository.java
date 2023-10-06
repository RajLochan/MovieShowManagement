package mbp.booking.redisCache;


import mbp.booking.domainObject.CartShowDetail;
import mbp.booking.exception.SeatNotAvailableException;

public interface CartBookingCacheRepository {

    void saveSeatLock(Integer showId, Integer seatId, String owner);

    void deleteSeatLock(Integer showId, Integer seatId, String owner);

    void checkSeatsLock(Integer showId, Integer seatId) throws SeatNotAvailableException;

    Boolean acqDistributedLock( String lockKey,String lockOwner);

    void relDistributedLock( String lockKey,String lockOwner);

    Boolean checkDistributedLock(String lockKey,String lockOwner);

    Boolean checkOrGetDistributedLock(String lockKey,String lockOwner);

    void saveBookingDetail(String cartId, CartShowDetail cartDetail);

    void isBookingDetailExists(String cartId);

    CartShowDetail getBookingDetail(String cartId);

    void deleteBookingDetail(String cartId);
}
