package mbp.booking.redisCache;

import jakarta.persistence.EntityNotFoundException;

import mbp.booking.domainObject.CartShowDetail;
import mbp.booking.exception.SeatNotAvailableException;

import mbp.booking.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class CartBookingCacheRepositoryImpl implements CartBookingCacheRepository {

        @Autowired
        private RedisTemplate<String, Object> redisTemplate;
    @Value("${cart.booking.detail.namespace}")
    private String cartDetailNamespace;
    @Value("${cart.booking.detail.ttl.ms}")
    private long cartDetailTtl;
    @Value("${cart.booking.seats.lock.namespace}")
    private String seatLockNamespace;
    @Value("${cart.booking.seats.lock.ttl.ms}")
    private long seatLockTtl;

    @Value("${cart.booking.seats.dis.lock.timeout}")
    private Integer disLockTimeout;

    @Override
    public void saveSeatLock(Integer showId, Integer seatId, String owner) {
        redisTemplate.opsForValue().set(Common.getKey(seatLockNamespace, showId, seatId,owner), "1", seatLockTtl);
    }

    @Override
    public void deleteSeatLock(Integer showId, Integer seatId, String owner) {
        redisTemplate.opsForValue().set(Common.getKey(seatLockNamespace, showId, seatId,owner), "", 1, TimeUnit.MILLISECONDS);
    }

    @Override
    public void checkSeatsLock(Integer showId, Integer seatId) throws SeatNotAvailableException {
        String val = (String) redisTemplate.opsForValue().get(Common.getKey(seatLockNamespace, showId, seatId));
        if (val != null)
            throw new SeatNotAvailableException(String.format("Seat not available %s", seatId));
    }

    @Override
    public Boolean acqDistributedLock(String lockKey, String lockOwner) {
        Boolean lockAcquired =redisTemplate.opsForValue().setIfAbsent
                (lockKey, Common.getKey(lockOwner), disLockTimeout, TimeUnit.SECONDS);
        return lockAcquired != null && lockAcquired;
    }

    @Override
    public void relDistributedLock(String lockKey,String lockOwner) {
        String currentOwner = (String) redisTemplate.opsForValue().get(lockKey);
        if (currentOwner != null && currentOwner.equals(lockOwner)) {
            redisTemplate.delete(lockKey);
        }
    }

    @Override
    public Boolean checkDistributedLock(String lockKey,String lockOwner) {
        Boolean ownerExists=false;
        String currentOwner = (String) redisTemplate.opsForValue().get(lockKey);
        if (currentOwner != null && currentOwner.equals(lockOwner)) {
            ownerExists=true;
        }
        return ownerExists;
    }

    @Override
    public Boolean checkOrGetDistributedLock(String lockKey,String lockOwner) {
      return checkDistributedLock(lockKey,lockOwner) || acqDistributedLock(lockKey,lockOwner);
    }
    @Override
    public void saveBookingDetail(String cartId, CartShowDetail cartDetail) {
        redisTemplate.opsForValue().set(Common.getKey(cartDetailNamespace, cartId), cartDetail,
                cartDetailTtl, TimeUnit.MILLISECONDS);
    }

    @Override
    public void isBookingDetailExists(String cartId) {
        CartShowDetail cartDetail = (CartShowDetail) redisTemplate.opsForValue().get(Common.getKey(cartDetailNamespace, cartId));
        if (cartDetail == null)
            throw new EntityNotFoundException("Cart not exists");
    }

    @Override
    public CartShowDetail getBookingDetail(String cartId) {
        CartShowDetail cartDetail = (CartShowDetail) redisTemplate.opsForValue().get(Common.getKey(cartDetailNamespace, cartId));
        if (cartDetail == null)
            throw new EntityNotFoundException("Cart not exists");
        return cartDetail;
    }

    @Override
    public void deleteBookingDetail(String cartId) {
        redisTemplate.opsForValue().set(Common.getKey(cartDetailNamespace, cartId), "", 1, TimeUnit.MILLISECONDS);
    }

}
