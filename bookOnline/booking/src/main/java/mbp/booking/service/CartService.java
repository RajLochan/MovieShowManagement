package mbp.booking.service;


import mbp.booking.domainObject.BookShowRequest;
import mbp.booking.domainObject.BookShowResponse;

public interface CartService {
    BookShowResponse saveCart(BookShowRequest request) throws Exception;

    BookShowResponse updateCart(String id, BookShowRequest request) throws Exception;

    BookShowResponse getCart(String id) throws Exception;

    void deleteCart(String id) throws Exception;

    BookShowResponse confirmCart(String id, BookShowRequest request) throws Exception;

    BookShowResponse lockCartSeats(String id, BookShowRequest request) throws Exception;
}
