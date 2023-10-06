package mbp.onboard.util;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.UUID;

public class Common {
    private Common() {
    }

    private static final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getKey(Object... input) {
        StringBuilder keyBuilder = new StringBuilder();
        Arrays.stream(input).forEach(v -> keyBuilder.append(v).append(":"));
        return keyBuilder.toString();
    }

    public static String getKeyWithFiller(String filler, String... input) {
        StringBuilder keyBuilder = new StringBuilder();
        Arrays.stream(input).forEach(v -> keyBuilder.append(v).append(filler));
        return keyBuilder.toString();
    }

    public static String getCartId() {
        return UUID.randomUUID().toString();
    }

    public static String getTransactionId() {
        return UUID.randomUUID().toString();
    }

    public static java.util.Date combineDateTime(Date sqlDate, Time sqlTime) throws ParseException {
        StringBuilder dateTimeBuilder = new StringBuilder(sqlDate.toString()).append(" ").append(sqlTime);
        java.util.Date dateTime = dateTimeFormatter.parse(dateTimeBuilder.toString());
        return dateTime;
    }
}
