package mbp.booking.service.impl;

import mbp.booking.domainObject.CartShowDetail;
import mbp.booking.domainObject.BookShowRequest;
import mbp.booking.domainObject.BookShowResponse;
import mbp.booking.pubsub.Operation;
import mbp.booking.pubsub.ShowsSeatLockPublisher;
import mbp.booking.pubsub.events.SeatLockEvent;
import mbp.booking.pubsub.events.SeatLockMessage;
import mbp.booking.redisCache.CartBookingCacheRepository;
import mbp.booking.redisCache.SeatLockRepository;
import mbp.booking.service.CartOperations;
import mbp.booking.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartOperations cartOperations;
    @Autowired
    private CartBookingCacheRepository cartBookingCache;
    @Autowired
    private SeatLockRepository seatLockRepository;
    @Autowired
    private ShowsSeatLockPublisher seatLockPublisher;

    @Override
    public BookShowResponse saveCart(BookShowRequest request) throws Exception {
        CartShowDetail cartDetail = CartShowDetail.copy(request);
        cartOperations.verifyCart(cartDetail);
        cartOperations.computeBill(cartDetail);
        cartBookingCache.saveBookingDetail(cartDetail.getCartId(), cartDetail);
        BookShowResponse response = new BookShowResponse(cartDetail.getCartId(), request.getUserId(),
                request.getCityId(), request.getTheatreId(), request.getAudiId(), request.getShowId(), request.getSeats(),
                cartDetail.getCartBillDetail());
        return response;
    }

    @Override

    public BookShowResponse updateCart(String id, BookShowRequest request) throws Exception {
        CartShowDetail cartDetail = CartShowDetail.copy(id, request);
        cartBookingCache.isBookingDetailExists(id);
        cartOperations.verifyCart(cartDetail);
        cartOperations.computeBill(cartDetail);
        cartBookingCache.saveBookingDetail(cartDetail.getCartId(), cartDetail);
        BookShowResponse response = new BookShowResponse(cartDetail.getCartId(), request.getUserId(),
                request.getCityId(), request.getTheatreId(), request.getAudiId(), request.getShowId(), request.getSeats(),
                cartDetail.getCartBillDetail());
        return response;
    }

    @Override
    public BookShowResponse confirmCart(String id, BookShowRequest request) throws Exception {
        CartShowDetail cartDetail = CartShowDetail.copy(id, request);
        cartBookingCache.isBookingDetailExists(id);
        cartOperations.verifyCart(cartDetail);
        cartOperations.computeBill(cartDetail);
        try {
            seatLockRepository.blockSeat(cartDetail.getShowId(), cartDetail.getSeats(), cartDetail.getCartId());
        } catch (Exception snae) {
            seatLockRepository.unblockSeat(cartDetail.getShowId(), cartDetail.getSeats(), cartDetail.getCartId());
            throw snae;
        }
        cartBookingCache.saveBookingDetail(cartDetail.getCartId(), cartDetail);
        SeatLockEvent event=new SeatLockEvent();event.setSeatsLocked(cartDetail.getSeats());event.setCartId(cartDetail.getCartId());
        event.setShowId(cartDetail.getShowId());event.setSeatsConfirmed(cartDetail.getSeats());
        seatLockPublisher.publish(cartDetail.getShowId().toString(), event);
        BookShowResponse response = new BookShowResponse(cartDetail.getCartId(), request.getUserId(),
                request.getCityId(), request.getTheatreId(), request.getAudiId(), request.getShowId(), request.getSeats(),
                cartDetail.getCartBillDetail());
        return response;
    }

    @Override
    public BookShowResponse lockCartSeats(String id, BookShowRequest request) throws Exception {
       // CartShowDetail cartDetail = CartShowDetail.copy(id, request);
        cartBookingCache.isBookingDetailExists(id);
        CartShowDetail cartDetail=cartBookingCache.getBookingDetail(id);
        cartOperations.verifyCart(cartDetail);
        cartOperations.computeBill(cartDetail);
        try {
            seatLockRepository.blockSeat(cartDetail.getShowId(), cartDetail.getSeats(), cartDetail.getCartId());
        } catch (Exception snae) {
            seatLockRepository.unblockSeat(cartDetail.getShowId(), cartDetail.getSeats(), cartDetail.getCartId());
            throw snae;
        }
        cartBookingCache.saveBookingDetail(cartDetail.getCartId(), cartDetail);
        SeatLockEvent event=new SeatLockEvent();event.setSeatsLocked(cartDetail.getSeats());event.setCartId(cartDetail.getCartId());
        event.setShowId(cartDetail.getShowId());
        seatLockPublisher.publish(cartDetail.getShowId().toString(), event);
        BookShowResponse response = new BookShowResponse(cartDetail.getCartId(), request.getUserId(),
                request.getCityId(), request.getTheatreId(), request.getAudiId(), request.getShowId(), request.getSeats(),
                cartDetail.getCartBillDetail());
        return response;
    }

    @Override
    public BookShowResponse getCart(String id) throws Exception {
        cartBookingCache.isBookingDetailExists(id);
        CartShowDetail cartDetail = cartBookingCache.getBookingDetail(id);
        return BookShowResponse.copy(cartDetail);
    }

    @Override
    public void deleteCart(String id) throws Exception {
        cartBookingCache.isBookingDetailExists(id);
        cartBookingCache.deleteBookingDetail(id);
    }

}
