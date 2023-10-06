package mbp.order.domainobject;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class BookShowResponse {
    @NotEmpty
    private String cartId;
    @NotEmpty
    private String sessionId;
    @NotEmpty
    private Integer cityId;
    @NotEmpty
    private Integer theatreId;
    @NotEmpty
    private Integer audiId;
    @NotEmpty
    private Integer showId;
    @NotEmpty
    private Set<Integer> seats;

    private CartBillDetail cartBillDetail;

    public static BookShowResponse copy(CartShowDetail obj) {
        BookShowResponse response = new BookShowResponse();
        response.setCartId(obj.getCartId());
        response.setSessionId(obj.getSessionId());
        response.setCityId(obj.getCityId());
        response.setTheatreId(obj.getTheatreId());
        response.setAudiId(obj.getAudiId());
        response.setShowId(obj.getShowId());
        response.setCartBillDetail(obj.getCartBillDetail());
        return response;
    }
}
