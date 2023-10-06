package mbp.order.service.client;


import mbp.order.domainobject.BookShowResponse;

public class BookingSvcFallBack implements BookingServiceApiClient{
    @Override
    public BookShowResponse getCart(String apiKey,String id) throws Exception {
        return null;
    }
}
